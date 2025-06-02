package com.ufps.gidisoft.services.formats.others;

import com.ufps.gidisoft.entities.formats.others.OtherActivity;
import com.ufps.gidisoft.entities.formats.others.OtherActivityUser;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.repositories.formats.others.OtherActivityUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OtherActivityUserService {

    /*
     * Repositories
     */
    private final OtherActivityUserRepository otherActivityUserRepository;

    @Transactional
    public void createOtherActivityUser(OtherActivity otherActivity, User user) {
        this.otherActivityUserRepository.save(new OtherActivityUser(otherActivity, user));
    }

    @Transactional
    public void deleteByOtherActivityId(Long otherActivityId) {
        this.otherActivityUserRepository.deleteByOtherActivityId(otherActivityId);
    }

    public boolean validateExistOtherActivityAndUser(OtherActivity otherActivity, User user) {
        return this.otherActivityUserRepository.existsByOtherActivityAndUser(otherActivity, user);
    }

    public List<String> findUserByOtherActivity(Long otherActivityId) {
        List<String> users = new ArrayList<>();
        this.otherActivityUserRepository.findByOtherActivityId(otherActivityId).forEach(otherActivityUser ->
                users.add(otherActivityUser.getUser().getName())
        );
        return users;
    }
}
