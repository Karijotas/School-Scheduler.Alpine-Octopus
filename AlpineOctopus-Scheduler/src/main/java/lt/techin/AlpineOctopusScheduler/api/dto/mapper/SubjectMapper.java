package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Subject;

public class SubjectMapper {


    public static SubjectDto toSubjectDto(Subject subject) {
        var subjectDto = new SubjectDto();

        subjectDto.setName(subject.getName());
        subjectDto.setDescription(subject.getDescription());


        return subjectDto;
    }

    public static Subject toSubject(SubjectDto subjectDto) {
        var subject = new Subject();

        subject.setName(subjectDto.getName());
        subject.setDescription(subjectDto.getDescription());

        return subject;
    }


    public static SubjectEntityDto toSubjectEntityDto(Subject subject) {
        var subjectDto = new SubjectEntityDto();

        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());
        subjectDto.setDescription(subject.getDescription());


        return subjectDto;
    }

    public static Subject toSubject(SubjectEntityDto subjectEntityDto) {
        var subject = new Subject();

        subject.setId(subjectEntityDto.getId());
        subject.setName(subjectEntityDto.getName());
        subject.setDescription(subjectEntityDto.getDescription());


        return subject;
    }

}
