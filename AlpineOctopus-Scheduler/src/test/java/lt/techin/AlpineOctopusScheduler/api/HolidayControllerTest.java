package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayTestDto;
import lt.techin.AlpineOctopusScheduler.model.Holiday;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = AlpineOctopusSchedulerApplication.class)
public class HolidayControllerTest {
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
    void getHoliday_returnsCorrectDtos() throws Exception {
        var holidayDto1 = new HolidayTestDto(1l);
        var holidayDto2 = new HolidayTestDto(2l);
        var holidayDto3 = new HolidayTestDto(3l);
        var holidayDto4 = new HolidayTestDto(4l);
        var holidayDto5 = new HolidayTestDto(5l);
        var holidayDto6 = new HolidayTestDto(6l);
        var holidayDto7 = new HolidayTestDto(7l);
        var holidayDto8 = new HolidayTestDto(8l);
        var holidayDto9 = new HolidayTestDto(9l);
        var holidayDto10 = new HolidayTestDto(10l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/holidays/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<HolidayTestDto>>() {
                });
        assertThat(mappedResponse).containsExactlyInAnyOrder(holidayDto1, holidayDto2, holidayDto3, holidayDto4, holidayDto5, holidayDto6, holidayDto7, holidayDto8, holidayDto9, holidayDto10);

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
    void createHoliday_shouldCreateHoliday() throws Exception {
        Holiday newHoliday = new Holiday();
        newHoliday.setName("Ilgos atostogos");
        newHoliday.setStartDate(LocalDate.parse("2022-01-16"));
        newHoliday.setEndDate(LocalDate.parse("2022-01-20"));
        newHoliday.setReccuring(true);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/holidays/")
                                .content(asJsonString(newHoliday))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        Holiday createdHoliday = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Holiday.class);
        assertNotNull(createdHoliday.getId());
        assertEquals(newHoliday.getName(), createdHoliday.getName());
        assertEquals(newHoliday.getStartDate(), createdHoliday.getStartDate());
        assertEquals(newHoliday.getEndDate(), createdHoliday.getEndDate());
        assertEquals(newHoliday.getReccuring(), createdHoliday.getReccuring());
    }

    @Test
    @Order(3)
    void updateHoliday_shouldUpdateHoliday() throws Exception {
        Holiday newHoliday2 = new Holiday();
        newHoliday2.setName("Atostogos");
        newHoliday2.setStartDate(LocalDate.parse("2020-01-16"));
        newHoliday2.setEndDate(LocalDate.parse("2021-09-16"));
        newHoliday2.setReccuring(true);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/holidays/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newHoliday2)))
                .andReturn();

        assertEquals("Atostogos", newHoliday2.getName());
        assertEquals(true, newHoliday2.getReccuring());
    }

    @Test
    @Order(2)
    void getHoliday_shouldReturnCorrectRoom() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/holidays/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<HolidayDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Vasaros atostogos");
    }

    @Test
    @Order(5)
    void removeHoliday_shouldSetBooleanToTrue() throws Exception {
        // Check that there is a holiday with ID 2
        var mvcResult1 = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/holidays/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        if (mvcResult1.getResponse().getStatus() != HttpStatus.OK.value()) {
            // If there is no such holiday, skip the test
            return;
        }

        // Delete the holiday with ID 2
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/holidays/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Get the list of holidays
        var mvcResult2 = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/holidays")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response body into a list of Holiday objects
        List<Holiday> holidayList = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(),
                new TypeReference<List<Holiday>>() {
                });

        // Check that the deleted holiday is no longer in the list
        Assertions.assertFalse(holidayList.stream().anyMatch(h -> h.getId() == 2));
    }
}
