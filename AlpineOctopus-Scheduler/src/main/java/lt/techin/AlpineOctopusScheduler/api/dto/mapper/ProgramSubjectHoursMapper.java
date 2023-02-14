package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ProgramDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramSubjectHoursDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramSubjectHoursDtoForList;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;

public class ProgramSubjectHoursMapper {

    public static ProgramSubjectHoursDto toProgramSubjectHoursDto(ProgramSubjectHours programSubjectHours) {
        var programSubjectHoursDto = new ProgramSubjectHoursDto();

        programSubjectHoursDto.setProgram(programSubjectHours.getProgram());
        programSubjectHoursDto.setSubject(programSubjectHours.getSubject());
        programSubjectHoursDto.setSubjectHours(programSubjectHours.getSubjectHours());


        return programSubjectHoursDto;
    }

    public static ProgramSubjectHours toProgramSubjectHours(ProgramSubjectHoursDto programSubjectHoursDto) {
        var programSubjectHours = new ProgramSubjectHours();

        programSubjectHours.setProgram(programSubjectHoursDto.getProgram());
        programSubjectHours.setSubject(programSubjectHoursDto.getSubject());
        programSubjectHours.setSubjectHours(programSubjectHoursDto.getSubjectHours());


        return programSubjectHours;
    }

    public static ProgramSubjectHoursDtoForList toProgramSubjectHoursDtoForList(ProgramSubjectHours programSubjectHours) {
        var programSubjectHoursDtoForList = new ProgramSubjectHoursDtoForList();

        programSubjectHoursDtoForList.setSubject(programSubjectHours.getSubject());
        programSubjectHoursDtoForList.setSubjectHours(programSubjectHours.getSubjectHours());


        return programSubjectHoursDtoForList;
    }

}
