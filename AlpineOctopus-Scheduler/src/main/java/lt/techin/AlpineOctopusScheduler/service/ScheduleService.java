package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ScheduleMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.dao.ScheduleLessonsRepository;
import lt.techin.AlpineOctopusScheduler.dao.ScheduleRepository;
import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleLessonsRepository scheduleLessonsRepository;

    private final Validator validator;
    private final GroupsRepository groupsRepository;
    private final ShiftRepository shiftRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleLessonsRepository scheduleLessonsRepository, Validator validator,
                           GroupsRepository groupsRepository,
                           ShiftRepository shiftRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleLessonsRepository = scheduleLessonsRepository;
        this.validator = validator;
        this.groupsRepository = groupsRepository;
        this.shiftRepository = shiftRepository;
    }

    void validateInputWithInjectedValidator(Schedule schedule) {
        Set<ConstraintViolation<Schedule>> violations = validator.validate(schedule);
        if (!violations.isEmpty()) {
            throw new SchedulerValidationException(violations.toString(), "Schedule", "Error in schedule entity", schedule.toString());
        }
    }

    public List<ScheduleTestDto> getAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleMapper::toScheduleTestDto)
                .collect(Collectors.toList());
    }

    public List<ScheduleEntityDto> getAllPagedSchedules(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return scheduleRepository.findAll(pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
    }

    public Optional<Schedule> getById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Schedule create(Schedule schedule, Long groupId, Long shiftId) {
//        validateInputWithInjectedValidator(schedule);

        var createdGroup = groupsRepository.findById(groupId)
                .orElseThrow(() -> new SchedulerValidationException("Group doesn't exist", "id", "Group doesn't exist", groupId.toString()));
        var createdShift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new SchedulerValidationException("Shift doesn't exist", "Shift", "Shift not found", shiftId.toString()));

        schedule.setGroup(createdGroup);
        schedule.setShift(createdShift);

        return scheduleRepository.save(schedule);
    }

    public Schedule update(Long id, Schedule schedule, Long groupId, Long shiftId) {
        var existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", id.toString()));
        var existingGroup = groupsRepository.findById(groupId)
                .orElseThrow(() -> new SchedulerValidationException("Group doesn't exist", "id", "Group doesn't exist", groupId.toString()));
        var existingShift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new SchedulerValidationException("Shift doesn't exist", "Shift", "Shift not found", shiftId.toString()));

        existingSchedule.setGroup(existingGroup);
        existingSchedule.setShift(existingShift);
        existingSchedule.setName(schedule.getName());
        existingSchedule.setStartingDate(schedule.getStartingDate());
        existingSchedule.setPlannedTillDate(schedule.getPlannedTillDate());
        existingSchedule.setStatus(schedule.getStatus());
        existingSchedule.setLessons(schedule.getLessons());

        return existingSchedule;
    }


    public boolean deleteById(Long id) {
        try {
            scheduleRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

//    public List<ScheduleLessons> getAllLessonsInScheduleByScheduleId(Long id) {
//        List<String> lessonList = scheduleRepository.get
//    }

}
