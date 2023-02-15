package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ProgramDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.List;
import java.util.Objects;

public class ProgramMapper {

    public static ProgramDto toProgramDto(Program program) {
        var programDto = new ProgramDto();

        programDto.setName(program.getName());
        programDto.setDescription(program.getDescription());
        programDto.setModifiedDate(program.getModifiedDate());
        programDto.setCreatedDate(program.getCreatedDate());

        return programDto;
    }

    public static Program toProgram(ProgramDto programDto) {
        var program = new Program();

        program.setName(programDto.getName());
        program.setDescription(programDto.getDescription());
        program.setModifiedDate(programDto.getModifiedDate());
        program.setCreatedDate(programDto.getCreatedDate());

        return program;
    }


    public static ProgramEntityDto toProgramEntityDto(Program program) {
        var programDto = new ProgramEntityDto();

        programDto.setId(program.getId());
        programDto.setName(program.getName());
        programDto.setDescription(program.getDescription());
        programDto.setModifiedDate(program.getModifiedDate());
        programDto.setCreatedDate(program.getCreatedDate());


        return programDto;
    }

    public static Program toProgram(ProgramEntityDto programEntityDto) {
        var program = new Program();

        program.setId(programEntityDto.getId());
        program.setName(programEntityDto.getName());
        program.setDescription(programEntityDto.getDescription());
        program.setModifiedDate(programEntityDto.getModifiedDate());
        program.setCreatedDate(programEntityDto.getCreatedDate());

        return program;
    }
}
