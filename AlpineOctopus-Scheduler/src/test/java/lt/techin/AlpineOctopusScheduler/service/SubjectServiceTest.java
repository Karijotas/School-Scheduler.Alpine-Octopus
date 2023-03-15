package lt.techin.AlpineOctopusScheduler.service;

import static lt.techin.AlpineOctopusScheduler.stubs.SubjectCreator.createSubject;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

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

import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Subject;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {


    @Mock
    SubjectRepository subjectRepository;

    @InjectMocks
    SubjectService subjectService;

    @Test
    void update_subjectNotFound_throwsException() {
        var subject = createSubject(1l);

        when(subjectRepository.findById(1l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subjectService.update(1l, subject))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Subject does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "1");
    }
    
    @Test
	public void getAll_ReturnsSubjectTest() {

		List<Subject> expectedSubjects = new ArrayList<>();
        expectedSubjects.add(lt.techin.AlpineOctopusScheduler.stubs.SubjectCreator.createSubject(12l));
        expectedSubjects.add(lt.techin.AlpineOctopusScheduler.stubs.SubjectCreator.createSubject(13l));

        Mockito.when(subjectRepository.findAll()).thenReturn(expectedSubjects);

        List<Subject> actualShifts = subjectService.getAll();

        Assertions.assertThat(expectedSubjects.size()).isEqualTo(actualShifts.size());
        
        
        Assertions.assertThat(expectedSubjects.get(0)).isEqualTo(actualShifts.get(0));
        Assertions.assertThat(expectedSubjects.get(1)).isEqualTo(actualShifts.get(1));
		
		
	}
    
    @Test
    public void testCreateSubject() {
		
        Subject subject = lt.techin.AlpineOctopusScheduler.stubs.SubjectCreator.createSubject(12l); 
        
        
        Mockito.when(subjectRepository.save(Mockito.any(Subject.class))).thenReturn(subject);

        Subject savedSubject = subjectService.create(subject);
        
        Mockito.verify(subjectRepository, Mockito.times(1)).save(Mockito.any(Subject.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(subject.getId()).isEqualTo(savedSubject.getId());
	
	}
    
    @Test
	public void testDeleteSubject() {
		
		Subject deletedItem = lt.techin.AlpineOctopusScheduler.stubs.SubjectCreator.createSubject(12l);
        Mockito.when(subjectRepository.findById(12l)).thenReturn(Optional.of(deletedItem));

        subjectService.deleteSubject(12l);

        Mockito.verify(subjectRepository, Mockito.times(1)).save(deletedItem);
        Assertions.assertThat(deletedItem.getDeleted()).isEqualTo(true); 
		
	}
    
    @Test
	public void testDeleteById() {
		
		// Perform delete
		subjectService.deleteById(1l);
		
		// Verify that the delete method was called with the correct shift ID
		Mockito.verify(subjectRepository, Mockito.times(1)).deleteById(1l);
	}
	
	@Test
	public void testRestoreSubject() {
		
		Subject restoredItem = lt.techin.AlpineOctopusScheduler.stubs.SubjectCreator.createSubject(12l);
        Mockito.when(subjectRepository.findById(12l)).thenReturn(Optional.of(restoredItem));

        subjectService.restoreSubject(12l);

        Mockito.verify(subjectRepository, Mockito.times(1)).save(restoredItem);
        Assertions.assertThat(restoredItem.getDeleted()).isEqualTo(false);
		
	}
	
	

}