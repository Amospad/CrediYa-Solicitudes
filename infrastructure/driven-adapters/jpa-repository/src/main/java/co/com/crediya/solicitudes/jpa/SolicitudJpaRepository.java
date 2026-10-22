package co.com.crediya.solicitudes.jpa;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudJpaRepository extends JpaRepository<SolicitudEntity, Long> {
    List<SolicitudEntity> findByEstadoIn(List<EstadoSolicitud> estados);
}