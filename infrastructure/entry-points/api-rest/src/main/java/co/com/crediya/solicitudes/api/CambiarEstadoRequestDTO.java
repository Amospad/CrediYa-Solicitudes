package co.com.crediya.solicitudes.api;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarEstadoRequestDTO {
    @NotNull(message = "El nuevo estado es obligatorio")
    private EstadoSolicitud estado;
}