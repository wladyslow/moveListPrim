package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wladyslow.moveList.dto.PortScheduleDto;
import ru.wladyslow.moveList.services.FindLastPortSchedule;
import ru.wladyslow.moveList.services.PortScheduleService;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindLastPortScheduleImpl implements FindLastPortSchedule {

    private final PortScheduleService portScheduleService;

    @Override
    public PortScheduleDto getPortScheduleForBot(){
        Optional<PortScheduleDto> portScheduleDtoOpt = portScheduleService.findByScheduleDate(LocalDate.now());
        if (portScheduleDtoOpt.isEmpty()) {
            portScheduleDtoOpt = portScheduleService.findByScheduleDate(LocalDate.now().minusDays(1));
            if (!portScheduleDtoOpt.isEmpty()){
                return portScheduleDtoOpt.get();
            } else return null;
        }
        return portScheduleDtoOpt.get();
    }
}
