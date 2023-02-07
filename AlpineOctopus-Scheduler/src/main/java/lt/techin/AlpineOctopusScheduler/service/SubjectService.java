package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject update(Long id, Subject subject) {
        subject.setId(id);//FIXME will improve later

        return subjectRepository.save(subject);
    }

    public boolean deleteById(Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
