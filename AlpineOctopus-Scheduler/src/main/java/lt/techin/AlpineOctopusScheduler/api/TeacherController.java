package lt.techin.AlpineOctopusScheduler.api;

//Mantvydas Juršys

import lt.techin.AlpineOctopusScheduler.api.dto.ModuleDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper;
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
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ModuleMapper.toModuleDto;
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

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        var createdTeacher = teacherService.create(toTeacher(teacherDto));

        return ok(toTeacherDto(createdTeacher));
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
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherDto teacherDto) {
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

    @PutMapping("/subjects/{moduleId}")
    public ResponseEntity<TeacherDto> addSubjectToTeacher(@PathVariable Long teacherId, @RequestBody Long subjectId) {
        var updatedTeacher = teacherService.addSubjectToTeacher(teacherId, subjectId);

        return ok(toTeacherDto(updatedTeacher));
    }
}
