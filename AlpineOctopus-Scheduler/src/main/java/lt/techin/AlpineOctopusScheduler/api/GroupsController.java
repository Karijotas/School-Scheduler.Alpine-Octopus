package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupsDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.model.Groups;
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
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper.toGroup;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper.toGroupDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/groups")
@Validated
public class GroupsController {

    private final Logger logger = LoggerFactory.getLogger(GroupsController.class);
    private final GroupService groupService;
    private final GroupsRepository groupsRepository;

    public GroupsController(GroupService groupService,
                            GroupsRepository groupsRepository) {
        this.groupService = groupService;
        this.groupsRepository = groupsRepository;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,})
    @ResponseBody
    public List<GroupsEntityDto> getGroups(){
        return groupService.getAll()
                .stream()
                .map(GroupsMapper::toGroupEntityDto)
                .collect(toList());
    }

    @GetMapping(value = "/{groupId}", produces = {MediaType.APPLICATION_JSON_VALUE,})
    public ResponseEntity<Groups> getGroup(@PathVariable Long groupId) {
        var groupsOptional = groupService.getById(groupId);

        var responseEntity = groupsOptional
                .map(groups -> ok(groups))
                .orElseGet(() -> ResponseEntity.notFound().build());

        return responseEntity;
    }
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,})
    public ResponseEntity<GroupsDto> createGroup(@RequestBody GroupsDto groupsDto){
        var createdGroup = groupService.create(toGroup(groupsDto));

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
    public ResponseEntity<GroupsDto> replaceGroup(@PathVariable Long groupId, @RequestBody GroupsDto groupsDto) {
        var updatedGroup = groupService.replace(groupId, toGroup(groupsDto));

        return ok(toGroupDto(updatedGroup));
    }
    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupsDto> updateGroup(@PathVariable Long groupId, @RequestBody GroupsDto groupsDto) {
        var updatedGroup = groupService.update(groupId, toGroup(groupsDto));

        return ok(toGroupDto(updatedGroup));
    }
}
