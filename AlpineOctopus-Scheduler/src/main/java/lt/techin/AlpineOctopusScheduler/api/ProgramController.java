package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.*;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramSubjectHoursMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramSubjectHourListRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramSubjectHoursRepository;
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import lt.techin.AlpineOctopusScheduler.service.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper.toProgram;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper.toProgramDto;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper.toProgramEntityDto;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramSubjectHoursMapper.*;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/programs")
@Validated
public class ProgramController {

    public static Logger logger = LoggerFactory.getLogger(ProgramController.class);
    private final ProgramService programService;
    private final ProgramSubjectHoursRepository programSubjectHoursRepository;
    private final ProgramRepository programRepository;
    private final SubjectRepository subjectRepository;
    private final ProgramSubjectHourListRepository programSubjectHourListRepository;

    public ProgramController(ProgramService programService,
                             ProgramSubjectHoursRepository programSubjectHoursRepository,
                             ProgramRepository programRepository,
                             SubjectRepository subjectRepository,
                             ProgramSubjectHourListRepository programSubjectHourListRepository) {
        this.programService = programService;
        this.programSubjectHoursRepository = programSubjectHoursRepository;
        this.programRepository = programRepository;
        this.subjectRepository = subjectRepository;
        this.programSubjectHourListRepository = programSubjectHourListRepository;
    }


    @GetMapping
    @ResponseBody
    public List<ProgramEntityDto> getPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody

    public List<ProgramEntityDto> getPagedAllPrograms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,

                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {


        return programService.getPagedAllPrograms(page, pageSize);

    }

    @GetMapping(path = "/starting-with/{nameText}")

    @ApiOperation(value = "Get Programs starting with", notes = "Returns list of Programs starting with passed String")
    @ResponseBody
    public List<ProgramEntityDto> getProgramsByNameContaining(@PathVariable String nameText) {
        return programService.getProgramsByNameContaining(nameText);
    }

    @GetMapping(path = "page/name-filter/{nameText}")
    @ApiOperation(value = "Get Paged Programs starting with", notes = "Returns list of Programs starting with passed String")
    @ResponseBody
    public List<ProgramEntityDto> getPagedProgramsByNameContaining(@PathVariable String nameText,
                                                                   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return programService.getPagedProgramsByNameContaining(nameText, page, pageSize);
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
    public List<ProgramSubjectHours> getAllSubjectsInProgram(@PathVariable Long programId) {
        return programService.getAllSubjectsInProgramByProgramId(programId);
    }

    @GetMapping(value = "/{programId}/subjects/hours", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Integer getSumOfHoursByProgramId(@PathVariable Long programId) {
        int sumOfHours = 0;
        return sumOfHours = programService.getAllSubjectsInProgramByProgramId(programId).stream()
                .map(ProgramSubjectHours::getSubjectHours).reduce(0, Integer::sum);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
        logger.info("Attempt to delete Program by id: {}", programId);
        List<ProgramSubjectHours> pshList = programSubjectHoursRepository.findAll().stream().filter(psh -> psh.getProgram()
                .getId().equals(programId)).collect(toList());
        if (!pshList.isEmpty()) {
            for (ProgramSubjectHours psh : pshList) {
                programSubjectHoursRepository.deleteById(psh.getId());
            }
        }
        boolean deleted = programService.deleteById(programId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/programsSubjects/{programSubjectId}")
    public ResponseEntity<Void> deleteProgramSubjectById(@PathVariable Long programSubjectId) {
        logger.info("Attempt to delete Program by id: {}", programSubjectId);

        boolean deleted = programService.deleteSubjectInProgramById(programSubjectId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{programId}/subjects/{subjectId}/{hours}")
    public ResponseEntity<Void> deleteSubjectFromProgramByProgramIdSubjectIdHours(@PathVariable Long programId, @PathVariable Long subjectId, @PathVariable Integer hours) {

        boolean deleted = programService.deleteSubjectInProgramById(programId, subjectId, hours);
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

    @PatchMapping("/programSubjects/{programSubjectId}")
    public ResponseEntity<ProgramSubjectHoursDtoForList> updateProgramSubjectHours(@PathVariable Long programSubjectId, @RequestBody ProgramSubjectHoursDto programSubjectHoursDto) {
        var updatedProgramSubjectHours = programService.updateProgramSubjectHours(programSubjectId, toProgramSubjectHours(programSubjectHoursDto));

        return ok(toProgramSubjectHoursDtoForList(updatedProgramSubjectHours));
    }

    @PostMapping(value = "/{programId}/subjects/{subjectId}/{hours}/newSubjectsWithHours")
    @ResponseBody
    public ProgramSubjectHours addSubjectAndHoursToProgram(@PathVariable Long programId, @PathVariable Integer hours,@PathVariable Long subjectId) {
        return programService.addSubjectAndHoursToProgram(programId, subjectId, hours);
    }

    @PostMapping(value = "/{programId}/subjects/newSubjectsWithHoursList")
    @ResponseBody
    public List<ProgramSubjectHoursDtoForList> addAllSubjectsAndHoursToProgram(@PathVariable Long programId, @RequestBody List<ProgramSubjectHoursForCreate> programSubjectHoursForCreateList){

        programSubjectHoursForCreateList.forEach(sh -> programService
               .addSubjectAndHoursToProgram(programId, sh.getSubjectId(), sh.getSubjectHour()));
               return programService.getAllSubjectsInProgramByProgramId(programId).stream()
                .map(ProgramSubjectHoursMapper::toProgramSubjectHoursDtoForList)
                .collect(toList());
    }


}
