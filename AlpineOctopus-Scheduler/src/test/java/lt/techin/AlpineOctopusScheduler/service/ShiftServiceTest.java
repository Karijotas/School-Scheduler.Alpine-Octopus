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

import lt.techin.AlpineOctopusScheduler.api.dto.ShiftTestDto;
import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Shift;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTest {

	@Mock
	ShiftRepository shiftRepository;

	@InjectMocks
	ShiftService shiftService;

	@Test
	void update_shiftNotFound_throwsException() {

		Shift shift = lt.techin.AlpineOctopusScheduler.stubs.ShiftCreator.createShift(12l);

		when(shiftRepository.findById(12l)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> shiftService.update(12l, shift)).isInstanceOf(SchedulerValidationException.class)
				.hasMessageContaining("Shift does not exist").hasFieldOrPropertyWithValue("rejectedValue", "12");
	}

	@Test
	public void getAll_ReturnsShiftTest() {

		List<Shift> expectedShifts = new ArrayList<>();
        expectedShifts.add(new Shift(1l, "Shift 1", 1, 2, LocalDateTime.now(), LocalDateTime.now(), false));
        expectedShifts.add(new Shift(2l, "Shift 2", 3, 4, LocalDateTime.now(), LocalDateTime.now(), false));

        Mockito.when(shiftRepository.findAll()).thenReturn(expectedShifts);

        List<Shift> actualShifts = shiftService.getAll();

        Assertions.assertThat(expectedShifts.size()).isEqualTo(actualShifts.size());
        
        
        Assertions.assertThat(expectedShifts.get(0)).isEqualTo(actualShifts.get(0));
        Assertions.assertThat(expectedShifts.get(1)).isEqualTo(actualShifts.get(1));
		
		
	}
	
	
	@Test
    public void testCreateShift() {
		
        Shift shift = lt.techin.AlpineOctopusScheduler.stubs.ShiftCreator.createShift(12l); 
        
        
        Mockito.when(shiftRepository.save(Mockito.any(Shift.class))).thenReturn(shift);

        Shift savedShift = shiftService.create(shift);
        
        Mockito.verify(shiftRepository, Mockito.times(1)).save(Mockito.any(Shift.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(shift.getId()).isEqualTo(savedShift.getId());
	
	}
	
	@Test
	public void testDeleteShift() {
		
		Shift deletedItem = lt.techin.AlpineOctopusScheduler.stubs.ShiftCreator.createShift(12l);
        Mockito.when(shiftRepository.findById(12l)).thenReturn(Optional.of(deletedItem));

        shiftService.deleteShift(12l);

        Mockito.verify(shiftRepository, Mockito.times(1)).save(deletedItem);
        Assertions.assertThat(deletedItem.getDeleted()).isEqualTo(true); 
		
	}
	
	@Test
	public void testDeleteById() {
		
		// Perform delete
		shiftService.deleteById(1l);
		
		// Verify that the delete method was called with the correct shift ID
		Mockito.verify(shiftRepository, Mockito.times(1)).deleteById(1l);
	}
	
	@Test
	public void testRestoreShift() {
		
		Shift restoredItem = lt.techin.AlpineOctopusScheduler.stubs.ShiftCreator.createShift(12l);
        Mockito.when(shiftRepository.findById(12l)).thenReturn(Optional.of(restoredItem));

        shiftService.restoreShift(12l);

        Mockito.verify(shiftRepository, Mockito.times(1)).save(restoredItem);
        Assertions.assertThat(restoredItem.getDeleted()).isEqualTo(false);
		
	}

}
