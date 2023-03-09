package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleTestDto;
import lt.techin.AlpineOctopusScheduler.model.Schedule;

public class ScheduleMapper {

    public static ScheduleDto toScheduleDto(Schedule schedule) {
        var scheduleDto = new ScheduleDto();

        scheduleDto.setName(schedule.getName());
        scheduleDto.setStartingDate(schedule.getStartingDate());
        scheduleDto.setPlannedTillDate(schedule.getPlannedTillDate());
        scheduleDto.setStatus(schedule.getStatus());
        scheduleDto.setLessons(schedule.getLessons());
        scheduleDto.setGroupName(schedule.getGroup().getName());
        scheduleDto.setGroupId(schedule.getGroup().getId());
        scheduleDto.setShiftName(schedule.getGroup().getShift().getName());
        scheduleDto.setShiftId(schedule.getGroup().getShift().getId());

        return scheduleDto;
    }

    public static Schedule toSchedule(ScheduleDto scheduleDto) {
        var schedule = new Schedule();

        schedule.setName(scheduleDto.getName());
        schedule.setStartingDate(scheduleDto.getStartingDate());
        schedule.setPlannedTillDate(scheduleDto.getPlannedTillDate());
        schedule.setStatus(scheduleDto.getStatus());
        schedule.setLessons(scheduleDto.getLessons());


        return schedule;
    }

    public static ScheduleEntityDto toScheduleEntityDto(Schedule schedule) {
        var scheduleEntityDto = new ScheduleEntityDto();

        scheduleEntityDto.setId(schedule.getId());
        scheduleEntityDto.setName(schedule.getName());
        scheduleEntityDto.setStartingDate(schedule.getStartingDate());
        scheduleEntityDto.setPlannedTillDate(schedule.getPlannedTillDate());
        scheduleEntityDto.setStatus(schedule.getStatus());
        scheduleEntityDto.setLessons(schedule.getLessons());
        scheduleEntityDto.setGroupName(schedule.getGroup().getName());
        scheduleEntityDto.setGroupId(schedule.getGroup().getId());
        scheduleEntityDto.setShiftName(schedule.getGroup().getShift().getName());
        scheduleEntityDto.setShiftId(schedule.getGroup().getShift().getId());

        return scheduleEntityDto;
    }

    public static Schedule toSchedule(ScheduleEntityDto scheduleEntityDto) {
        var schedule = new Schedule();

        schedule.setId(scheduleEntityDto.getId());
        schedule.setName(scheduleEntityDto.getName());
        schedule.setStartingDate(scheduleEntityDto.getStartingDate());
        schedule.setPlannedTillDate(scheduleEntityDto.getPlannedTillDate());
        schedule.setModifiedDate(scheduleEntityDto.getModifiedDate());
        schedule.setCreatedDate(scheduleEntityDto.getCreatedDate());
        schedule.setStatus(scheduleEntityDto.getStatus());
        schedule.setLessons(scheduleEntityDto.getLessons());


        return schedule;
    }

    public static ScheduleTestDto toScheduleTestDto(Schedule schedule) {
        var scheduleDto = new ScheduleTestDto();

        scheduleDto.setId(schedule.getId());

        return scheduleDto;
    }
}
