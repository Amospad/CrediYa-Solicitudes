package co.com.crediya.solicitudes.api;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EvaluarRequestDTO {
    private BigDecimal salarioBase;
    private BigDecimal deudaActual;
    private BigDecimal tasaInteres;
    private BigDecimal monto;
    private Integer plazo;
}