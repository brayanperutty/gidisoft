package com.ufps.gidisoft.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Seed {

    private final SeederRole seederRole;
    private final SeederUser seederUser;

    public void seed(){
        this.seederRole.seed();
        this.seederUser.seed();
    }
}
