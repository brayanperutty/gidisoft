package com.ufps.gidisoft.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Seed {

    private final SeederRole seederRole;
    private final SeederUser seederUser;
    private final SeederAcademicPeriods seederAcademicPeriods;

    public void seed(){
        this.seederRole.seed();
        this.seederUser.seed();
        this.seederAcademicPeriods.seed();
    }
}
