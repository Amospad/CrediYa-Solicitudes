package co.com.crediya.solicitudes.jpa;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import co.com.crediya.solicitudes.model.solicitud.Solicitud;
import co.com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SolicitudAdapter implements SolicitudRepository {

    private final SolicitudJpaRepository solicitudJpaRepository;

    @Override
    public Solicitud save(Solicitud solicitud) {
        SolicitudEntity entity = mapToEntity(solicitud);
        return mapToDomain(solicitudJpaRepository.save(entity));
    }

    @Override
    public List<Solicitud> findByEstados(List<EstadoSolicitud> estados) {
        return solicitudJpaRepository.findByEstadoIn(estados).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return solicitudJpaRepository.findById(id).map(this::mapToDomain);
    }

    private SolicitudEntity mapToEntity(Solicitud s) {
        return SolicitudEntity.builder()
                .id(s.getId()).documentoIdentidad(s.getDocumentoIdentidad())
                .monto(s.getMonto()).plazo(s.getPlazo())
                .tipoPrestamo(s.getTipoPrestamo()).estado(s.getEstado())
                .fechaSolicitud(s.getFechaSolicitud()).build();
    }

    private Solicitud mapToDomain(SolicitudEntity e) {
        return Solicitud.builder()
                .id(e.getId()).documentoIdentidad(e.getDocumentoIdentidad())
                .monto(e.getMonto()).plazo(e.getPlazo())
                .tipoPrestamo(e.getTipoPrestamo()).estado(e.getEstado())
                .fechaSolicitud(e.getFechaSolicitud()).build();
    }
}