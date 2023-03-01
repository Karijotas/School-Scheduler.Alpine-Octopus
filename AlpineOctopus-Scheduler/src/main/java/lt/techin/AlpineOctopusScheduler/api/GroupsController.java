package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import lt.techin.AlpineOctopusScheduler.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper.toGroup;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper.toGroupDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/groups")
@Validated
public class GroupsController {

    private final Logger logger = LoggerFactory.getLogger(GroupsController.class);
    private final GroupService groupService;

    public GroupsController(GroupService groupService,
                            GroupsRepository groupsRepository) {
        this.groupService = groupService;
    }


    @GetMapping
    @ResponseBody
    public List<GroupsEntityDto> getAvailableGroups() {
        return groupService.getAllAvailableGroups();
    }


    @GetMapping(path = "/archive/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<GroupsEntityDto> getDeletedGroups() {
        return groupService.getAllDeletedGroups();
    }


    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<GroupsEntityDto> getPagedAvailableGroups(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        return groupService.getAllAvailablePagedGroups(page, pageSize);
    }

    @GetMapping(path = "/archive/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<GroupsEntityDto> getPagedDeletedGroups(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                       @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return groupService.getAllDeletedPagedGroups(page, pageSize);
    }

    @GetMapping(path = "/page/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody

    public List<GroupsEntityDto> getPagedAllGroups(@RequestParam(value = "page", defaultValue = "0", required = false) int page,

                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return groupService.getAllAvailableGroups();

    }

//    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,})
//    @ResponseBody
//    public List<GroupsEntityDto> getGroups() {
//        return groupService.getAll()
//                .stream()
//                .map(GroupsMapper::toGroupEntityDto)
//                .collect(toList());
//    }

//    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public List<GroupsEntityDto> getPagedAllGroups(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
//                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//
//        return groupService.getPagedAllGroups(page, pageSize).stream()
//                .map(GroupsMapper::toGroupEntityDto)
//                .collect(toList());
//    }

    @GetMapping(path = "/name-filter/{nameText}")
    @ApiOperation(value = "Get Programs starting with", notes = "Returns list of Programs starting with passed String")
    @ResponseBody
    public List<GroupsEntityDto> getGroupsByNameContaining(@PathVariable String nameText) {
        return groupService.getGroupsByNameContaining(nameText);
    }

    @GetMapping(path = "/year-filter/{schoolYearText}")
    @ApiOperation(value = "Get Programs starting with", notes = "Returns list of Programs starting with passed String")
    @ResponseBody
    public List<GroupsEntityDto> getGroupsBySchoolYear(@PathVariable Integer schoolYearText) {
        return groupService.getGroupsBySchoolYear(schoolYearText);
    }

    @GetMapping(path = "/program-filter/{programText}")
    @ApiOperation(value = "Get Programs starting with", notes = "Returns list of Programs starting with passed String")
    @ResponseBody
    public List<GroupsEntityDto> getGroupsByProgram(@PathVariable String programText) {
        return groupService.getGroupsByProgram(programText);
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
    public ResponseEntity<GroupsDto> createGroup(@Valid @RequestBody GroupsDto groupsDto, Long programId, Long shiftId) {
        if (groupService.groupNameIsUnique(toGroup(groupsDto))) {
            if (groupService.studentAmountIsValid(toGroup(groupsDto))) {
                if (groupService.schoolYearIsValid(toGroup(groupsDto))) {
                    var createdGroup = groupService.create(toGroup(groupsDto), programId, shiftId);
                    return ok(toGroupDto(createdGroup));
                } else {
                    throw new SchedulerValidationException("Invalid year value", "School year", "School year must be between 2023-3023", groupsDto.getSchoolYear().toString());
                }
            } else {
                throw new SchedulerValidationException("Group already exists", "Group name", "Already exists", groupsDto.getName());
            }
        } else {
            throw new SchedulerValidationException("Invalid student amount", "Student amount", "Student amount must be between 1-300", groupsDto.getStudentAmount().toString());
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        logger.info("Attempt to delete Group by id: {}", groupId);
        boolean deleted = groupService.deleteById(groupId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{groupId}")
//    public ResponseEntity<GroupsDto> replaceGroup(@PathVariable Long groupId, @Valid @RequestBody GroupsDto groupsDto) {
//        if (groupService.studentAmountIsValid(toGroup(groupsDto))) {
//            if (groupService.schoolYearIsValid(toGroup(groupsDto))) {
//                var updatedGroup = groupService.replace(groupId, toGroup(groupsDto));
//                return ok(toGroupDto(updatedGroup));
//            } else {
//                throw new SchedulerValidationException("Invalid year value", "School year", "School year must be between 2023-3023", groupsDto.getSchoolYear().toString());
//            }
//        } else {
//            throw new SchedulerValidationException("Invalid student amount", "Student amount", "Student amount must be between 1-300", groupsDto.getStudentAmount().toString());
//        }
//    }

    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupsDto> updateGroup(@PathVariable Long groupId, @Valid @RequestBody GroupsDto groupsDto, Long programId, Long shiftId) {
        if (groupService.studentAmountIsValid(toGroup(groupsDto))) {
            if (groupService.schoolYearIsValid(toGroup(groupsDto))) {
                var updatedGroup = groupService.update(groupId, toGroup(groupsDto), programId, shiftId);
                return ok(toGroupDto(updatedGroup));
            } else {
                throw new SchedulerValidationException("Invalid year value", "School year", "School year must be between 2023-3023", groupsDto.getSchoolYear().toString());
            }
        } else {
            throw new SchedulerValidationException("Invalid student amount", "Student amount", "Student amount must be between 1-300", groupsDto.getStudentAmount().toString());
        }
    }

    @PatchMapping("/delete/{groupId}")
    public ResponseEntity<GroupsEntityDto> removeGroup(@PathVariable Long groupId) {
        var updatedGroup = groupService.deleteGroup(groupId);
        return ok(updatedGroup);
    }

    @PatchMapping("/restore/{groupId}")
    public ResponseEntity<GroupsEntityDto> restoreGroup(@PathVariable Long groupId) {
        var updatedGroup = groupService.restoreGroup(groupId);
        return ok(updatedGroup);
    }
}
