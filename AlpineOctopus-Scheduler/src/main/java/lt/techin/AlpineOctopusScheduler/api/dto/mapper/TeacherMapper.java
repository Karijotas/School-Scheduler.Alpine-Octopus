package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Teacher;

public class TeacherMapper {

    public static TeacherDto toTeacherDto(Teacher teacher) {

        var teacherDto = new TeacherDto();

        teacherDto.setName(teacher.getName());
        teacherDto.setSurname(teacher.getSurname());
        teacherDto.setContactEmail(teacher.getContactEmail());
        teacherDto.setPhone(teacher.getPhone());
        teacherDto.setShift(teacher.getShift());



        return teacherDto;
    }

    public static Teacher toTeacher(TeacherDto teacherDto) {

        var teacher = new Teacher();

        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setContactEmail(teacherDto.getContactEmail());
        teacher.setPhone(teacherDto.getPhone());
        teacher.setShift(teacherDto.getShift());


        return teacher;
    }

    public static TeacherEntityDto toTeacherEntityDto(Teacher teacher) {

        var teacherEntityDto = new TeacherEntityDto();

        teacherEntityDto.setId(teacher.getId());
        teacherEntityDto.setName(teacher.getName());
        teacherEntityDto.setSurname(teacher.getSurname());
        teacherEntityDto.setContactEmail(teacher.getContactEmail());
        teacherEntityDto.setPhone(teacher.getPhone());
        teacherEntityDto.setShift(teacher.getShift());
        teacherEntityDto.setTeachersSubjects(teacher.getTeachersSubjects());

        return teacherEntityDto;
    }

    public static Teacher toTeacher(TeacherEntityDto teacherEntityDto) {

        var teacher = new Teacher();

        teacher.setId(teacherEntityDto.getId());
        teacher.setName(teacherEntityDto.getName());
        teacher.setSurname(teacherEntityDto.getSurname());
        teacher.setContactEmail(teacherEntityDto.getContactEmail());
        teacher.setPhone(teacherEntityDto.getPhone());
        teacher.setShift(teacherEntityDto.getShift());
        teacher.setTeachersSubjects(teacherEntityDto.getTeachersSubjects());

        return teacher;
    }
}
