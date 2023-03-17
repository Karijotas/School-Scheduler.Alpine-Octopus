package lt.techin.AlpineOctopusScheduler.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Teacher;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
	
	@Mock
    TeacherRepository teacherRepository;

    @InjectMocks
    TeacherService teacherService;
	
    @Test
    void update_teacherNotFound_throwsException() {
    	
        Teacher teacher = lt.techin.AlpineOctopusScheduler.stubs.TeacherCreator.createTeacher(101l);

        when(teacherRepository.findById(101l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teacherService.update(101l, teacher))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Teacher does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "101");
    }

}


