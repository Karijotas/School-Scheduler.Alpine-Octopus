package lt.techin.AlpineOctopusScheduler.api.dto.mapper;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHoursList;
import lt.techin.AlpineOctopusScheduler.api.dto.*;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHoursList;

public class ProgramSubjectHoursMapper {

    public static ProgramSubjectHoursDto toProgramSubjectHoursDto(ProgramSubjectHours programSubjectHours) {
        var programSubjectHoursDto = new ProgramSubjectHoursDto();

        programSubjectHoursDto.setId(programSubjectHours.getId());
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

        programSubjectHoursDtoForList.setId(programSubjectHours.getId());
        programSubjectHoursDtoForList.setSubject(programSubjectHours.getSubject());
        programSubjectHoursDtoForList.setSubjectHours(programSubjectHours.getSubjectHours());


        return programSubjectHoursDtoForList;
    }

//    public static ProgramSubjectHourListDto  toProgramSubjectHourListDto(ProgramSubjectHoursList programSubjectHoursList, ProgramSubjectHoursForCreate programSubjectHoursForCreate){
//        var programSubjectHourListDto = new ProgramSubjectHourListDto(programSubjectHoursForCreate);
//
//        programSubjectHourListDto.setId(programSubjectHoursList.getId());
//        programSubjectHourListDto.setSubjectHourList();
//
//        return programSubjectHourListDto;
//    }

    public static ProgramSubjectHoursList toProgramSubjectHourList(ProgramSubjectHourListDto programSubjectHoursListDto){
       var programSubjectHoursList = new ProgramSubjectHoursList();

        programSubjectHoursList.setId(programSubjectHoursListDto.getId());
        programSubjectHoursList.setPshForCreate_id(programSubjectHoursListDto.getSubjectHourList().getSubjectId());
   return programSubjectHoursList;
    }
}
