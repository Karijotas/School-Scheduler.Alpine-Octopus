package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomDto;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomTestDto;
import lt.techin.AlpineOctopusScheduler.model.Room;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
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


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    void createRoom_shouldCreateRoom() throws Exception {
        Room newRoom = new Room();
        newRoom.setName("Kabinetas 14");
        newRoom.setBuilding("Ozo g. 174");
        newRoom.setDescription("Naujai paruoštas kabinetas pirmos klasės mokiniams.");

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/rooms")
                                .content(asJsonString(newRoom))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @Order(3)
    void updateRoom_shouldUpdateRoom() throws Exception {
        Room newRoom2 = new Room();
        newRoom2.setName("Kabinetukas");
        newRoom2.setBuilding("Pylimo g. 16");
        newRoom2.setDescription("Labai puikus");


        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/rooms/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newRoom2)))
                .andReturn();


        assertEquals("Kabinetukas", newRoom2.getName());
        assertEquals("Labai puikus", newRoom2.getDescription());
    }

    @Test
    @Order(2)
    void getRoom_shouldReturnCorrectRoom() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/rooms/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<RoomDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "LAK-101");
    }

    @Test
    @Order(5)
    void removeRoom_shouldSetBooleanToTrue() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/rooms/delete/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Room mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Room>() {
                });

        Assertions.assertTrue(mappedResponse.getDeleted());
    }

    @Test
    void restoreRoom_shouldSetBooleanToFalse() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/rooms/restore/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Room mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Room>() {
                });

        Assertions.assertFalse(mappedResponse.getDeleted());
    }
//    @Test
//    void deleteTeacherSetsDeletedPropertyToTrue() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/rooms/delete/4").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        Room resultTeacher = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Room>() {});
//        Assertions.assertTrue(resultTeacher.isDeleted());
//    }

}

