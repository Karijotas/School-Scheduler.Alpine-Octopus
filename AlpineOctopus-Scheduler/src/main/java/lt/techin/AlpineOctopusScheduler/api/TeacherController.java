package lt.techin.AlpineOctopusScheduler.api;

//Mantvydas Juršys

import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import lt.techin.AlpineOctopusScheduler.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    @ResponseBody
    public List<TeacherEntityDto> getTeachers() {
        return teacherService.getAll().stream()
                .map(TeacherMapper::toTeacherEntityDto)
                .collect(toList());
    }

    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
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

    @DeleteMapping("/{teacherId}")
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
}
