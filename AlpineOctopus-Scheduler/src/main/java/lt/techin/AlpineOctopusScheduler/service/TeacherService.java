package lt.techin.AlpineOctopusScheduler.service;

<<<<<<< HEAD
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.GroupsMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.model.Groups;
=======
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Subject;
>>>>>>> main
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
import java.util.stream.Collectors;
=======
import java.util.Set;
>>>>>>> main

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

    public List<Teacher> getPagedAllTeachers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return teacherRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TeacherEntityDto> getTeachersByNameContaining(String nameText) {
        return teacherRepository.findByNameContainingIgnoreCase(nameText).stream()
                .map(TeacherMapper::toTeacherEntityDto).collect(Collectors.toList());
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
