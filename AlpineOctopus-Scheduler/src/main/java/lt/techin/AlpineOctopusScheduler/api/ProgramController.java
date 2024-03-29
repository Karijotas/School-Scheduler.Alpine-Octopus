package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramTestDto;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.service.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper.*;
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


    @GetMapping(path = "/all")
    @ResponseBody
    public List<ProgramTestDto> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping
    @ResponseBody
    public List<ProgramEntityDto> getAvailablePrograms() {
        return programService.getAllAvailablePrograms();
    }


    @GetMapping(path = "/archive/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ProgramEntityDto> getDeletedPrograms() {
        return programService.getAllDeletedPrograms();
    }


    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ProgramEntityDto> getPagedAvailablePrograms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        return programService.getAllAvailablePagedPrograms(page, pageSize);
    }

    @GetMapping(path = "/archive/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ProgramEntityDto> getPagedDeletedPrograms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return programService.getAllDeletedPagedPrograms(page, pageSize);
    }

//    @GetMapping(path = "/page/all", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//
//    public List<ProgramEntityDto> getPagedAllPrograms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
//
//                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return programService.getAllAvailablePrograms();
//
//    }

//    @GetMapping(path = "/starting-with/{nameText}")
//
//    @ApiOperation(value = "Get Programs starting with", notes = "Returns list of Programs starting with passed String")
//    @ResponseBody
//    public List<ProgramEntityDto> getProgramsByNameContaining(@PathVariable String nameText) {
//        return programService.getProgramsByNameContaining(nameText);
//    }

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

//    @DeleteMapping("/{programId}")
//    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
//        logger.info("Attempt to delete Program by id: {}", programId);
//        List<ProgramSubjectHours> pshList = programSubjectHoursRepository.findAll().stream().filter(psh -> psh.getProgram()
//                .getId().equals(programId)).collect(toList());
//        if (!pshList.isEmpty()) {
//            for (ProgramSubjectHours psh : pshList) {
//                programSubjectHoursRepository.deleteById(psh.getId());
//            }
//        }
//        boolean deleted = programService.deleteById(programId);
//        if (deleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @DeleteMapping("/programsSubjects/{programSubjectId}")
//    public ResponseEntity<Void> deleteProgramSubjectById(@PathVariable Long programSubjectId) {
//        logger.info("Attempt to delete Program by id: {}", programSubjectId);
//
//        boolean deleted = programService.deleteSubjectInProgramById(programSubjectId);
//        if (deleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

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
    public ResponseEntity<ProgramEntityDto> createProgram(@Valid @RequestBody ProgramEntityDto programDto) {
        if (programService.programNameIsUnique(toProgram(programDto))) {
            var createdProgram = programService.create(toProgram(programDto));
            return ok(toProgramEntityDto(createdProgram));
        } else {
            throw new SchedulerValidationException("Program already exists", "Program name", "Already exists", programDto.getName());
        }
    }

    @PatchMapping("/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable Long programId, @Valid @RequestBody ProgramDto programDto) {

        var updatedProgram = programService.update(programId, toProgram(programDto));
        return ok(toProgramDto(updatedProgram));
    }

    @PatchMapping("/delete/{programId}")
    public ResponseEntity<ProgramEntityDto> removeProgram(@PathVariable Long programId) {
        var updatedProgram = programService.deleteProgram(programId);
        return ok(updatedProgram);
    }

    @PatchMapping("/restore/{programId}")
    public ResponseEntity<ProgramEntityDto> restoreProgram(@PathVariable Long programId) {
        var updatedProgram = programService.restoreProgram(programId);
        return ok(updatedProgram);
    }

//    @PatchMapping("/programSubjects/{programSubjectId}")
//    public ResponseEntity<ProgramSubjectHoursDtoForList> updateProgramSubjectHours(@PathVariable Long programSubjectId, @Valid @RequestBody ProgramSubjectHoursDto programSubjectHoursDto) {
//        var updatedProgramSubjectHours = programService.updateProgramSubjectHours(programSubjectId, toProgramSubjectHours(programSubjectHoursDto));
//
//        return ok(toProgramSubjectHoursDtoForList(updatedProgramSubjectHours));
//    }

    @PostMapping(value = "/{programId}/subjects/{subjectId}/{hours}/newSubjectsWithHours")
    @ResponseBody
    public ProgramSubjectHours addSubjectAndHoursToProgram(@PathVariable Long programId, @PathVariable Integer hours, @PathVariable Long subjectId) {
        return programService.addSubjectAndHoursToProgram(programId, subjectId, hours);
    }

//    @PostMapping(value = "/{programId}/subjects/newSubjectsWithHoursList")
//    @ResponseBody
//    public List<ProgramSubjectHoursDtoForList> addAllSubjectsAndHoursToProgram(@PathVariable Long programId, @Valid @RequestBody List<ProgramSubjectHoursForCreate> programSubjectHoursForCreateList) {
//
//        programSubjectHoursForCreateList.forEach(sh -> programService
//                .addSubjectAndHoursToProgram(programId, sh.getSubjectId(), sh.getSubjectHour()));
//        return programService.getAllSubjectsInProgramByProgramId(programId).stream()
//                .map(ProgramSubjectHoursMapper::toProgramSubjectHoursDtoForList)
//                .collect(toList());
//    }

    @GetMapping(value = "/{programId}/availableSubjects", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Subject> getAvailableSubjectsInProgram(@PathVariable Long programId) {
        List<Subject> psh = programService.getAllSubjectsInProgramByProgramId(programId)
                .stream().map(ProgramSubjectHours::getSubject).collect(toList());
        List<Subject> subjectList = programService.getFreeSubjects(programId);
        List<Subject> availableSubjects = subjectList.stream().filter(sub -> !psh.contains(sub)).collect(toList());

        return availableSubjects;
    }
}
