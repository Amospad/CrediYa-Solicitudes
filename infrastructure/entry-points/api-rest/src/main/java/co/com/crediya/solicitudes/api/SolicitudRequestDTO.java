package co.com.crediya.solicitudes.api;

import co.com.crediya.solicitudes.model.solicitud.TipoPrestamo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SolicitudRequestDTO {

    @NotBlank(message = "El documento de identidad es obligatorio")
    private String documentoIdentidad;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "1.0", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "El plazo es obligatorio")
    @Min(value = 1, message = "El plazo debe ser mayor a 0")
    private Integer plazo;

    @NotNull(message = "El tipo de préstamo es obligatorio")
    private TipoPrestamo tipoPrestamo;
}