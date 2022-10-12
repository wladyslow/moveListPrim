package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {
    private Long id;

    private String name;

    public PointDto(String name) {
        this.name = name;
    }
}
