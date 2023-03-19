package lt.techin.AlpineOctopusScheduler.api;


import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Shift;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import lt.techin.AlpineOctopusScheduler.service.TeacherService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper.*;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/teachers")
@Validated
public class TeacherController {

    public static Logger logger = LoggerFactory.getLogger(TeacherController.class);
    private final TeacherService teacherService;


    public TeacherController(TeacherService teacherService) {

        this.teacherService = teacherService;
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<TeacherTestDto> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping
    @ResponseBody
    public List<TeacherEntityDto> getAvailableTeachers() {
        return teacherService.getAllAvailableTeachers();
    }


    @GetMapping(path = "/archive/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TeacherEntityDto> getDeletedTeachers() {
        return teacherService.getAllDeletedTeachers();
    }


    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TeacherEntityDto> getPagedAvailableTeachers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        return teacherService.getAllAvailablePagedTeachers(page, pageSize);
    }

    @GetMapping(path = "/archive/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TeacherEntityDto> getPagedDeletedTeachers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return teacherService.getAllDeletedPagedTeachers(page, pageSize);
    }

//    @GetMapping
//    @ResponseBody
//    public List<TeacherEntityDto> getTeachers() {
//        return teacherService.getAll().stream()
//                .map(TeacherMapper::toTeacherEntityDto)
//                .collect(toList());
//    }
//
//    @GetMapping(path = "/page/all", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public List<TeacherEntityDto> getPagedAllTeachers(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
//                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return teacherService.getPagedAllTeachers(page, pageSize).stream()
//                .map(TeacherMapper::toTeacherEntityDto)
//                .collect(toList());
//    }

    @PostMapping
    public ResponseEntity<TeacherEntityDto> createTeacher(@Valid @RequestBody TeacherEntityDto teacherDto) {
        if (teacherService.loginEmailIsUnique(toTeacher(teacherDto))) {
            var createdTeacher = teacherService.create(toTeacher(teacherDto));
            return ok(toTeacherEntityDto(createdTeacher));
        } else {
            throw new SchedulerValidationException("Teacher already exists", "Teacher login email", "Already exists", teacherDto.getLoginEmail());
        }
    }

    @DeleteMapping("/delete/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId) {
        var teacherDeleted = teacherService.deleteById(teacherId);
        if (teacherDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable Long teacherId, @Valid @RequestBody TeacherDto teacherDto) {
        var updatedTeacher = teacherService.update(teacherId, toTeacher(teacherDto));
        return ok(toTeacherDto(updatedTeacher));
    }

    @GetMapping(value = "/{teacherId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long teacherId) {
        var teacherOptional = teacherService.getById(teacherId);

        var responseEntity = teacherOptional
                .map(teacher -> ok(teacher))
                .orElseGet(() -> ResponseEntity.notFound().build());

        return responseEntity;
    }

    @GetMapping(value = "/{teacherId}/shifts")
    @ResponseBody
    public Set<Shift> getAllShiftsById(@PathVariable Long teacherId) {
        return teacherService.getAllShiftsById(teacherId);
    }


    @PostMapping("/{teacherId}/shifts/{shiftId}/newShifts")
    public ResponseEntity<TeacherDto> addShiftToTeacher(@PathVariable Long teacherId, @Valid @PathVariable Long shiftId) {

        var updatedTeacher = teacherService.addShiftToTeacher(teacherId, shiftId);

        return ok(toTeacherDto(updatedTeacher));
    }

    @DeleteMapping("/{teacherId}/shifts/{shiftId}")
    public ResponseEntity<Void> deleteShiftFromTeacherByShiftId(@PathVariable Long teacherId, @PathVariable Long shiftId) {
        boolean deleted = teacherService.deleteShiftInTeacherById(teacherId, shiftId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(path = "page/name-filter/{nameText}")
    @ApiOperation(value = "Get Paged Teachers starting with", notes = "Returns list of Teachers starting with passed String")
    @ResponseBody
    public List<TeacherEntityDto> getPagedTeachersByNameContaining(@PathVariable String nameText,
                                                                   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return teacherService.getPagedTeachersByNameContaining(nameText, page, pageSize);
    }

    @GetMapping(path = "page/shift-filter/{shiftText}")
    @ApiOperation(value = "Get Paged Teachers starting with", notes = "Returns list of Teachers starting with passed String")
    @ResponseBody
    public List<TeacherEntityDto> getPagedTeachersByShiftContaining(@PathVariable String shiftText) {
        return teacherService.getPagedTeachersByShiftNameContaining(shiftText);
    }

    @GetMapping(path = "page/subject-filter/{subjectText}")
    @ApiOperation(value = "Get Paged Teachers starting with", notes = "Returns list of Teachers starting with passed String")
    @ResponseBody
    public List<TeacherEntityDto> getPagedTeachersBySubjectContaining(@PathVariable String subjectText) {
        return teacherService.getPagedTeachersBySubjectNameContaining(subjectText);
    }

    @PatchMapping("/delete/{teacherId}")
    public ResponseEntity<TeacherEntityDto> removeTeacher(@PathVariable Long teacherId) {
        var updatedTeacher = teacherService.deleteTeacher(teacherId);
        return ok(updatedTeacher);
    }

    @PatchMapping("/restore/{teacherId}")
    public ResponseEntity<TeacherEntityDto> restoreTeacher(@PathVariable Long teacherId) {
        var updatedTeacher = teacherService.restoreTeacher(teacherId);
        return ok(updatedTeacher);
    }

    @GetMapping(value = "/{teacherId}/subjects")
    @ResponseBody
    public Set<SubjectEntityDto> getAllSubjectsById(@PathVariable Long teacherId) {
        return teacherService.getAllSubjectById(teacherId);
    }

    @PostMapping("/{teacherId}/subjects/{subjectId}/newSubjects")
    public ResponseEntity<TeacherDto> addSubjectToTeacher(@PathVariable Long teacherId, @Valid @PathVariable Long subjectId) {

        var updatedTeacher = teacherService.addSubjectToTeacher(teacherId, subjectId);

        return ok(toTeacherDto(updatedTeacher));
    }

    @DeleteMapping("/{teacherId}/subjects/{subjectId}")
    public ResponseEntity<Void> deleteSubjectFromTeacherBySubjectId(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        boolean deleted = teacherService.deleteSubjectInTeacherById(teacherId, subjectId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{teacherId}/availableSubjects", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Subject> getAvailableSubjectsInTeacher(@PathVariable Long teacherId) {
        List<Subject> subjects = new ArrayList<>(teacherService.getAllSubjectById(teacherId).stream().map(SubjectMapper::toSubject).collect(toList()));
        List<Subject> subjectList = teacherService.getFreeSubjects();
        List<Subject> availableSubjects = subjectList.stream().filter(sub -> !subjects.contains(sub)).collect(toList());

        return availableSubjects;
    }

    @GetMapping(value = "/{teacherId}/availableShifts", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Shift> getAvailableShiftsInTeacher(@PathVariable Long teacherId) {
        List<Shift> shifts = new ArrayList<>(teacherService.getAllShiftsById(teacherId));
        List<Shift> subjectList = teacherService.getFreeShifts();
        List<Shift> availableShifts = subjectList.stream().filter(sub -> !shifts.contains(sub)).collect(toList());

        return availableShifts;
    }
}
