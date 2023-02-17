package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//Mantvydas Juršys


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {

        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Teacher> getAll() {

        return teacherRepository.findAll();
    }

    public Optional<Teacher> getById(Long id) {

        return teacherRepository.findById(id);
    }

    

    public Teacher create(Teacher teacher) {

        return teacherRepository.save(teacher);
    }

    public Teacher update(Long id, Teacher teacher) {

        teacher.setId(id);

        return teacherRepository.save(teacher);
    }

    public boolean deleteById(Long id) {

        if(teacherRepository.existsById(id)) {

            teacherRepository.deleteById(id);
            return  true;
        }
        return false;
    }

    public Teacher addSubjectToTeacher(Long teacherId, Long subjectId) {
        var existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist",
                        "id", "Teacher not found", teacherId.toString()));

        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        Set<Subject> existingSubjectList = existingTeacher.getTeachersSubjects();
        existingSubjectList.add(existingSubject);
        existingTeacher.setTeachersSubjects(existingSubjectList);

        return teacherRepository.save(existingTeacher);
    }











}
