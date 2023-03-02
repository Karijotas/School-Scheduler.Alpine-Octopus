package lt.techin.AlpineOctopusScheduler.api;


import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper;
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
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper.toTeacher;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper.toTeacherDto;
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

//    @GetMapping(path = "/all")
//    @ResponseBody
//    public List<TeacherTestDto> getAllTeachers() {
//        return teachersService.getAllTeachers();
//    }

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

    @GetMapping(path = "/page/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<TeacherEntityDto> getPagedAllTeachers(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return teacherService.getPagedAllTeachers(page, pageSize).stream()
                .map(TeacherMapper::toTeacherEntityDto)
                .collect(toList());
    }

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        if (teacherService.loginEmailIsUnique(toTeacher(teacherDto))) {
            var createdTeacher = teacherService.create(toTeacher(teacherDto));
            return ok(toTeacherDto(createdTeacher));
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
        if (teacherService.loginEmailIsUnique(toTeacher(teacherDto))) {
            var updatedTeacher = teacherService.update(teacherId, toTeacher(teacherDto));
            return ok(toTeacherDto(updatedTeacher));
        } else {
            throw new SchedulerValidationException("Teacher already exists", "Teacher login email", "Already exists", teacherDto.getLoginEmail());
        }
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

    @GetMapping(value = "/{teacherId}/subjects")
    @ResponseBody
    public List<Subject> getAllSubjectsById(@PathVariable Long teacherId) {
        return teacherService.getAllSubjectsById(teacherId);
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

    @PostMapping("/{teacherId}/subjects/{subjectId}/newSubjects")
    public void addSubjectToTeacher(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        teacherService.addSubjectToTeacher(teacherId, subjectId);
    }

    @DeleteMapping("/{teacherId}/subjects/{subjectId}")
    public ResponseEntity<Void> deleteTeacherFromSubjectByTeacherId(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        boolean deleted = teacherService.deleteSubjectFromTeacherById(teacherId, subjectId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "page/shift-filter/{shiftText}")
    @ApiOperation(value = "Get Paged Teachers starting with", notes = "Returns list of Teachers starting with passed String")
    @ResponseBody
    public List<TeacherEntityDto> getPagedTeachersByShiftContaining(@PathVariable String shiftText) {
        return teacherService.getPagedTeachersByShiftNameContaining(shiftText);
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
}
