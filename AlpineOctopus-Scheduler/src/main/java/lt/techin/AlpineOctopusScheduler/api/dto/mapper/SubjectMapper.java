package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectTestDto;
import lt.techin.AlpineOctopusScheduler.model.Subject;

public class SubjectMapper {


    public static SubjectDto toSubjectDto(Subject subject) {
        var subjectDto = new SubjectDto();

        subjectDto.setName(subject.getName());
        subjectDto.setDescription(subject.getDescription());
        subjectDto.setCreatedDate(subject.getCreatedDate());
        subjectDto.setModifiedDate(subject.getModifiedDate());
        subjectDto.setSubjectModules(subject.getSubjectModules());
        subjectDto.setSubjectRooms(subject.getSubjectRooms());
        subjectDto.setSubjectTeachers(subject.getSubjectTeachers());


        return subjectDto;
    }

    public static Subject toSubject(SubjectDto subjectDto) {
        var subject = new Subject();

        subject.setName(subjectDto.getName());
        subject.setDescription(subjectDto.getDescription());
        subject.setCreatedDate(subjectDto.getCreatedDate());
        subject.setModifiedDate(subjectDto.getModifiedDate());
        subject.setSubjectModules(subjectDto.getSubjectModules());
        subject.setSubjectRooms(subjectDto.getSubjectRooms());
        subject.setSubjectTeachers(subjectDto.getSubjectTeachers());


        return subject;
    }


    public static SubjectEntityDto toSubjectEntityDto(Subject subject) {
        var subjectEntityDto = new SubjectEntityDto();

        subjectEntityDto.setId(subject.getId());
        subjectEntityDto.setName(subject.getName());
        subjectEntityDto.setDescription(subject.getDescription());
        subjectEntityDto.setCreatedDate(subject.getCreatedDate());
        subjectEntityDto.setModifiedDate(subject.getModifiedDate());
        subjectEntityDto.setSubjectModules(subject.getSubjectModules());
        subjectEntityDto.setSubjectRooms(subject.getSubjectRooms());
        subjectEntityDto.setSubjectTeachers(subject.getSubjectTeachers());


        return subjectEntityDto;
    }

    public static Subject toSubject(SubjectEntityDto subjectEntityDto) {
        var subject = new Subject();

        subject.setId(subjectEntityDto.getId());
        subject.setName(subjectEntityDto.getName());
        subject.setDescription(subjectEntityDto.getDescription());
        subject.setCreatedDate(subjectEntityDto.getCreatedDate());
        subject.setModifiedDate(subjectEntityDto.getModifiedDate());
        subject.setSubjectModules(subjectEntityDto.getSubjectModules());
        subject.setSubjectRooms(subjectEntityDto.getSubjectRooms());
        subject.setSubjectTeachers(subjectEntityDto.getSubjectTeachers());


        return subject;
    }

    public static SubjectTestDto toSubjectTestDto(Subject subject) {
        var subjectDto = new SubjectTestDto();

        subjectDto.setId(subject.getId());
        return subjectDto;
    }

}
