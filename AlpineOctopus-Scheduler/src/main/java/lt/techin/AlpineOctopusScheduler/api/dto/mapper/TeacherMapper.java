package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherTestDto;
import lt.techin.AlpineOctopusScheduler.model.Teacher;

public class TeacherMapper {

    public static TeacherDto toTeacherDto(Teacher teacher) {

        var teacherDto = new TeacherDto();

        teacherDto.setName(teacher.getName());
        teacherDto.setLoginEmail(teacher.getLoginEmail());
        teacherDto.setWorkHoursPerWeek(teacher.getWorkHoursPerWeek());
        teacherDto.setContactEmail(teacher.getContactEmail());
        teacherDto.setPhone(teacher.getPhone());
        teacherDto.setTeacherShifts(teacher.getTeacherShifts());


        return teacherDto;
    }

    public static Teacher toTeacher(TeacherDto teacherDto) {

        var teacher = new Teacher();

        teacher.setName(teacherDto.getName());
        teacher.setLoginEmail(teacherDto.getLoginEmail());
        teacher.setContactEmail(teacherDto.getContactEmail());
        teacher.setWorkHoursPerWeek(teacherDto.getWorkHoursPerWeek());
        teacher.setPhone(teacherDto.getPhone());
        teacher.setTeacherShifts(teacherDto.getTeacherShifts());


        return teacher;
    }

    public static TeacherEntityDto toTeacherEntityDto(Teacher teacher) {

        var teacherEntityDto = new TeacherEntityDto();

        teacherEntityDto.setId(teacher.getId());
        teacherEntityDto.setName(teacher.getName());
        teacherEntityDto.setLoginEmail(teacher.getLoginEmail());
        teacherEntityDto.setContactEmail(teacher.getContactEmail());
        teacherEntityDto.setWorkHoursPerWeek(teacher.getWorkHoursPerWeek());
        teacherEntityDto.setPhone(teacher.getPhone());
        teacherEntityDto.setTeacherShifts(teacher.getTeacherShifts());


        return teacherEntityDto;
    }

    public static Teacher toTeacher(TeacherEntityDto teacherEntityDto) {

        var teacher = new Teacher();

        teacher.setId(teacherEntityDto.getId());
        teacher.setName(teacherEntityDto.getName());
        teacher.setLoginEmail(teacherEntityDto.getLoginEmail());
        teacher.setContactEmail(teacherEntityDto.getContactEmail());
        teacher.setWorkHoursPerWeek(teacherEntityDto.getWorkHoursPerWeek());
        teacher.setPhone(teacherEntityDto.getPhone());
        teacher.setTeacherShifts(teacherEntityDto.getTeacherShifts());

        return teacher;
    }

    public static TeacherTestDto toTeacherTestDto(Teacher teacher) {
        var teacherDto = new TeacherTestDto();

        teacherDto.setId(teacher.getId());
        return teacherDto;
    }
}
