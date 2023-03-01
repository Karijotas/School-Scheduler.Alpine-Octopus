package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ShiftDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Shift;

public class ShiftMapper {

    public static ShiftDto toShiftDto(Shift shift) {
        var shiftDto = new ShiftDto();

        shiftDto.setName(shift.getName());
        shiftDto.setStarts(shift.getStarts());
        shiftDto.setEnds(shift.getEnds());
        shiftDto.setCreatedDate(shift.getCreatedDate());
        shiftDto.setModifiedDate(shift.getModifiedDate());

        return shiftDto;
    }

    public static Shift toShift(ShiftDto shiftDto) {
        var shift = new Shift();

        shift.setName(shiftDto.getName());
        shift.setStarts(shiftDto.getStarts());
        shift.setEnds(shiftDto.getEnds());
        shift.setCreatedDate(shiftDto.getCreatedDate());
        shift.setModifiedDate(shiftDto.getModifiedDate());

        return shift;
    }

    public static ShiftEntityDto toShiftEntityDto(Shift shift) {
        var shiftEntityDto = new ShiftEntityDto();
        shiftEntityDto.setId(shift.getId());
        shiftEntityDto.setName(shift.getName());
        shiftEntityDto.setStarts(shift.getStarts());
        shiftEntityDto.setEnds(shift.getEnds());
        shiftEntityDto.setCreatedDate(shift.getCreatedDate());
        shiftEntityDto.setModifiedDate(shift.getModifiedDate());

        return shiftEntityDto;
    }

    public static Shift toShift(ShiftEntityDto shiftEntityDto) {
        var shift = new Shift();
        shift.setId(shiftEntityDto.getId());

        shift.setName(shiftEntityDto.getName());
        shift.setStarts(shiftEntityDto.getStarts());
        shift.setEnds(shiftEntityDto.getEnds());
        shift.setCreatedDate(shiftEntityDto.getCreatedDate());
        shift.setModifiedDate(shiftEntityDto.getModifiedDate());

        return shift;
    }
}
