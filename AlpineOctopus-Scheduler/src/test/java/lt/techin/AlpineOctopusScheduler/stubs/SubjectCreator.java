package lt.techin.AlpineOctopusScheduler.stubs;

import lt.techin.AlpineOctopusScheduler.model.Subject;

public class SubjectCreator {

    public static Subject createSubject(Long id) {
        var subject = new Subject();

        subject.setId(id);
        subject.setName("Matematika");
        subject.setDescription("Tikslieji mokslai");

        return subject;
    }
}
