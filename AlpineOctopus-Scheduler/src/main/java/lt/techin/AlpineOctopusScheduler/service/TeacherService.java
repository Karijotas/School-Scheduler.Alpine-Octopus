package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Mantvydas Juršys


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {

        this.teacherRepository = teacherRepository;
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













}
