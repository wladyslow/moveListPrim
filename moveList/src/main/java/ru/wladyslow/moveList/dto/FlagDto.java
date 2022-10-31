package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlagDto {

    private Long id;

    private String rusName;

    private String engName;

    public FlagDto(String rusName) {
        this.rusName = rusName;
    }
    public boolean equals(FlagDto flagDto) {
        return this.rusName.equals(flagDto.rusName);
    }
}


