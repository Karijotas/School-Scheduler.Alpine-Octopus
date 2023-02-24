package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper.toSubject;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper.toSubjectDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/subjects")
@Validated
public class SubjectController {
    public static Logger logger = LoggerFactory.getLogger(SubjectController.class);
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    @ResponseBody
    public List<SubjectEntityDto> getSubjects() {
        return subjectService.getAll().stream()
                .map(SubjectMapper::toSubjectEntityDto)
                .collect(toList());
    }

    @GetMapping(value = "/{subjectId}/modules")
    @ResponseBody
    public Set<Module> getAllModulesById(@PathVariable Long subjectId) {
        return subjectService.getAllModulesById(subjectId);
    }

    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<SubjectEntityDto> getPagedAllSubjects(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return subjectService.getPagedAllSubjects(page, pageSize);

    }

    @GetMapping(path = "page/name-filter/{nameText}")
    @ApiOperation(value = "Get Paged Subjects starting with", notes = "Returns list of Subjects starting with passed String")
    @ResponseBody
    public List<SubjectEntityDto> getPagedSubjectsByNameContaining(@PathVariable String nameText,
                                                                   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return subjectService.getPagedSubjectsByNameContaining(nameText, page, pageSize);
    }

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@Valid @RequestBody SubjectDto subjectDto) {
        if (subjectService.subjectNameIsUnique(toSubject(subjectDto))) {
            var createdSubject = subjectService.create(toSubject(subjectDto));
            return ok(toSubjectDto(createdSubject));
        } else {
            throw new SchedulerValidationException("Subject already exists", "Subject name", "Already exists", subjectDto.getName());
        }
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long subjectId) {
        var subjectDeleted = subjectService.deleteById(subjectId);

        if (subjectDeleted) {
            return ResponseEntity.noContent().build();
//            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable Long subjectId, @Valid @RequestBody SubjectDto subjectDto) {
        if (subjectService.subjectNameIsUnique(toSubject(subjectDto))) {
            var updatedSubject = subjectService.update(subjectId, toSubject(subjectDto));
            return ok(toSubjectDto(updatedSubject));
        } else {
            throw new SchedulerValidationException("Subject already exists", "Subject name", "Already exists", subjectDto.getName());
        }
    }

    @GetMapping(value = "/{subjectId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Subject> getSubject(@PathVariable Long subjectId) {
        var subjectOptional = subjectService.getById(subjectId);

        var responseEntity = subjectOptional
                .map(subject -> ok(subject))
                .orElseGet(() -> ResponseEntity.notFound().build());

        return responseEntity;
    }

    @PutMapping("/modules/{subjectId}")
    public ResponseEntity<SubjectDto> addModuleToSubject(@PathVariable Long subjectId, @Valid @RequestBody Long moduleId) {
        var updatedSubject = subjectService.addModuleToSubject(subjectId, moduleId);

        return ok(toSubjectDto(updatedSubject));
    }

    @PutMapping("/teachers/{subjectId}")
    public ResponseEntity<SubjectDto> addTeacherToSubject(@PathVariable Long subjectId, @Valid @RequestBody Long teacherId) {
        var updatedSubject = subjectService.addTeacherToSubject(subjectId, teacherId);

        return ok(toSubjectDto(updatedSubject));
    }

    @PutMapping("/rooms/{subjectId}")
    public ResponseEntity<SubjectDto> addRoomToSubject(@PathVariable Long subjectId, @Valid @RequestBody Long roomId) {
        var updatedSubject = subjectService.addRoomToSubject(subjectId, roomId);

        return ok(toSubjectDto(updatedSubject));
    }
}
