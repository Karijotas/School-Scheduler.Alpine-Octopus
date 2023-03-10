package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.model.Lesson;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;

public class LessonMapper {

    public static Lesson toLessonFromSubject(ProgramSubjectHours subject) {
        var lesson = new Lesson();

        lesson.setSubject(subject.getSubject());
        lesson.setLessonHours(subject.getSubjectHours());


        return lesson;
    }


    public static Lesson toIndividualLesson(Lesson lesson) {
        var newLesson = new Lesson();

        newLesson.setSubject(lesson.getSubject());
        newLesson.setTeacher(lesson.getTeacher());
        newLesson.setRoom(lesson.getRoom());


        return newLesson;
    }
}
