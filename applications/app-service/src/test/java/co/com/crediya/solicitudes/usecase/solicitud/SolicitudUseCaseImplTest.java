package co.com.crediya.solicitudes.usecase.solicitud;

import co.com.crediya.solicitudes.model.solicitud.EstadoSolicitud;
import co.com.crediya.solicitudes.model.solicitud.Solicitud;
import co.com.crediya.solicitudes.model.solicitud.TipoPrestamo;
import co.com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudUseCaseImplTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @InjectMocks
    private SolicitudUseCaseImpl solicitudUseCase;

    private Solicitud validSolicitud;

    @BeforeEach
    void setUp() {
        validSolicitud = Solicitud.builder()
                .documentoIdentidad("123456789")
                .monto(new BigDecimal("5000000"))
                .plazo(12)
                .tipoPrestamo(TipoPrestamo.LIBRE_INVERSION)
                .build();
    }

    @Test
    @DisplayName("Debe registrar una solicitud con estado PENDIENTE_REVISION")
    void registrarSolicitud_Exitoso() {
        // Arrange
        when(solicitudRepository.save(any(Solicitud.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Solicitud resultado = solicitudUseCase.registrarSolicitud(validSolicitud);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo(EstadoSolicitud.PENDIENTE_REVISION);
        assertThat(resultado.getFechaSolicitud()).isNotNull();
        
        verify(solicitudRepository, times(1)).save(any(Solicitud.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el documento está vacío")
    void registrarSolicitud_DocumentoVacio() {
        validSolicitud.setDocumentoIdentidad("");

        assertThatThrownBy(() -> solicitudUseCase.registrarSolicitud(validSolicitud))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El documento de identidad es obligatorio");
        
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el monto es negativo")
    void registrarSolicitud_MontoNegativo() {
        validSolicitud.setMonto(new BigDecimal("-100"));

        assertThatThrownBy(() -> solicitudUseCase.registrarSolicitud(validSolicitud))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El monto debe ser mayor a 0");
    }

    @Test
    @DisplayName("Debe listar las solicitudes en los estados permitidos")
    void listarSolicitudes_Exitoso() {
        // Arrange
        Solicitud sol1 = Solicitud.builder().estado(EstadoSolicitud.PENDIENTE_REVISION).build();
        Solicitud sol2 = Solicitud.builder().estado(EstadoSolicitud.RECHAZADA).build();
        when(solicitudRepository.findByEstados(anyList())).thenReturn(Arrays.asList(sol1, sol2));

        // Act
        List<Solicitud> resultado = solicitudUseCase.listarSolicitudesParaRevision();

        // Assert
        assertThat(resultado).hasSize(2);
        verify(solicitudRepository, times(1)).findByEstados(anyList());
    }

    @Test
    @DisplayName("Debe cambiar el estado de una solicitud a APROBADA")
    void cambiarEstado_Exitoso() {
        // Arrange
        Solicitud solicitudExistente = Solicitud.builder()
                .id(1L)
                .documentoIdentidad("123")
                .estado(EstadoSolicitud.PENDIENTE_REVISION)
                .build();
        
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitudExistente));
        when(solicitudRepository.save(any(Solicitud.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Solicitud resultado = solicitudUseCase.cambiarEstadoSolicitud(1L, EstadoSolicitud.APROBADA);

        // Assert
        assertThat(resultado.getEstado()).isEqualTo(EstadoSolicitud.APROBADA);
        verify(solicitudRepository, times(1)).save(any(Solicitud.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nuevo estado no es permitido")
    void cambiarEstado_EstadoInvalido() {
        // Arrange
        Solicitud solicitudExistente = Solicitud.builder().id(1L).build();
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitudExistente));

        // Act & Assert
        assertThatThrownBy(() -> solicitudUseCase.cambiarEstadoSolicitud(1L, EstadoSolicitud.PENDIENTE_REVISION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El estado debe ser APROBADA o RECHAZADA");
    }
}