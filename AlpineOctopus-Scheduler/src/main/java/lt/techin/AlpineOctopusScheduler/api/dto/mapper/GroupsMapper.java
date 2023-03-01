package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupsDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
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

        groupEntityDto.setModifiedDate(groups.getModifiedDate());


        return groupEntityDto;
    }

    public static Groups toGroup(GroupsEntityDto groupEntityDto) {
        var group = new Groups();

        group.setId(groupEntityDto.getId());
        group.setName(groupEntityDto.getName());
        group.setSchoolYear(groupEntityDto.getSchoolYear());
        group.setStudentAmount(groupEntityDto.getStudentAmount());
//        group.setProgram(groupEntityDto.getProgram());
//        group.setShift(groupEntityDto.getShift());

        return group;
    }
}
