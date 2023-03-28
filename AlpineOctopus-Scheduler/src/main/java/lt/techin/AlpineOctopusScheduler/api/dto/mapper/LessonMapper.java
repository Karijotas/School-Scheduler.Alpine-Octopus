package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.dao.LessonRepository;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.model.Lesson;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import lt.techin.AlpineOctopusScheduler.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class LessonMapper {

    static TeacherRepository teacherRepository;
    static LessonRepository lessonRepository;

    public static Lesson toLessonFromSubject(ProgramSubjectHours subject) {
        var lesson = new Lesson();
        List<Teacher> teachersList = new ArrayList<>();

        lesson.setSubject(subject.getSubject());
        lesson.setLessonHours(subject.getSubjectHours());
        lesson.setSubjectTeachers(teachersList);

        return lesson;
    }


    public static Lesson toIndividualLesson(Lesson lesson) {
        var newLesson = new Lesson();

        newLesson.setSubject(lesson.getSubject());
        newLesson.setTeacher(lesson.getTeacher());
        newLesson.setRoom(lesson.getRoom());
        newLesson.setStatus(lesson.getStatus());


        return newLesson;
    }
}
