package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.entities.formats.ProyectStatus;
import com.ufps.gidisoft.repositories.formats.ProyectStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProyectStatusService {

    /*
     * Repositories
     */
    private final ProyectStatusRepository proyectStatusRepository;

    public void createProyectStatus(String status) {
        ProyectStatus proyectStatus = new ProyectStatus();
        proyectStatus.setName(status);
        this.proyectStatusRepository.save(proyectStatus);
    }

    public boolean existsProyectStatus(String status) {
        return proyectStatusRepository.existsByName(status);
    }
}
