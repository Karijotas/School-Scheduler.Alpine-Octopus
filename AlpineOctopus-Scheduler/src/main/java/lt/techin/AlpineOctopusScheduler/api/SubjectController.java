package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectTestDto;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import lt.techin.AlpineOctopusScheduler.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper.*;
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
    public List<SubjectEntityDto> getAvailableSubject() {
        return subjectService.getAllAvailableSubjects();
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<SubjectTestDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping(path = "/archive", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<SubjectEntityDto> getDeletedSubject() {
        return subjectService.getAllDeletedSubjects();
    }


    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<SubjectEntityDto> getPagedAvailableSubjects(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return subjectService.getAllAvailablePagedSubjects(page, pageSize);
    }

    @GetMapping(path = "/archive/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<SubjectEntityDto> getPagedDeletedPrograms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return subjectService.getAllDeletedPagedSubjects(page, pageSize);
    }


    @PatchMapping("/delete/{subjectId}")
    public ResponseEntity<Subject> removeSubject(@PathVariable Long subjectId) {
        var updatedSubject = subjectService.deleteSubject(subjectId);
        return ok(updatedSubject);
    }

    @PatchMapping("/restore/{subjectId}")
    public ResponseEntity<Subject> restoreSubject(@PathVariable Long subjectId) {
        var updatedSubject = subjectService.restoreSubject(subjectId);
        return ok(updatedSubject);
    }


    @GetMapping(value = "/{subjectId}/modules")
    @ResponseBody
    public Set<Module> getAllModulesById(@PathVariable Long subjectId) {
        return subjectService.getAllModulesById(subjectId);
    }

    @GetMapping(value = "/{subjectId}/rooms")
    @ResponseBody
    public Set<Room> getAllRoomsById(@PathVariable Long subjectId) {
        return subjectService.getAllRoomsById(subjectId);
    }


    @GetMapping(path = "page/name-filter/{nameText}")
    @ApiOperation(value = "Get Paged Subjects starting with", notes = "Returns list of Subjects starting with passed String")
    @ResponseBody
    public List<SubjectEntityDto> getPagedSubjectsByNameContaining(@PathVariable String nameText,
                                                                   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return subjectService.getPagedSubjectsByNameContaining(nameText, page, pageSize);
    }


    @GetMapping(path = "page/module-filter/{moduleText}")
    @ApiOperation(value = "Get Paged Subjects starting with", notes = "Returns list of Subjects starting with passed String")
    @ResponseBody
    public List<SubjectEntityDto> getPagedSubjectsByModuleContaining(@PathVariable String moduleText) {
        return subjectService.getPagedSubjectsByModuleNameContaining(moduleText);
    }


    @PostMapping
    public ResponseEntity<SubjectEntityDto> createSubject(@Valid @RequestBody SubjectEntityDto subjectDto) {
        if (subjectService.subjectNameIsUnique(toSubject(subjectDto))) {
            var createdSubject = subjectService.create(toSubject(subjectDto));
            return ok(toSubjectEntityDto(createdSubject));
        } else {
            throw new SchedulerValidationException("Subject already exists", "Subject name", "Already exists", subjectDto.getName());
        }
    }

//    @DeleteMapping("/{subjectId}")
//    public ResponseEntity<Void> deleteSubject(@PathVariable Long subjectId) {
//        var subjectDeleted = subjectService.deleteById(subjectId);
//
//        if (subjectDeleted) {
//            return ResponseEntity.noContent().build();
////            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//        }
//
//        return ResponseEntity.notFound().build();
//    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable Long subjectId, @Valid @RequestBody SubjectDto subjectDto) {

        var updatedSubject = subjectService.update(subjectId, toSubject(subjectDto));
        return ok(toSubjectDto(updatedSubject));
    }

    @GetMapping(value = "/{subjectId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Subject> getSubject(@PathVariable Long subjectId) {
        var subjectOptional = subjectService.getById(subjectId);

        var responseEntity = subjectOptional
                .map(subject -> ok(subject))
                .orElseGet(() -> ResponseEntity.notFound().build());

        return responseEntity;
    }


    @PostMapping("/{subjectId}/modules/{moduleId}/newModules")
    public ResponseEntity<SubjectDto> addModuleToSubject(@PathVariable Long subjectId, @Valid @PathVariable Long moduleId) {

        var updatedSubject = subjectService.addModuleToSubject(subjectId, moduleId);

        return ok(toSubjectDto(updatedSubject));
    }


    @PostMapping("/{subjectId}/rooms/{roomId}/newRooms")
    public ResponseEntity<SubjectDto> addRoomToSubject(@PathVariable Long subjectId, @Valid @PathVariable Long roomId) {

        var updatedSubject = subjectService.addRoomToSubject(subjectId, roomId);
        return ok(toSubjectDto(updatedSubject));
    }

    @DeleteMapping("/{subjectId}/modules/{moduleId}")
    public ResponseEntity<Void> deleteModuleFromSubjectByModuleId(@PathVariable Long subjectId, @PathVariable Long moduleId) {
        boolean deleted = subjectService.deleteModuleInSubjectById(subjectId, moduleId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{subjectId}/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoomFromSubjectByRoomId(@PathVariable Long subjectId, @PathVariable Long roomId) {
        boolean deleted = subjectService.deleteRoomInSubjectById(subjectId, roomId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{subjectId}/teachers")
    @ResponseBody
    public List<Teacher> getAllTeachersById(@PathVariable Long subjectId) {
        return subjectService.getAllTeachersById(subjectId);
    }

    @PostMapping("/{subjectId}/teachers/{teacherId}/newTeachers")
    public ResponseEntity<SubjectDto> addTeacherToSubject(@PathVariable Long subjectId, @PathVariable Long teacherId) {

        var updatedSubject = subjectService.addTeacherToSubject(subjectId, teacherId);

        return ok(toSubjectDto(updatedSubject));
    }

    @DeleteMapping("/{subjectId}/teachers/{teacherId}")
    public ResponseEntity<Void> deleteSubjectFromTeacherBySubjectId(@PathVariable Long subjectId, @PathVariable Long teacherId) {
        boolean deleted = subjectService.deleteTeacherFromSubjectById(subjectId, teacherId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{subjectId}/availableTeachers", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Teacher> getAvailableTeacherInSubject(@PathVariable Long subjectId) {
        List<Teacher> teachers = subjectService.getAllTeachersById(subjectId);
        List<Teacher> teacherList = subjectService.getFreeTeachers();
        List<Teacher> availableTeachers = teacherList.stream().filter(sub -> !teachers.contains(sub)).collect(toList());
        return availableTeachers;
    }

    @GetMapping(value = "/{subjectId}/availableModules", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Module> getAvailableModulesInSubject(@PathVariable Long subjectId) {
        List<Module> modules = new ArrayList<>(subjectService.getAllModulesById(subjectId));
        List<Module> moduleList = subjectService.getFreeModules();
        List<Module> availableModules = moduleList.stream().filter(sub -> !modules.contains(sub)).collect(toList());

        return availableModules;
    }

    @GetMapping(value = "/{subjectId}/availableRooms", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Room> getAvailableRoomsInSubject(@PathVariable Long subjectId) {
        List<Room> rooms = new ArrayList<>(subjectService.getAllRoomsById(subjectId));
        List<Room> subjectList = subjectService.getFreeRooms();
        List<Room> availableRooms = subjectList.stream().filter(sub -> !rooms.contains(sub)).collect(toList());

        return availableRooms;
    }
}
