package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleTestDto;
import lt.techin.AlpineOctopusScheduler.model.Schedule;
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
public class ScheduleControllerTest {

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
    void getSchedules_returnsCorrectDtos() throws Exception {

        var scheduleDto1 = new ScheduleTestDto(1l);
        var scheduleDto2 = new ScheduleTestDto(2l);
        var scheduleDto3 = new ScheduleTestDto(3l);
        var scheduleDto4 = new ScheduleTestDto(4l);
        var scheduleDto5 = new ScheduleTestDto(5l);


        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/schedule/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ScheduleTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(scheduleDto1, scheduleDto2, scheduleDto3, scheduleDto4, scheduleDto5);
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
    void createSchedule_shouldCreateSchedule() throws Exception {
        Schedule newSchedule = new Schedule();
        newSchedule.setName("Penktasis");
        newSchedule.setStatus("Valid");

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/schedule?groupId=5&startingDate=2023-05-06")
                                .content(asJsonString(newSchedule))
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
    void updateSchedule_shouldUpdateSchedule() throws Exception {
        Schedule newSchedules = new Schedule();
        newSchedules.setName("Penktasis");

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/schedule/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newSchedules)))
                .andReturn();


        assertEquals("Penktasis", newSchedules.getName());
    }

    @Test
    @Order(3)
    void getSchedule_shouldReturnCorrectSchedule() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/schedule/5")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ScheduleDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Penktasis");
    }
}



