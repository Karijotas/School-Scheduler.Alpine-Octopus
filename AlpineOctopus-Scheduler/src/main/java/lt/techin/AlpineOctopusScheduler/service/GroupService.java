package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupsDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import lt.techin.AlpineOctopusScheduler.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private GroupsRepository groupsRepository;
    private ProgramRepository programRepository;
    @Autowired
    public GroupService(GroupsRepository groupsRepository, ProgramRepository programRepository) {
        this.groupsRepository = groupsRepository;
        this.programRepository = programRepository;
    }
    public List<Groups> getAll() {
        return groupsRepository.findAll();
    }

    public Optional<Groups> getById(Long id){
        return groupsRepository.findById(id);
    }

    public List<Groups> getPagedAllGroups(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return groupsRepository.findAll(pageable).stream().sorted(Comparator.comparing(Groups::getModifiedDate).reversed()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByNameContaining(String nameText) {
        return groupsRepository.findByNameContainingIgnoreCase(nameText).stream().sorted(Comparator.comparing(Groups::getModifiedDate).reversed())
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsBySchoolYear(Integer schoolYearText) {
        return groupsRepository.findBySchoolYear(schoolYearText).stream().sorted(Comparator.comparing(Groups::getModifiedDate).reversed())
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<GroupsEntityDto> getGroupsByProgram(String programText) {
        Program createdProgram = programRepository.findByNameContainingIgnoreCase(programText).stream().findAny().get();
        return groupsRepository.findAll().stream().sorted(Comparator.comparing(Groups::getModifiedDate).reversed()).filter(groups -> groups.getProgram().equals(createdProgram))
                .map(GroupsMapper::toGroupEntityDto).collect(Collectors.toList());
    }


    public Groups create(Groups groups, Long programId){
        Program createdProgram = programRepository.findById(programId).get();
        groups.setProgram(createdProgram);
        return groupsRepository.save(groups);
    }
//    .getById(programId)
    public Groups update(Long id, Groups groups, Long programId){
        Program createdProgram = programRepository.findById(programId).get();
        Groups existingGroups =  groupsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group doesn't exist"));

        existingGroups.setName(groups.getName());
        existingGroups.setShift(groups.getShift());
        existingGroups.setProgram(createdProgram);
        existingGroups.setSchoolYear(groups.getSchoolYear());
        existingGroups.setStudentAmount(groups.getStudentAmount());
        return groupsRepository.save(existingGroups);
    }

    public Groups replace(Long id, Groups groups){
        groups.setId(id);
        return groupsRepository.save(groups);
    }

    public Boolean deleteById(Long id){
        try{
            groupsRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}
