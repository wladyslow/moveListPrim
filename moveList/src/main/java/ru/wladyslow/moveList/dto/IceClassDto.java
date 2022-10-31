package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IceClassDto {

    private Long id;

    private String name;

    public IceClassDto(String name) {
        this.name = name;
    }

    public boolean equals(IceClassDto iceClassDto) {
        return this.name.equals(iceClassDto.name);
    }
}
