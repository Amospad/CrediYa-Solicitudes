package co.com.crediya.solicitudes.model.solicitud.gateways;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import co.com.crediya.solicitudes.model.solicitud.Solicitud;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {
    Solicitud save(Solicitud solicitud);
    List<Solicitud> findByEstados(List<EstadoSolicitud> estados);
    Optional<Solicitud> findById(Long id);
}