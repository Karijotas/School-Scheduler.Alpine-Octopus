package lt.techin.AlpineOctopusScheduler.stubs;

import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.HashSet;
import java.util.Set;

public class ProgramCreator {

    public static Program createProgram(Long id) {
        var program = new Program();
        Set<Subject> subjects = new HashSet<>();

        program.setId(id);
        program.setName("Programa puikioji");
        program.setDescription("o tai tau vai vai vai");
        program.setHours(null);

        return program;
    }
}
