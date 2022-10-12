package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgentDto {

    private Long id;

    private String name;

    public AgentDto(String name) {
        this.name = name;
    }
}


