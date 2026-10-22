package co.com.crediya.solicitudes.model.solicitud;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CapacidadEndeudamientoCalculator {

    private static final BigDecimal PORCENTAJE_MAXIMO = new BigDecimal("0.35");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1300000"); // Aproximado Colombia 2024

    public static BigDecimal calcularCapacidadMaxima(BigDecimal salarioBase) {
        return salarioBase.multiply(PORCENTAJE_MAXIMO).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcularCuotaMensual(BigDecimal monto, Integer plazo, BigDecimal tasaInteresMensual) {
        if (tasaInteresMensual.compareTo(BigDecimal.ZERO) == 0) {
            return monto.divide(new BigDecimal(plazo), 2, RoundingMode.HALF_UP);
        }
        // Fórmula: Cuota = P * (i * (1+i)^n) / ((1+i)^n - 1)
        double i = tasaInteresMensual.doubleValue();
        double n = plazo.doubleValue();
        double p = monto.doubleValue();
        double factor = Math.pow(1 + i, n);
        double cuota = p * (i * factor) / (factor - 1);
        return BigDecimal.valueOf(cuota).setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean requiereRevisionManual(BigDecimal monto, BigDecimal salarioBase) {
        BigDecimal cincoSalarios = salarioBase.multiply(new BigDecimal("5"));
        return monto.compareTo(cincoSalarios) > 0;
    }
}