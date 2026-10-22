package co.com.crediya.solicitudes;

import co.com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.solicitudes.usecase.solicitud.EvaluarSolicitudUseCase;
import co.com.crediya.solicitudes.usecase.solicitud.EvaluarSolicitudUseCaseImpl;
import co.com.crediya.solicitudes.usecase.solicitud.SolicitudUseCase;
import co.com.crediya.solicitudes.usecase.solicitud.SolicitudUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public SolicitudUseCase solicitudUseCase(SolicitudRepository solicitudRepository) {
        return new SolicitudUseCaseImpl(solicitudRepository);
    }

    @Bean
    public EvaluarSolicitudUseCase evaluarSolicitudUseCase() {
        return new EvaluarSolicitudUseCaseImpl();
    }
}