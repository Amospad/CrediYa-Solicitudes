package co.com.crediya.solicitudes.usecase.solicitud;

import co.com.crediya.solicitudes.model.solicitud.Solicitud;

public interface EvaluarSolicitudUseCase {
    String evaluarSolicitud(Solicitud solicitud, java.math.BigDecimal salarioBase, java.math.BigDecimal deudaActual, java.math.BigDecimal tasaInteres);
}