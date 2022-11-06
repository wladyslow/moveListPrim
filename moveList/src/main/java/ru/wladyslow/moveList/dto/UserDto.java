package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.wladyslow.moveList.utils.State;
import ru.wladyslow.moveList.utils.UserStatus;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String firstName;

    private String lastName;

    private Long chatId;

    private UserStatus userStatus;

    private State botState;

    public UserDto(Long chatId) {
        this.chatId = chatId;
        this.botState = State.START;
        this.userStatus = UserStatus.INITIAL;
    }
}
