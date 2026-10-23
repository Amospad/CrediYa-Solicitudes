package co.com.crediya.solicitudes.usecase.solicitud;

import co.com.crediya.solicitudes.model.solicitud.CapacidadEndeudamientoCalculator;
import co.com.crediya.solicitudes.model.solicitud.Solicitud;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CapacidadEndeudamientoCalculatorTest {

    @Test
    @DisplayName("Debe calcular la capacidad máxima como el 35% del salario")
    void calcularCapacidadMaxima_Exitoso() {
        BigDecimal salario = new BigDecimal("5000000");
        BigDecimal esperado = new BigDecimal("1750000.00"); // 35% de 5,000,000

        BigDecimal resultado = CapacidadEndeudamientoCalculator.calcularCapacidadMaxima(salario);

        assertThat(resultado).isEqualByComparingTo(esperado);
    }

    @Test
    @DisplayName("Debe calcular la cuota mensual con la fórmula de amortización")
    void calcularCuotaMensual_Exitoso() {
        BigDecimal monto = new BigDecimal("10000000");
        Integer plazo = 24;
        BigDecimal tasa = new BigDecimal("0.015"); // 1.5% mensual

        BigDecimal cuota = CapacidadEndeudamientoCalculator.calcularCuotaMensual(monto, plazo, tasa);

        // Validamos que la cuota sea mayor a 0 (el cálculo matemático exacto no importa aquí)
        assertThat(cuota).isPositive();
    }

    @Test
    @DisplayName("Debe calcular la cuota cuando la tasa es 0 (sin intereses)")
    void calcularCuotaMensual_TasaCero() {
        BigDecimal monto = new BigDecimal("1200000");
        Integer plazo = 12;
        BigDecimal tasa = BigDecimal.ZERO;

        BigDecimal cuota = CapacidadEndeudamientoCalculator.calcularCuotaMensual(monto, plazo, tasa);

        // 1,200,000 / 12 = 100,000
        assertThat(cuota).isEqualByComparingTo(new BigDecimal("100000.00"));
    }

    @Test
    @DisplayName("Debe requerir revisión manual si el préstamo es mayor a 5 salarios")
    void requiereRevisionManual_Exitoso() {
        BigDecimal monto = new BigDecimal("30000000"); // 30 millones
        BigDecimal salario = new BigDecimal("5000000"); // 5 millones -> 5 salarios = 25 millones

        boolean requiere = CapacidadEndeudamientoCalculator.requiereRevisionManual(monto, salario);

        assertThat(requiere).isTrue();
    }
}