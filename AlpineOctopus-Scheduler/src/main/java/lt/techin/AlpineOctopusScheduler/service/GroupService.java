package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupsDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return groupsRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupsDto> getGroupsByNameContaining(String nameText) {
        return groupsRepository.findByNameContainingIgnoreCase(nameText).stream()
                .map(GroupsMapper::toGroupDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<GroupsDto> getGroupsBySchoolYear(Integer schoolYearText) {
        return groupsRepository.findBySchoolYear(schoolYearText).stream()
                .map(GroupsMapper::toGroupDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<GroupsDto> getGroupsByProgram(String programText) {
        return groupsRepository.findByProgram(programText).stream()
                .map(GroupsMapper::toGroupDto).collect(Collectors.toList());
    }


    public Groups create(Groups groups){
        return groupsRepository.save(groups);
    }
    public Groups update(Long id, Groups groups){
        Groups existingGroups =  groupsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group doesn't exist"));

        existingGroups.setName(groups.getName());
        existingGroups.setShift(groups.getShift());
        existingGroups.setProgram(groups.getProgram());
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
