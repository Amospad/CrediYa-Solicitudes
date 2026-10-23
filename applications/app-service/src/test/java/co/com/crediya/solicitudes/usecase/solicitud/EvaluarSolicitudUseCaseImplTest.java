package co.com.crediya.solicitudes.usecase.solicitud;

import co.com.crediya.solicitudes.model.solicitud.Solicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EvaluarSolicitudUseCaseImplTest {

    private EvaluarSolicitudUseCaseImpl evaluarUseCase;

    @BeforeEach
    void setUp() {
        evaluarUseCase = new EvaluarSolicitudUseCaseImpl();
    }

    @Test
    @DisplayName("Debe APROBAR la solicitud si la cuota cabe en la capacidad disponible")
    void evaluar_Aprobada() {
        // Cliente gana 5M, tiene deuda de 500k
        BigDecimal salario = new BigDecimal("5000000");
        BigDecimal deudaActual = new BigDecimal("500000");
        BigDecimal tasa = new BigDecimal("0.015");
        
        // Pide 10M a 24 meses
        Solicitud solicitud = Solicitud.builder()
                .monto(new BigDecimal("10000000"))
                .plazo(24)
                .build();

        String resultado = evaluarUseCase.evaluarSolicitud(solicitud, salario, deudaActual, tasa);

        assertThat(resultado).isEqualTo("APROBADA");
    }

    @Test
    @DisplayName("Debe RECHAZAR la solicitud si la cuota supera la capacidad disponible")
    void evaluar_Rechazada() {
        // Cliente gana 5M, tiene deuda de 500k
        BigDecimal salario = new BigDecimal("5000000");
        BigDecimal deudaActual = new BigDecimal("500000");
        BigDecimal tasa = new BigDecimal("0.015");
        
        // Pide 50M a 24 meses (cuota gigante)
        Solicitud solicitud = Solicitud.builder()
                .monto(new BigDecimal("50000000"))
                .plazo(24)
                .build();

        String resultado = evaluarUseCase.evaluarSolicitud(solicitud, salario, deudaActual, tasa);

        assertThat(resultado).isEqualTo("RECHAZADA");
    }

    @Test
    @DisplayName("Debe mandar a REVISION_MANUAL si el préstamo supera los 5 salarios")
    void evaluar_RevisionManual() {
        // Cliente gana 5M, no tiene deudas
        BigDecimal salario = new BigDecimal("5000000");
        BigDecimal deudaActual = BigDecimal.ZERO;
        BigDecimal tasa = new BigDecimal("0.015");
        
        // Pide 30M (más de 5 salarios = 25M)
        Solicitud solicitud = Solicitud.builder()
                .monto(new BigDecimal("30000000"))
                .plazo(24)
                .build();

        String resultado = evaluarUseCase.evaluarSolicitud(solicitud, salario, deudaActual, tasa);

        assertThat(resultado).isEqualTo("REVISION_MANUAL");
    }
}