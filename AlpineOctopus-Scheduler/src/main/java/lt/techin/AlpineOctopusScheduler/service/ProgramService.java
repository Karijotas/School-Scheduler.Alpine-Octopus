package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Program;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final SubjectRepository subjectRepository;

    public ProgramService(ProgramRepository programRepository, SubjectRepository subjectRepository) {
        this.programRepository = programRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Program> getAll(){
        return programRepository.findAll();
    }

    public Optional<Program> getById(Long id) {
        return programRepository.findById(id);
    }

    public Program create (Program program){
        return programRepository.save(program);
    }

    public Program update(Long id, Program program) {
        var existingProgram = programRepository.findById(id)
                        .orElseThrow(() -> new SchedulerValidationException("Program does not exist",
                "id", "Program not found", id.toString()));

        existingProgram.setName(program.getName());
        existingProgram.setDescription(program.getDescription());
        existingProgram.setSubjectHours(program.getSubjectHours());

        return programRepository.save(existingProgram);
    }

    public boolean deleteById(Long id) {
        try {
            programRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception){
            return false;
        }

    }
}
