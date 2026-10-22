package co.com.crediya.solicitudes.api;

import co.com.crediya.solicitudes.model.solicitud.Solicitud;
import co.com.crediya.solicitudes.usecase.solicitud.EvaluarSolicitudUseCase;
import co.com.crediya.solicitudes.usecase.solicitud.SolicitudUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/solicitud")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudUseCase solicitudUseCase;
    private final EvaluarSolicitudUseCase evaluarSolicitudUseCase;

    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> registrarSolicitud(@Valid @RequestBody SolicitudRequestDTO request) {
        Solicitud solicitudDomain = Solicitud.builder()
                .documentoIdentidad(request.getDocumentoIdentidad())
                .monto(request.getMonto()).plazo(request.getPlazo())
                .tipoPrestamo(request.getTipoPrestamo()).build();

        Solicitud saved = solicitudUseCase.registrarSolicitud(solicitudDomain);
        return new ResponseEntity<>(mapToResponse(saved), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudResponseDTO>> listarSolicitudes() {
        List<SolicitudResponseDTO> response = solicitudUseCase.listarSolicitudesParaRevision()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // SOLUCIÓN: Agregar (name = "id") en el PathVariable
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> cambiarEstado(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody CambiarEstadoRequestDTO request) {
        
        Solicitud actualizada = solicitudUseCase.cambiarEstadoSolicitud(id, request.getEstado());
        return new ResponseEntity<>(mapToResponse(actualizada), HttpStatus.OK);
    }

        @PostMapping("/evaluar")
    public ResponseEntity<String> evaluarSolicitud(@RequestBody EvaluarRequestDTO request) {
        Solicitud solicitud = Solicitud.builder()
                .monto(request.getMonto())
                .plazo(request.getPlazo())
                .build();
        
        String resultado = evaluarSolicitudUseCase.evaluarSolicitud(
                solicitud,
                request.getSalarioBase(),
                request.getDeudaActual(),
                request.getTasaInteres()
        );
        
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    private SolicitudResponseDTO mapToResponse(Solicitud s) {
        return SolicitudResponseDTO.builder()
                .id(s.getId()).documentoIdentidad(s.getDocumentoIdentidad())
                .monto(s.getMonto()).plazo(s.getPlazo())
                .tipoPrestamo(s.getTipoPrestamo()).estado(s.getEstado())
                .fechaSolicitud(s.getFechaSolicitud()).build();
    }
}