package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.api.dto.*;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.service.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper.toProgram;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper.toProgramDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/programs")
@Validated
public class ProgramController {

    public static Logger logger = LoggerFactory.getLogger(ProgramController.class);
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }



    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ProgramEntityDto> getPrograms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                              @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                              @RequestParam(value = "sortDir", defaultValue = "asc", required = false) Sort.Direction sortDir) {


        return programService.getAll(page, pageSize, sortBy, sortDir).stream()
                .map(ProgramMapper::toProgramEntityDto)
                .collect(toList());
    }

    @GetMapping(value = "/{programId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Program> getProgram(@PathVariable Long programId) {
        var programOptional = programService.getById(programId);

        return programOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping(value = "/{programId}/subjects", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ProgramSubjectHoursDtoForList> getAllSubjectsInProgram(@PathVariable Long programId) {
        return programService.getAllSubjectsInProgram(programId);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
        logger.info("Attempt to delete Program by id: {}", programId);

        boolean deleted = programService.deleteById(programId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramDto programDto) {
        var createdProgram = programService.create(toProgram(programDto));

        return ok(toProgramDto(createdProgram));
    }

    @PatchMapping("/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable Long programId, @RequestBody ProgramDto programDto) {
        var updatedProgram = programService.update(programId, toProgram(programDto));

        return ok(toProgramDto(updatedProgram));
    }

    @PostMapping(value = "/{programId}/subjects/newSubjectsWithHours")
    @ResponseBody
    public ProgramSubjectHours addSubjectAndHoursToProgram(@PathVariable Long programId, @RequestParam Integer subjectHours, @RequestParam Long subjectId) {
        return programService.addSubjectAndHoursToProgram(programId, subjectId, subjectHours);
    }


}
