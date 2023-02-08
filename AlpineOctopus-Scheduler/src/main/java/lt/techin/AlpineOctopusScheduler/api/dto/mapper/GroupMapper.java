package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.GroupDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Group;

public class GroupMapper {

    public static GroupDto toGroupDto(Group group){
        var groupDto = new GroupDto();

        groupDto.setName(group.getName());
        groupDto.setSchoolYear(group.getSchoolYear());
        groupDto.setStudentAmount(group.getStudentAmount());
        groupDto.setProgram(group.getProgram());
        groupDto.setShift(group.getShift());

        return groupDto;
    }

    public static Group toGroup(GroupDto groupDto){
        var group = new Group();

        group.setName(groupDto.getName());
        group.setSchoolYear(groupDto.getSchoolYear());
        group.setStudentAmount(groupDto.getStudentAmount());
        group.setProgram(groupDto.getProgram());
        group.setShift(groupDto.getShift());

        return group;
    }

    public static GroupEntityDto toGroupEntityDto(Group group){
        var groupEntityDto = new GroupEntityDto();

        groupEntityDto.setName(group.getName());
        groupEntityDto.setSchoolYear(group.getSchoolYear());
        groupEntityDto.setStudentAmount(group.getStudentAmount());
        groupEntityDto.setProgram(group.getProgram());
        groupEntityDto.setShift(group.getShift());

        return groupEntityDto;
    }

    public static Group toGroup(GroupEntityDto groupEntityDto){
        var group = new Group();

        group.setName(groupEntityDto.getName());
        group.setSchoolYear(groupEntityDto.getSchoolYear());
        group.setStudentAmount(groupEntityDto.getStudentAmount());
        group.setProgram(groupEntityDto.getProgram());
        group.setShift(groupEntityDto.getShift());

        return group;
    }
}
