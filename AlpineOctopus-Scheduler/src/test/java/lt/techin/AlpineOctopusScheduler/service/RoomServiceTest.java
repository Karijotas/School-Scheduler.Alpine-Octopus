package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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

    @Test
    public void getAll_ReturnsRoomTest() {

        List<Room> extpectedRooms = new ArrayList<>();
        extpectedRooms.add(new Room(1l, "pirmas kambarys", "pirmas pastatas", "siltas pastatas", false, LocalDateTime.now(), LocalDateTime.now()));
        extpectedRooms.add(new Room(2l, "antras kambarys", "antras pastatas", "siltas+ pastatas", false, LocalDateTime.now(), LocalDateTime.now()));

        Mockito.when(roomRepository.findAll()).thenReturn(extpectedRooms);

        List<Room> actualRooms = roomService.getAll();

        Assertions.assertThat(extpectedRooms.size()).isEqualTo(actualRooms.size());


        Assertions.assertThat(extpectedRooms.get(0)).isEqualTo(actualRooms.get(0));
        Assertions.assertThat(extpectedRooms.get(1)).isEqualTo(actualRooms.get(1));


    }

    @Test
    public void testCreateRoom() {

        Room room = lt.techin.AlpineOctopusScheduler.stubs.RoomCreator.createRoom(12l);


        Mockito.when(roomRepository.save(Mockito.any(Room.class))).thenReturn(room);

        Room savedRoom = roomService.create(room);

        Mockito.verify(roomRepository, Mockito.times(1)).save(Mockito.any(Room.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(room.getId()).isEqualTo(savedRoom.getId());

    }

    @Test
    public void testDeleteRoom() {

        Room deletedItem = lt.techin.AlpineOctopusScheduler.stubs.RoomCreator.createRoom(12l);
        Mockito.when(roomRepository.findById(12l)).thenReturn(Optional.of(deletedItem));

        roomService.deleteRoom(12l);

        Mockito.verify(roomRepository, Mockito.times(1)).save(deletedItem);
        Assertions.assertThat(deletedItem.getDeleted()).isEqualTo(true);

    }

//    @Test
//	public void testDeleteById() {
//
//		// Perform delete
//		roomService.deleteById(1l);
//
//		// Verify that the delete method was called with the correct room ID
////		Mockito.verify(roomRepository, Mockito.times(1)).deleteById(1l);
//	}


    @Test
    public void testRestoreRoom() {

        Room restoredItem = lt.techin.AlpineOctopusScheduler.stubs.RoomCreator.createRoom(12l);
        Mockito.when(roomRepository.findById(12l)).thenReturn(Optional.of(restoredItem));

        roomService.restoreRoom(12l);

        Mockito.verify(roomRepository, Mockito.times(1)).save(restoredItem);
        Assertions.assertThat(restoredItem.getDeleted()).isEqualTo(false);

    }

}
