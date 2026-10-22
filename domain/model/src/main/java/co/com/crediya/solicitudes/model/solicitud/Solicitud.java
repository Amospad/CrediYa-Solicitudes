package co.com.crediya.solicitudes.model.solicitud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    private Long id;
    private String documentoIdentidad;
    private BigDecimal monto;
    private Integer plazo;
    private TipoPrestamo tipoPrestamo;
    private EstadoSolicitud estado;
    private LocalDate fechaSolicitud;
}