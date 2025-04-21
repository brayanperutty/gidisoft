package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.entities.formats.Proyect;
import com.ufps.gidisoft.repositories.formats.ProyectRepository;
import com.ufps.gidisoft.requests.formats.ProyectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProyectService {

    /*
     * Repositories
     */
    private final ProyectRepository proyectRepository;

    /*
     * Services
     */
    private final FormatService formatService;

    public Proyect createProyect(ProyectRequest proyectRequest) {
        return this.proyectRepository.save(new Proyect(proyectRequest,
                formatService.findFormatById(proyectRequest.getFormatId())));
    }
}
