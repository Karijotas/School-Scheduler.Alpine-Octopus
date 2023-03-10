package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleTestDto;
import lt.techin.AlpineOctopusScheduler.model.Schedule;
import lt.techin.AlpineOctopusScheduler.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ScheduleMapper.toSchedule;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ScheduleMapper.toScheduleEntityDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/schedule")
@Validated
public class ScheduleController {
    public static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ScheduleEntityDto> getAllPagedSchedules(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return scheduleService.getAllPagedSchedules(page, pageSize);

    }

    @GetMapping(path = "page/name-filter/{nameText}")
    @ApiOperation(value = "Get paged schedules starting with", notes = "Returns list of schedules starting with passed String")
    @ResponseBody
    public List<ScheduleEntityDto> getPagedSchedulesByNameContaining(@PathVariable String nameText,
                                                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return scheduleService.getSchedulesByNameContaining(nameText, page, pageSize);
    }

    @GetMapping(path = "page/starting-date-filter/{startingDate}")
    @ApiOperation(value = "Get paged schedules by starting date", notes = "Returns list of schedules by starting date with passed String")
    @ResponseBody
    public List<ScheduleEntityDto> getPagedSchedulesByStartingDate(@PathVariable String startingDate,
                                                                   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return scheduleService.getSchedulesByStartingDate(startingDate, page, pageSize);
    }

    @GetMapping(path = "page/planned-till-filter/{plannedTill}")
    @ApiOperation(value = "Get paged schedules by plannedTill date", notes = "Returns list of schedules by plannedTill date with passed String")
    @ResponseBody
    public List<ScheduleEntityDto> getPagedSchedulesByPlannedTill(@PathVariable String plannedTill,
                                                                  @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return scheduleService.getSchedulesByPlannedTill(plannedTill, page, pageSize);
    }


    @GetMapping(value = "/{scheduleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long scheduleId) {
        var scheduleOptional = scheduleService.getById(scheduleId);

        return scheduleOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleEntityDto> createSchedule(@RequestBody ScheduleEntityDto scheduleEntityDto, Long groupId,
                                                            @RequestParam(value = "startingDate", required = false) String startingDate) {

        var starting = LocalDate.parse(startingDate.toString());
        var createdSchedule = scheduleService.create(toSchedule(scheduleEntityDto), groupId, starting);
        return ok(toScheduleEntityDto(createdSchedule));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        logger.info("Attempt to delete Schedule by id: {}", scheduleId);
        boolean deleted = scheduleService.deleteById(scheduleId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{scheduleId}/{lessonId}")
    public ResponseEntity<ScheduleEntityDto> setTeacherAndRoomInASchedule(@PathVariable Long scheduleId, Long lessonId, Long teacherId, Long roomId) {
        var updatedSchedule = scheduleService.setTeacherAndRoomInASchedule(scheduleId, lessonId, teacherId, roomId);
        return ok(toScheduleEntityDto(updatedSchedule));
    }

    @PatchMapping("/{scheduleId}/")
    public ResponseEntity<ScheduleEntityDto> scheduleLesson(@PathVariable Long scheduleId, Long subjectId,
                                                            @RequestParam(value = "startTime", required = true) String startTime,
                                                            @RequestParam(value = "endTime", required = true) String endTime) {
        var starting = LocalDateTime.parse(startTime.toString());
        var ending = LocalDateTime.parse(endTime.toString());

        var updatedSchedule = scheduleService.ScheduleLesson(scheduleId, subjectId, starting, ending);
        return ok(toScheduleEntityDto(updatedSchedule));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleEntityDto> setLessonOnline(@PathVariable Long scheduleId,
                                                             Long lessonId) {
        var updatedSchedule = scheduleService.setLessonOnline(scheduleId, lessonId);
        return ok(toScheduleEntityDto(updatedSchedule));
    }

    @DeleteMapping("/{scheduleId}/{lessonId}")
    public ResponseEntity<ScheduleEntityDto> removeLesson(@PathVariable Long scheduleId,
                                                          Long lessonId) {

        logger.info("Attempt to delete Lesson from Schedule by id: {}", lessonId);
        boolean deleted = scheduleService.removeLesson(scheduleId, lessonId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<ScheduleTestDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

}
