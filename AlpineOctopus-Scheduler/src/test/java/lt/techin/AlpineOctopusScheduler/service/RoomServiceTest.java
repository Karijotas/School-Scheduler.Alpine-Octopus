package lt.techin.AlpineOctopusScheduler.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Room;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

	@Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomService roomService;
    
    
    @Test
    void update_roomNotFound_throwsException() {
    	
        Room room = lt.techin.AlpineOctopusScheduler.stubs.RoomCreator.createRoom(123l);

        when(roomRepository.findById(123l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roomService.update(123l, room))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Room does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "123");
    }
    
}
