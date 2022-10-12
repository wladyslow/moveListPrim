package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PilotDto {

    private Long id;

    private String name;

    private String engName;

    public PilotDto(String name) {
        this.name = name;
    }
}
