package co.com.crediya.solicitudes.jpa;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import co.com.crediya.solicitudes.model.solicitud.TipoPrestamo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "documento_identidad", nullable = false)
    private String documentoIdentidad;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private Integer plazo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_prestamo", nullable = false)
    private TipoPrestamo tipoPrestamo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;
}