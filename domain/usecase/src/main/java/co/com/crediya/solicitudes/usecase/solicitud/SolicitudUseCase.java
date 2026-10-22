package co.com.crediya.solicitudes.usecase.solicitud;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import co.com.crediya.solicitudes.model.solicitud.Solicitud;
import java.util.List;

public interface SolicitudUseCase {
    Solicitud registrarSolicitud(Solicitud solicitud);
    List<Solicitud> listarSolicitudesParaRevision();
    Solicitud cambiarEstadoSolicitud(Long id, EstadoSolicitud nuevoEstado);
}