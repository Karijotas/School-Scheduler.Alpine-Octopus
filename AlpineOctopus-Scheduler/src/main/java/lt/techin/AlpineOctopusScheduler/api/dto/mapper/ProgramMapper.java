package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ProgramDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramTestDto;
import lt.techin.AlpineOctopusScheduler.model.Program;

public class ProgramMapper {

    public static ProgramDto toProgramDto(Program program) {
        var programDto = new ProgramDto();

        programDto.setName(program.getName());
        programDto.setDescription(program.getDescription());


        return programDto;
    }

    public static Program toProgram(ProgramDto programDto) {
        var program = new Program();

        program.setName(programDto.getName());
        program.setDescription(programDto.getDescription());
//        program.setModifiedDate(programDto.getModifiedDate());
//        program.setCreatedDate(programDto.getCreatedDate());

        return program;
    }


    public static ProgramEntityDto toProgramEntityDto(Program program) {
        var programDto = new ProgramEntityDto();

        programDto.setId(program.getId());
        programDto.setName(program.getName());
        programDto.setDescription(program.getDescription());
//        programDto.setModifiedDate(program.getModifiedDate());
//        programDto.setCreatedDate(program.getCreatedDate());
        programDto.setDeleted(program.getDeleted());


        return programDto;
    }

    public static Program toProgram(ProgramEntityDto programEntityDto) {
        var program = new Program();

        program.setId(programEntityDto.getId());
        program.setName(programEntityDto.getName());
        program.setDescription(programEntityDto.getDescription());
//        program.setModifiedDate(programEntityDto.getModifiedDate());
//        program.setCreatedDate(programEntityDto.getCreatedDate());
        program.setDeleted(programEntityDto.getDeleted());

        return program;
    }

    public static ProgramTestDto toProgramTestDto(Program program) {
        var programDto = new ProgramTestDto();

        programDto.setId(program.getId());
        return programDto;
    }

}
