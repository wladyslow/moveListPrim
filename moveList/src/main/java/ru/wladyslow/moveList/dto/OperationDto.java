package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperationDto {

    private Long id;

    private String name;

    public OperationDto(String name) {
        this.name = name;
    }
}
