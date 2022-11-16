package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AisDataDto {

    private Long id;

    private Long vesselId;

    private String etaDestination;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime etaDateAndTime;

    private String navigationStatus;

    private double currentDraft;

    private double currentSpeed;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime lastUpdated;

    public AisDataDto(Long vesselId, String etaDestination,
                      LocalDateTime etaDateAndTime,
                      String navigationStatus, double currentDraft,
                      double currentSpeed, LocalDateTime lastUpdated) {
        this.vesselId = vesselId;
        this.etaDestination = etaDestination;
        this.etaDateAndTime = etaDateAndTime;
        this.navigationStatus = navigationStatus;
        this.currentDraft = currentDraft;
        this.currentSpeed = currentSpeed;
        this.lastUpdated = lastUpdated;
    }
}
