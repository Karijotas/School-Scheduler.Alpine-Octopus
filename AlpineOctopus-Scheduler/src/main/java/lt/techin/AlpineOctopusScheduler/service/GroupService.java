package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import lt.techin.AlpineOctopusScheduler.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupsRepository groupsRepository;
    private final ProgramRepository programRepository;

    private final Validator validator;

    @Autowired
    public GroupService(GroupsRepository groupsRepository, ProgramRepository programRepository, Validator validator) {
        this.groupsRepository = groupsRepository;
        this.programRepository = programRepository;
        this.validator = validator;
    }

    void validateInputWithInjectedValidator(Groups groups) {
        Set<ConstraintViolation<Groups>> violations = validator.validate(groups);
        if (!violations.isEmpty()) {
            throw new SchedulerValidationException(violations.toString(), "Groups", "Error in groups entity", groups.toString());
        }
    }

    public List<Groups> getAll() {
        return groupsRepository.findAll();
    }

    public Optional<Groups> getById(Long id) {
        return groupsRepository.findById(id);
    }

    public List<Groups> getPagedAllGroups(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return groupsRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByNameContaining(String nameText) {
        return groupsRepository.findByNameContainingIgnoreCase(nameText).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsBySchoolYear(Integer schoolYearText) {

        return groupsRepository.findBySchoolYear(schoolYearText).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByProgram(String programText) {
        Program createdProgram = programRepository.findByNameContainingIgnoreCase(programText)
                .stream()
                .findFirst()
                .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", programText));

        return groupsRepository.findAll()
                .stream()
                .filter(groups -> groups.getProgram().equals(createdProgram))
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }


    public Groups create(Groups groups, Long programId) {
        validateInputWithInjectedValidator(groups);
        Program createdProgram = programRepository.findById(programId)
                .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", programId.toString()));

        if (groups.schoolYearIsValid()) {
            groups.setProgram(createdProgram);
            return groupsRepository.save(groups);
        } else {
            throw new SchedulerValidationException("Invalid year value", "School year", "School year must be between 2023-3023", groups.getSchoolYear().toString());
        }
    }

    //    .getById(programId)
    public Groups update(Long id, Groups groups, Long programId) {
        validateInputWithInjectedValidator(groups);
        Program createdProgram = programRepository.findById(programId)
                .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", programId.toString()));
        Groups existingGroups = groupsRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Group doesn't exist", "id", "Group doesn't exist", id.toString()));

        existingGroups.setName(groups.getName());
        existingGroups.setShift(groups.getShift());
        existingGroups.setProgram(createdProgram);
        existingGroups.setSchoolYear(groups.getSchoolYear());
        existingGroups.setStudentAmount(groups.getStudentAmount());
        return groupsRepository.save(existingGroups);
    }

    public Groups replace(Long id, Groups groups) {
        validateInputWithInjectedValidator(groups);
        groups.setId(id);
        return groupsRepository.save(groups);
    }

    public Boolean deleteById(Long id) {
        try {
            groupsRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
