package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.wladyslow.moveList.utils.State;
import ru.wladyslow.moveList.utils.UserStatus;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private Long chatId;

    private UserStatus userStatus;

    private State botState;

    public UserDto(Long chatId) {
        this.chatId = chatId;
        this.botState = State.START;
        this.userStatus = UserStatus.INITIAL;
    }
}
