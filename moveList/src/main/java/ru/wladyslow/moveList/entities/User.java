package ru.wladyslow.moveList.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.wladyslow.moveList.utils.State;
import ru.wladyslow.moveList.utils.UserStatus;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Long chatId;

    @Column
    private UserStatus userStatus;

    @Column
    private State botState;

    public User(Long chatId) {
        this.chatId = chatId;
        this.botState = State.START;
        this.userStatus = UserStatus.INITIAL;
    }
}
