package com.ufps.gidisoft.services.formats.general;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.formats.general.FormatUser;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.repositories.formats.FormatUserRepository;
import com.ufps.gidisoft.responses.format.FormatListDto;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormatUserService {

    /*
     * Repositories
     */
    private final FormatUserRepository formatUserRepository;
    private final UserService userService;

    public void createFormatUser(Format format, User user){
        this.formatUserRepository.save(new FormatUser(format, user));
    }

    public List<FormatListDto> findAllFormatsByPermissionAndStatus(User user, Long statusId) {
        List<FormatListDto> formats = new ArrayList<>();
        this.formatUserRepository.findByUser(user).stream().filter(formatUser -> formatUser.getFormat()
                .getStatus().getId().equals(statusId)).forEach(formatUser -> {
            Format format = formatUser.getFormat();
            FormatListDto formatListDto = new FormatListDto(format);
            formats.add(formatListDto);
        });
        return formats;
    }

    public List<UsersDto> findAllUsersByPermission() {
        List<FormatUser> formatUsers = this.formatUserRepository.findAll();
        List<UsersDto> usersDtos = this.userService.findAllUsers();

        List<UsersDto> filteredUsers = usersDtos.stream()
                .filter(usersDto -> formatUsers.stream()
                        .noneMatch(formatUser -> formatUser.getUser().getUsercode().equals(usersDto.getUsercode())))
                .toList();

        return filteredUsers;
    }

    public void deleteByFormatId(Long formatId){
        this.formatUserRepository.deleteAllByFormatId(formatId);
    }
}
