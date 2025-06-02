package com.ufps.gidisoft.seeders;

import com.ufps.gidisoft.services.formats.projects.ProjectStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeederProyectStatus {

    private final ProjectStatusService projectStatusService;

    public void seed() {
        getProyectStatus(projectStatusService);
    }

    private static void getProyectStatus(ProjectStatusService projectStatusService) {
        if (!projectStatusService.existsProyectStatus("Borrador") &&
                !projectStatusService.existsProyectStatus("Enviado")) {
            projectStatusService.createProyectStatus("Borrador");
            projectStatusService.createProyectStatus("Enviado");
        }
    }
}
