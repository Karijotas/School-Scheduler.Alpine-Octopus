package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ScheduleMapper;
import lt.techin.AlpineOctopusScheduler.dao.*;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
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
    private final ProgramRepository programRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleLessonsRepository scheduleLessonsRepository, Validator validator,
                           GroupsRepository groupsRepository,
                           ShiftRepository shiftRepository,
                           ProgramRepository programRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleLessonsRepository = scheduleLessonsRepository;
        this.validator = validator;
        this.groupsRepository = groupsRepository;
        this.shiftRepository = shiftRepository;
        this.programRepository = programRepository;
    }

    void validateInputWithInjectedValidator(Schedule schedule) {
        Set<ConstraintViolation<Schedule>> violations = validator.validate(schedule);
        if (!violations.isEmpty()) {
            throw new SchedulerValidationException(violations.toString(), "Schedule", "Error in schedule entity", schedule.toString());
        }
    }

    @Transactional
    public List<ScheduleEntityDto> getSchedulesByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return scheduleRepository.findByNameContainingIgnoreCaseOrderByModifiedDateDesc(nameText, pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleEntityDto> getSchedulesByStartingDate(String startingDate, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        var starting = LocalDate.parse(startingDate);
        return scheduleRepository.findByStartingDateOrderByModifiedDateDesc(starting, pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleEntityDto> getSchedulesByPlannedTill(String plannedTill, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        var planned = LocalDate.parse(plannedTill);
        return scheduleRepository.findByPlannedTillDateOrderByModifiedDateDesc(planned, pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
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

    public Schedule create(Schedule schedule, Long groupId) {
        var createdGroup = groupsRepository.findById(groupId)
                .orElseThrow(() -> new SchedulerValidationException("Group doesn't exist", "id", "Group doesn't exist", groupId.toString()));
        var createdShift = shiftRepository.findById(createdGroup.getShift().getId())
                .orElseThrow(() -> new SchedulerValidationException("Shift doesn't exist", "Shift", "Shift not found", createdGroup.getShift().getId().toString()));
        var createdProgram = programRepository.findById(createdGroup.getProgram().getId())
                .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", createdGroup.getProgram().getId().toString()));

//        var

        schedule.setGroup(createdGroup);
        schedule.setShift(createdShift);
//        schedule.setLessons();

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
