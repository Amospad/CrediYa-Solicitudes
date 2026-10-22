package co.com.crediya.solicitudes.api;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import co.com.crediya.solicitudes.model.solicitud.TipoPrestamo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SolicitudResponseDTO {
    private Long id;
    private String documentoIdentidad;
    private BigDecimal monto;
    private Integer plazo;
    private TipoPrestamo tipoPrestamo;
    private EstadoSolicitud estado;
    private LocalDate fechaSolicitud;
}