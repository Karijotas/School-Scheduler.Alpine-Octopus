package lt.techin.AlpineOctopusScheduler.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Shift;
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
    
    
    @Test
	public void getAll_ReturnsTeacherTest() {

		List<Teacher> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(lt.techin.AlpineOctopusScheduler.stubs.TeacherCreator.createTeacher(1l));
        expectedTeachers.add(lt.techin.AlpineOctopusScheduler.stubs.TeacherCreator.createTeacher(2l));

        Mockito.when(teacherRepository.findAll()).thenReturn(expectedTeachers);

        List<Teacher> actualShifts = teacherService.getAll();

        Assertions.assertThat(expectedTeachers.size()).isEqualTo(actualShifts.size());
        
        
        Assertions.assertThat(expectedTeachers.get(0)).isEqualTo(actualShifts.get(0));
        Assertions.assertThat(expectedTeachers.get(1)).isEqualTo(actualShifts.get(1));
		
		
	}
    
    @Test
    public void testCreateTeacher() {
		
        Teacher teacher = lt.techin.AlpineOctopusScheduler.stubs.TeacherCreator.createTeacher(12l); 
        
        
        Mockito.when(teacherRepository.save(Mockito.any(Teacher.class))).thenReturn(teacher);

        Teacher savedTeacher = teacherService.create(teacher);
        
        Mockito.verify(teacherRepository, Mockito.times(1)).save(Mockito.any(Teacher.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(teacher.getId()).isEqualTo(savedTeacher.getId());
	
	}
    
    @Test
	public void testDeleteTeacher() {
		
		Teacher deletedItem = lt.techin.AlpineOctopusScheduler.stubs.TeacherCreator.createTeacher(12l);
        Mockito.when(teacherRepository.findById(12l)).thenReturn(Optional.of(deletedItem));

        teacherService.deleteTeacher(12l);

        Mockito.verify(teacherRepository, Mockito.times(1)).save(deletedItem);
        Assertions.assertThat(deletedItem.getDeleted()).isEqualTo(true); 
		
	}
    
    @Test
	public void testDeleteById() {
		
		// Perform delete
		teacherService.deleteById(1l);
		
		// Verify that the delete method was called with the correct shift ID
//		Mockito.verify(teacherRepository, Mockito.times(1)).deleteById(1l);
	}
	
	@Test
	public void testRestoreShift() {
		
		Teacher restoredItem = lt.techin.AlpineOctopusScheduler.stubs.TeacherCreator.createTeacher(12l);
        Mockito.when(teacherRepository.findById(12l)).thenReturn(Optional.of(restoredItem));

        teacherService.restoreTeacher(12l);

        Mockito.verify(teacherRepository, Mockito.times(1)).save(restoredItem);
        Assertions.assertThat(restoredItem.getDeleted()).isEqualTo(false);
		
	}
    

}


