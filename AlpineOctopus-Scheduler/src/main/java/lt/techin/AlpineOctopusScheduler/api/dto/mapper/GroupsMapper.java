package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupsDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsTestDto;
import lt.techin.AlpineOctopusScheduler.model.Groups;

public class GroupsMapper {

    public static GroupsDto toGroupDto(Groups groups) {
        var groupDto = new GroupsDto();

        groupDto.setName(groups.getName());
        groupDto.setSchoolYear(groups.getSchoolYear());
        groupDto.setStudentAmount(groups.getStudentAmount());
//        groupDto.setProgram(groups.getProgram());
//        groupDto.setShift(groups.getShift());
//        groupDto.setModifiedDate(groups.getModifiedDate());

        return groupDto;
    }

    public static Groups toGroup(GroupsDto groupsDto) {
        var group = new Groups();

        group.setName(groupsDto.getName());
        group.setSchoolYear(groupsDto.getSchoolYear());
        group.setStudentAmount(groupsDto.getStudentAmount());
//        group.setProgram(groupsDto.getProgram());
//        group.setShift(groupsDto.getShift());

        return group;
    }

    public static GroupsEntityDto toGroupEntityDto(Groups groups) {
        var groupEntityDto = new GroupsEntityDto();

        groupEntityDto.setId(groups.getId());
        groupEntityDto.setName(groups.getName());
        groupEntityDto.setSchoolYear(groups.getSchoolYear());
        groupEntityDto.setStudentAmount(groups.getStudentAmount());
        groupEntityDto.setProgramName(groups.getProgram().getName());
        groupEntityDto.setShiftName(groups.getShift().getName());
        groupEntityDto.setProgramId(groups.getProgram().getId());
        groupEntityDto.setShiftId(groups.getShift().getId());
        groupEntityDto.setModifiedDate(groups.getModifiedDate());
        groupEntityDto.setDeleted(groups.getDeleted());

        return groupEntityDto;
    }

    public static Groups toGroup(GroupsEntityDto groupEntityDto) {
        var groups = new Groups();


        groups.setId(groupEntityDto.getId());
        groups.setName(groupEntityDto.getName());
        groups.setSchoolYear(groupEntityDto.getSchoolYear());
        groups.setStudentAmount(groupEntityDto.getStudentAmount());
        groups.setDeleted(groupEntityDto.getDeleted());
//        groups.set(groupEntityDto.getProgram());
//        groups.setShift(groupEntityDto.getShift());

        return groups;
    }

    public static GroupsTestDto toGroupsTestDto(Groups groups) {
        var groupsDto = new GroupsTestDto();

        groupsDto.setId(groups.getId());
        return groupsDto;
    }

}
