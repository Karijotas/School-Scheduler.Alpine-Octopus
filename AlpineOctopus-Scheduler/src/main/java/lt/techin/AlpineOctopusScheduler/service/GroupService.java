package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private GroupsRepository groupsRepository;
    @Autowired
    public GroupService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }
    public List<Groups> getAll() {
        return groupsRepository.findAll();
    }

    public Optional<Groups> getById(Long id){
        return groupsRepository.findById(id);
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
