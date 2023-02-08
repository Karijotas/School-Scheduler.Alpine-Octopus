package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupRepository;
import lt.techin.AlpineOctopusScheduler.model.Group;
import lt.techin.AlpineOctopusScheduler.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupMapper.toGroup;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupMapper.toGroupDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/groups")
@Validated
public class GroupController {

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;
    private final GroupRepository groupRepository;

    public GroupController(GroupService groupService,
                           GroupRepository groupRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,})
    @ResponseBody
    public List<GroupEntityDto> getGroups(){
        return groupService.getAll()
                .stream()
                .map(GroupMapper::toGroupEntityDto)
                .collect(toList());
    }
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,})
    public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupDto groupDto){
        var createdGroup = groupService.create(toGroup(groupDto));

        return ok(toGroupDto(createdGroup));
    }
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        logger.info("Attempt to delete Animal by id: {}", groupId);

        boolean deleted = groupService.deleteById(groupId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> replaceGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
        var updatedGroup = groupService.replace(groupId, toGroup(groupDto));

        return ok(toGroupDto(updatedGroup));
    }
    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
        var updatedGroup = groupService.update(groupId, toGroup(groupDto));

        return ok(toGroupDto(updatedGroup));
    }
}
