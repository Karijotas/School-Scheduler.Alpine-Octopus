package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftTestDto;
import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.model.Shift;
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
public class ShiftControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ShiftRepository shiftRepository;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @Order(1)
    void getShifts_returnsCorrectDtos() throws Exception {

        var shiftDto1 = new ShiftTestDto(1l);
        var shiftDto2 = new ShiftTestDto(2l);
        var shiftDto3 = new ShiftTestDto(3l);
        var shiftDto4 = new ShiftTestDto(4l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/shifts/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ShiftTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(shiftDto1, shiftDto2, shiftDto3, shiftDto4);
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
    void createShift_shouldCreateNewShiftSuccesfully() throws Exception {
        Shift newShift = new Shift();
        newShift.setName("Kabinetas 14");
        newShift.setStarts(2);
        newShift.setEnds(4);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/shifts")
                                .content(asJsonString(newShift))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


    @Test
    @Order(2)
    void updateShift_shouldUpdateShift() throws Exception {
        Shift newShift2 = new Shift();
        newShift2.setName("Kabinetas 15");
        newShift2.setStarts(3);
        newShift2.setEnds(6);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/shifts/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newShift2)))
                .andReturn();


        assertEquals(3, newShift2.getStarts());
        assertEquals("Kabinetas 15", newShift2.getName());
    }

    @Test
    @Order(3)
    void getShift_shouldReturnCorrectShift() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/shifts/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ShiftDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Rytas");
    }

    @Test
    @Order(5)
    void removeShift_shouldSetBooleanToTrue() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/shifts/delete/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Shift mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Shift>() {
                });

        Assertions.assertTrue(mappedResponse.getDeleted());
    }

    @Test
    void restoreShift_shouldSetBooleanToFalse() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/shifts/restore/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Shift mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Shift>() {
                });

        Assertions.assertFalse(mappedResponse.getDeleted());
    }
}
