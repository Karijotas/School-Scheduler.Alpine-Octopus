package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleEntityDto;
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

import javax.validation.Valid;
import java.util.List;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper.toGroupDto;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ScheduleMapper.toSchedule;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ScheduleMapper.toScheduleEntityDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/schedule")
@Validated
public class ScheduleController {
    public static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;
//
//    private final ScheduleRepository scheduleRepository;
//
//    private final ScheduleLessonsRepository scheduleLessonsRepository;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
//        this.scheduleRepository = scheduleRepository;
//        this.scheduleLessonsRepository = scheduleLessonsRepository;
    }


    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ScheduleEntityDto> getAllPagedSchedules(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return scheduleService.getAllPagedSchedules(page, pageSize);

    }

    @GetMapping(value = "/{scheduleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long scheduleId) {
        var scheduleOptional = scheduleService.getById(scheduleId);

        return scheduleOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleEntityDto> createSchedule(@Valid @RequestBody ScheduleEntityDto scheduleEntityDto) {
        var createdSchedule = scheduleService.create(toSchedule(scheduleEntityDto));
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

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody ScheduleDto scheduleDto, Long groupId, Long shiftId) {

        var updatedSchedule = scheduleService.update(groupId, toGroup(groupsDto), programId, shiftId);
        return ok(toGroupDto(updatedGroup));

    }
}
