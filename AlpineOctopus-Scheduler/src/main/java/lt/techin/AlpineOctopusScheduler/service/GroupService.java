package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
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
    private final ShiftRepository shiftRepository;

    @Autowired
    public GroupService(GroupsRepository groupsRepository, ProgramRepository programRepository, Validator validator,
                        ShiftRepository shiftRepository) {
        this.groupsRepository = groupsRepository;
        this.programRepository = programRepository;
        this.validator = validator;
        this.shiftRepository = shiftRepository;
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

//    public boolean studentAmountIsValid(Groups groups) {
//        return groups.getStudentAmount() >= 1 && groups.getStudentAmount() <= 300;
//    }

    public List<GroupsTestDto> getAllGroups() {
        return groupsRepository.findAll().stream()
                .map(GroupsMapper::toGroupsTestDto).collect(Collectors.toList());
    }

    public List<Groups> getAll() {
        return groupsRepository.findAll();
    }

    public Optional<Groups> getById(Long id) {
        
        return groupsRepository.findById(id);
    }

//    public List<Groups> getPagedAllGroups(int page, int pageSize) {
//
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        return groupsRepository.findAll(pageable).stream()
//                .collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByNameContaining(String nameText) {
        return groupsRepository.findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, nameText).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsBySchoolYear(Integer schoolYearText) {

        return groupsRepository.findAllByDeletedAndSchoolYearOrderByModifiedDateDesc(Boolean.FALSE, schoolYearText).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByProgram(String programText) {

        return groupsRepository.findAllByDeletedAndProgram_nameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, programText)
                .stream().map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
//        Program createdProgram = programRepository.findAllByDeletedAndNameContainingIgnoreCase(Boolean.FALSE, programText)
//                .stream()
//                .findFirst().get();
//        return groupsRepository.findAll()
//                .stream()
//                .filter(groups -> groups.getProgram().equals(createdProgram))
//                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    public Groups create(Groups groups, Long programId, Long shiftId) {
//        validateInputWithInjectedValidator(groups);
        Program createdProgram = programRepository.findById(programId)
                .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", programId.toString()));
        Shift createdShift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new SchedulerValidationException("Shift doesn't exist", "Shift", "Shift not found", shiftId.toString()));

        var newGroup = new Groups();

        if (schoolYearIsValid(groups)) {
            newGroup.setId(groups.getId());
            newGroup.setName(groups.getName());
            newGroup.setProgram(createdProgram);
            newGroup.setShift(createdShift);
            newGroup.setSchoolYear(groups.getSchoolYear());
            newGroup.setStudentAmount(groups.getStudentAmount());
            newGroup.setDeleted(Boolean.FALSE);
            newGroup.setCreatedDate(groups.getCreatedDate());
            newGroup.setModifiedDate(groups.getModifiedDate());
            return groupsRepository.save(newGroup);
        } else {
            throw new SchedulerValidationException("Invalid year value", "School year", "School year must be between 2023-3023", groups.getSchoolYear().toString());
        }
    }

//    public Groups create(Groups groups) {
//      validateInputWithInjectedValidator(groups);
//      Program createdProgram = programRepository.findById(programId)
//              .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", programId.toString()));
//      Shift createdShift = shiftRepository.findById(shiftId)
//              .orElseThrow(() -> new SchedulerValidationException("Shift doesn't exist", "Shift", "Shift not found", shiftId.toString()));
//
//      if (schoolYearIsValid(groups)) {
//          groups.setProgram(createdProgram);
//          groups.setShift(createdShift);
//        var newGroup = new Groups();
//        newGroup.setId(groups.getId());
//        newGroup.setName(groups.getName());
//        newGroup.setProgram(groups.getProgram());
//        newGroup.setShift(groups.getShift());
//        newGroup.setSchoolYear(groups.getSchoolYear());
//        newGroup.setStudentAmount(groups.getStudentAmount());
//        newGroup.setDeleted(Boolean.FALSE);
//        newGroup.setCreatedDate(groups.getCreatedDate());
//        newGroup.setModifiedDate(groups.getModifiedDate());
//        return groupsRepository.save(newGroup);
//
//    }

    //    .getById(programId)
    public Groups update(Long id, Groups groups, Long programId, Long shiftId) {
        validateInputWithInjectedValidator(groups);
        Program createdProgram = programRepository.findById(programId)
                .orElseThrow(() -> new SchedulerValidationException("Program doesn't exist", "Program", "Program not found", programId.toString()));
        Shift createdShift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new SchedulerValidationException("Shift doesn't exist", "Shift", "Shift not found", shiftId.toString()));
        Groups existingGroups = groupsRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Group doesn't exist", "id", "Group doesn't exist", id.toString()));

        existingGroups.setName(groups.getName());
        existingGroups.setShift(createdShift);
        existingGroups.setProgram(createdProgram);
        existingGroups.setSchoolYear(groups.getSchoolYear());
        existingGroups.setStudentAmount(groups.getStudentAmount());
        existingGroups.setModifiedDate(LocalDateTime.now());

        return groupsRepository.save(existingGroups);
    }

//    public Groups replace(Long id, Groups groups) {
////        validateInputWithInjectedValidator(groups);
//
//        Groups existingGroups = groupsRepository.findById(id)
//                .orElseThrow(() -> new SchedulerValidationException("Group does not exist", "id", "Group doesn't exist", id.toString()));
//
//
//        groups.setId(id);
//        return groupsRepository.save(groups);
//    }

//    public Boolean deleteById(Long id) {
//        try {
//            groupsRepository.deleteById(id);
//            return true;
//        } catch (EmptyResultDataAccessException e) {
//            return false;
//        }
//    }

    public List<GroupsEntityDto> getAllAvailablePagedGroups(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return groupsRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE, pageable).stream()
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }

//    public List<GroupsEntityDto> getAllDeletedPagedGroups(int page, int pageSize) {
//
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        return groupsRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE, pageable).stream()
//                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
//    }

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
