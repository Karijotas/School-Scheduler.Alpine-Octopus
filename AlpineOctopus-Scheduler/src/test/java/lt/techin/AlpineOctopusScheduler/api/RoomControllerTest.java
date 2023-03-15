package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomTestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@MockBeans({@MockBean(RoomController.class), @MockBean(RoomService.class), @MockBean(RoomRepository.class)})
@AutoConfigureMockMvc
@ContextConfiguration(classes = AlpineOctopusSchedulerApplication.class)
public class RoomControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getRooms_returnsCorrectDtos() throws Exception {

        var roomDto1 = new RoomTestDto(1l);
        var roomDto2 = new RoomTestDto(2l);
        var roomDto3 = new RoomTestDto(3l);
        var roomDto4 = new RoomTestDto(4l);
        var roomDto5 = new RoomTestDto(5l);
        var roomDto6 = new RoomTestDto(6l);
        var roomDto7 = new RoomTestDto(7l);
        var roomDto8 = new RoomTestDto(8l);
        var roomDto9 = new RoomTestDto(9l);
        var roomDto10 = new RoomTestDto(10l);
        var roomDto11 = new RoomTestDto(11l);
        var roomDto12 = new RoomTestDto(12l);
        var roomDto13 = new RoomTestDto(13l);
        var roomDto14 = new RoomTestDto(14l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/rooms/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<RoomTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(roomDto1, roomDto2, roomDto3, roomDto4, roomDto5, roomDto6, roomDto7, roomDto8, roomDto9, roomDto10, roomDto11, roomDto12, roomDto13, roomDto14);
    }


    @Test
    void getRoom() {
    }

//    @Test
//    void createRoom_shouldCreateRoom() {
//        Room newRoom = new Room();
//        newRoom.setName("Kabinetas 11");
//        newRoom.setBuilding("Ozo g. 171");
//        newRoom.setDescription("Naujai paruoštas kabinetas pirmos klasės mokiniams.");
//
//        var mvcResult = mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .get("/api/v1/rooms")
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString());
//
//        assertThat(mappedResponse);
//    }
//
//
//    }
}
