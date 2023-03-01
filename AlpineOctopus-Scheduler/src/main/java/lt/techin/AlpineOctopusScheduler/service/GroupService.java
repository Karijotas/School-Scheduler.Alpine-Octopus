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

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper.toGroupEntityDto;


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

    public boolean groupNameIsUnique(Groups groups) {
        return groupsRepository.findAll()
                .stream()
                .noneMatch(groups1 -> groups1.getName().equals(groups.getName()));
    }

    public boolean schoolYearIsValid(Groups groups) {
        return groups.getSchoolYear() >= 2023 && groups.getSchoolYear() <= 3032;
    }

    public boolean studentAmountIsValid(Groups groups) {
        return groups.getStudentAmount() >= 1 && groups.getStudentAmount() <= 300;
    }

    public List<Groups> getAll() {
        return groupsRepository.findAll();
    }

    public Optional<Groups> getById(Long id) {
        return groupsRepository.findById(id);
    }

    public List<Groups> getPagedAllGroups(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return groupsRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByNameContaining(String nameText) {
        return groupsRepository.findAllByDeletedAndNameContainingIgnoreCase(Boolean.FALSE, nameText).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsBySchoolYear(Integer schoolYearText) {
        return groupsRepository.findAllByDeletedAndSchoolYear(Boolean.FALSE, schoolYearText).stream()

                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByProgram(String programText) {

        return groupsRepository.findAllByDeletedAndProgram_nameContainingIgnoreCaseOrderByModifiedDateDesc(false, programText)
                .stream().map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
//        Program createdProgram = programRepository.findAllByDeletedAndNameContainingIgnoreCase(Boolean.FALSE, programText)
//                .stream()
//                .findFirst().get();
//        return groupsRepository.findAll()
//                .stream()
//                .filter(groups -> groups.getProgram().equals(createdProgram))
//                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    public Groups create(Groups groups, Long programId) {
        validateInputWithInjectedValidator(groups);
        Program createdProgram = programRepository.findById(programId)
                .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", programId.toString()));

        if (schoolYearIsValid(groups)) {
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

    public List<GroupsEntityDto> getAllAvailablePagedGroups(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return groupsRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE, pageable).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    public List<GroupsEntityDto> getAllDeletedPagedGroups(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return groupsRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE, pageable).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    public List<GroupsEntityDto> getAllAvailableGroups() {
        return groupsRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    public List<GroupsEntityDto> getAllDeletedGroups() {
        return groupsRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    public GroupsEntityDto restoreGroup(Long groupId) {

        var existingGroup = groupsRepository.findById(groupId).orElseThrow(() -> new SchedulerValidationException("Group does not exist",
                "id", "Group not found", groupId.toString()));
        existingGroup.setDeleted(Boolean.FALSE);
        groupsRepository.save(existingGroup);
        return toGroupEntityDto(existingGroup);
    }

    public GroupsEntityDto deleteGroup(Long groupId) {

        var existingGroup = groupsRepository.findById(groupId).orElseThrow(() -> new SchedulerValidationException("Group does not exist",
                "id", "Group not found", groupId.toString()));
        existingGroup.setDeleted(Boolean.TRUE);
        groupsRepository.save(existingGroup);
        return toGroupEntityDto(existingGroup);
    }
}
