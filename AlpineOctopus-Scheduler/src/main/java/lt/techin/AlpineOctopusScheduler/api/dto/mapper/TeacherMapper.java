package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Teacher;

public class TeacherMapper {

    public static TeacherDto toTeacherDto(Teacher teacher) {

        var teacherDto = new TeacherDto();

        teacherDto.setName(teacher.getName());
        teacherDto.setSurname(teacher.getSurname());
        teacherDto.setLoginEmail(teacher.getLoginEmail());
        teacherDto.setWorkHoursPerWeek(teacher.getWorkHoursPerWeek());
        teacherDto.setContactEmail(teacher.getContactEmail());
        teacherDto.setPhone(teacher.getPhone());
        teacherDto.setShift(teacher.getShift());




        return teacherDto;
    }

    public static Teacher toTeacher(TeacherDto teacherDto) {

        var teacher = new Teacher();

        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setLoginEmail(teacherDto.getLoginEmail());
        teacher.setContactEmail(teacherDto.getContactEmail());
        teacher.setWorkHoursPerWeek(teacherDto.getWorkHoursPerWeek());
        teacher.setPhone(teacherDto.getPhone());
        teacher.setShift(teacherDto.getShift());



        return teacher;
    }

    public static TeacherEntityDto toTeacherEntityDto(Teacher teacher) {

        var teacherEntityDto = new TeacherEntityDto();

        teacherEntityDto.setId(teacher.getId());
        teacherEntityDto.setName(teacher.getName());
        teacherEntityDto.setSurname(teacher.getSurname());
        teacherEntityDto.setLoginEmail(teacher.getLoginEmail());
        teacherEntityDto.setContactEmail(teacher.getContactEmail());
        teacherEntityDto.setWorkHoursPerWeek(teacher.getWorkHoursPerWeek());
        teacherEntityDto.setPhone(teacher.getPhone());
        teacherEntityDto.setShift(teacher.getShift());


        return teacherEntityDto;
    }

    public static Teacher toTeacher(TeacherEntityDto teacherEntityDto) {

        var teacher = new Teacher();

        teacher.setId(teacherEntityDto.getId());
        teacher.setName(teacherEntityDto.getName());
        teacher.setSurname(teacherEntityDto.getSurname());
        teacher.setLoginEmail(teacherEntityDto.getLoginEmail());
        teacher.setContactEmail(teacherEntityDto.getContactEmail());
        teacher.setWorkHoursPerWeek(teacherEntityDto.getWorkHoursPerWeek());
        teacher.setPhone(teacherEntityDto.getPhone());
        teacher.setShift(teacherEntityDto.getShift());

        return teacher;
    }
}
