package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.HolidayDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayTestDto;
import lt.techin.AlpineOctopusScheduler.model.Holiday;


public class HolidayMapper {
    public static HolidayDto toHolidayDto(Holiday holiday) {
        var holidayDto = new HolidayDto();

        holidayDto.setName(holiday.getName());
        holidayDto.setStartDate(holiday.getStartDate());
        holidayDto.setEndDate(holiday.getEndDate());
        holidayDto.setReccuring(holiday.getReccuring());

        return holidayDto;
    }

    public static Holiday toHoliday(HolidayDto holidayDto) {
        var holiday = new Holiday();

        holiday.setName(holidayDto.getName());
        holiday.setStartDate(holidayDto.getStartDate());
        holiday.setEndDate(holidayDto.getEndDate());
        holiday.setReccuring(holidayDto.getReccuring());


        return holiday;
    }

    public static HolidayEntityDto toHolidayEntityDto(Holiday holiday) {
        var holidayEntityDto = new HolidayEntityDto();

        holidayEntityDto.setId(holiday.getId());
        holidayEntityDto.setName(holiday.getName());
        holidayEntityDto.setStartDate(holiday.getStartDate());
        holidayEntityDto.setEndDate(holiday.getEndDate());
        holidayEntityDto.setReccuring(holiday.getReccuring());

        return holidayEntityDto;
    }

    public static Holiday toModule(HolidayEntityDto holidayEntityDto) {
        var holiday = new Holiday();

        holiday.setId(holidayEntityDto.getId());
        holiday.setName(holidayEntityDto.getName());
        holiday.setStartDate(holidayEntityDto.getStartDate());
        holiday.setEndDate(holidayEntityDto.getEndDate());
        holiday.setReccuring(holidayEntityDto.getReccuring());


        return holiday;
    }

    public static HolidayTestDto toHolidayTestDto(Holiday holiday) {
        var holidayDto = new HolidayTestDto();

        holidayDto.setId(holiday.getId());
        return holidayDto;
    }
}
