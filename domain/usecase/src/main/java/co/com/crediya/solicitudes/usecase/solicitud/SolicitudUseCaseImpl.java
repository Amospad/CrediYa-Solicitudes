package co.com.crediya.solicitudes.usecase.solicitud;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import co.com.crediya.solicitudes.model.solicitud.Solicitud;
import co.com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SolicitudUseCaseImpl implements SolicitudUseCase {

    private final SolicitudRepository solicitudRepository;

    public SolicitudUseCaseImpl(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    public Solicitud registrarSolicitud(Solicitud solicitud) {
        if (solicitud.getDocumentoIdentidad() == null || solicitud.getDocumentoIdentidad().trim().isEmpty()) throw new IllegalArgumentException("El documento de identidad es obligatorio");
        if (solicitud.getMonto() == null || solicitud.getMonto().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El monto debe ser mayor a 0");
        if (solicitud.getPlazo() == null || solicitud.getPlazo() <= 0) throw new IllegalArgumentException("El plazo debe ser mayor a 0");
        if (solicitud.getTipoPrestamo() == null) throw new IllegalArgumentException("El tipo de préstamo es obligatorio");

        solicitud.setEstado(EstadoSolicitud.PENDIENTE_REVISION);
        solicitud.setFechaSolicitud(LocalDate.now());

        return solicitudRepository.save(solicitud);
    }

    @Override
    public List<Solicitud> listarSolicitudesParaRevision() {
        List<EstadoSolicitud> estadosPermitidos = Arrays.asList(
                EstadoSolicitud.PENDIENTE_REVISION,
                EstadoSolicitud.RECHAZADA,
                EstadoSolicitud.REVISION_MANUAL
        );
        return solicitudRepository.findByEstados(estadosPermitidos);
    }

    @Override
    public Solicitud cambiarEstadoSolicitud(Long id, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        // Validación de negocio: solo se puede aprobar o rechazar
        if (nuevoEstado != EstadoSolicitud.APROBADA && nuevoEstado != EstadoSolicitud.RECHAZADA) {
            throw new IllegalArgumentException("El estado debe ser APROBADA o RECHAZADA");
        }

        solicitud.setEstado(nuevoEstado);
        Solicitud actualizada = solicitudRepository.save(solicitud);

        // Simulación de envío a SQS (Se imprimirá en consola)
        System.out.println("📢 [SQS] Mensaje enviado a la cola de notificaciones:");
        System.out.println("   - Solicitud ID: " + id);
        System.out.println("   - Nuevo Estado: " + nuevoEstado);
        System.out.println("   - Documento Cliente: " + solicitud.getDocumentoIdentidad());

        return actualizada;
    }
}