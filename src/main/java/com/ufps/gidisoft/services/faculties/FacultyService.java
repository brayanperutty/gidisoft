package com.ufps.gidisoft.services.faculties;

import com.ufps.gidisoft.entities.faculties.Faculty;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.repositories.faculties.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {

    /*
     * Repositories
     */
    private final FacultyRepository facultyRepository;

    public List<Faculty> findAllFaculties() {
        return this.facultyRepository.findAll();
    }

    public Faculty findById(Long id) {
        return this.facultyRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ExceptionCodeEnum.FAC01.getMessage()));
    }
}
