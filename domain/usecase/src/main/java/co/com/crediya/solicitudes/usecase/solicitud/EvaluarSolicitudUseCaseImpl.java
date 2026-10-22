package co.com.crediya.solicitudes.usecase.solicitud;

import co.com.crediya.solicitudes.model.solicitud.CapacidadEndeudamientoCalculator;
import co.com.crediya.solicitudes.model.solicitud.Solicitud;

import java.math.BigDecimal;

public class EvaluarSolicitudUseCaseImpl implements EvaluarSolicitudUseCase {

    @Override
    public String evaluarSolicitud(Solicitud solicitud, BigDecimal salarioBase, BigDecimal deudaActual, BigDecimal tasaInteres) {
        // 1. Capacidad Máxima
        BigDecimal capacidadMaxima = CapacidadEndeudamientoCalculator.calcularCapacidadMaxima(salarioBase);

        // 2. Capacidad Disponible
        BigDecimal capacidadDisponible = capacidadMaxima.subtract(deudaActual);

        // 3. Cuota del Nuevo Préstamo
        BigDecimal cuotaNuevoPrestamo = CapacidadEndeudamientoCalculator.calcularCuotaMensual(
                solicitud.getMonto(), solicitud.getPlazo(), tasaInteres
        );

        // 4. Decisión
        if (cuotaNuevoPrestamo.compareTo(capacidadDisponible) <= 0) {
            // Si se aprueba, validar si requiere revisión manual (más de 5 salarios)
            if (CapacidadEndeudamientoCalculator.requiereRevisionManual(solicitud.getMonto(), salarioBase)) {
                return "REVISION_MANUAL";
            }
            return "APROBADA";
        } else {
            return "RECHAZADA";
        }
    }
}