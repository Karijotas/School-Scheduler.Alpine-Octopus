package lt.techin.AlpineOctopusScheduler.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.model.Shift;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTest {
	
	@Mock
    ShiftRepository shiftRepository;

    @InjectMocks
    ShiftService shiftService;
	
	
    @Test
    void update_shiftNotFound_throwsException() {
    	
        Shift shift = lt.techin.AlpineOctopusScheduler.stubs.ShiftCreator.createShift(121l);

        when(shiftRepository.findById(124l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> shiftService.update(124l, shift))
//                .isInstanceOf(SchedulerValidationException.class)    // as nezinau koki exception'a reikia priskirti. ??
                .hasMessageContaining("Shift does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "1");
    }

}
