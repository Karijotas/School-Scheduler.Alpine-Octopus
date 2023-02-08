package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.GroupRepository;
import lt.techin.AlpineOctopusScheduler.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private GroupRepository groupRepository;
    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public Optional<Group> getById(Long id){
        return groupRepository.findById(id);
    }

    public Group create(Group group){
        return groupRepository.save(group);
    }
    public Group update(Long id, Group group){
        Group existingGroup =  groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group doesn't exist"));

        existingGroup.setName(group.getName());
        existingGroup.setShift(group.getShift());
        existingGroup.setProgram(group.getProgram());
        existingGroup.setSchoolYear(group.getSchoolYear());
        existingGroup.setStudentAmount(group.getStudentAmount());
        return groupRepository.save(existingGroup);
    }

    public Group replace(Long id, Group group){
        group.setId(id);
        return groupRepository.save(group);
    }

    public Boolean deleteById(Long id){
        try{
            groupRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}
