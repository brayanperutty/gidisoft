package com.ufps.gidisoft.seeders;

import com.ufps.gidisoft.services.formats.ProyectStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeederProyectStatus {

    private final ProyectStatusService proyectStatusService;

    public void seed() {
        getProyectStatus(proyectStatusService);
    }

    private static void getProyectStatus(ProyectStatusService proyectStatusService) {
        if (proyectStatusService.existsProyectStatus("Borrador") &&
                proyectStatusService.existsProyectStatus("Publicado")) {
            proyectStatusService.createProyectStatus("Borrador");
            proyectStatusService.createProyectStatus("Publicado");
        }
    }
}
