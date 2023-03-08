package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.model.Lesson;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;

public class LessonMapper {

    public static Lesson toLessonFromSubject(ProgramSubjectHours subject) {
        var lesson = new Lesson();

        lesson.setSubject(subject.getSubject());
        lesson.setLessonNumber(subject.getSubjectHours());


        return lesson;
    }
}
