package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayTestDto;
import lt.techin.AlpineOctopusScheduler.model.Holiday;
import lt.techin.AlpineOctopusScheduler.service.HolidayService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Mock
    private HolidayService holidayService;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void getHoliday_returnsCorrectDtos() throws Exception {
        // Arrange
        List<HolidayTestDto> expectedHolidayDtos = Arrays.asList(
                new HolidayTestDto("New Year's Day", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1), false),
                new HolidayTestDto("Christmas Day", LocalDate.of(2022, 12, 25), LocalDate.of(2022, 12, 25), false),
                new HolidayTestDto("Easter Sunday", LocalDate.of(2022, 4, 17), LocalDate.of(2022, 4, 17), true)
        );
        Mockito.when(holidayService.getAllHolidays()).thenReturn(expectedHolidayDtos);

        // Act
        MvcResult result = mockMvc.perform(get("/api/v1/holidays/all"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String jsonResponse = result.getResponse().getContentAsString();
        List<HolidayTestDto> actualHolidayDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<HolidayTestDto>>() {
        });
        assertEquals(expectedHolidayDtos.size(), actualHolidayDtos.size());
        for (int i = 0; i < expectedHolidayDtos.size(); i++) {
            assertEquals(expectedHolidayDtos.get(i).getName(), actualHolidayDtos.get(i).getName());
            assertEquals(expectedHolidayDtos.get(i).getStartDate(), actualHolidayDtos.get(i).getStartDate());
            assertEquals(expectedHolidayDtos.get(i).getEndDate(), actualHolidayDtos.get(i).getEndDate());
            assertEquals(expectedHolidayDtos.get(i).getReccuring(), actualHolidayDtos.get(i).getReccuring());
        }
        if (!expectedHolidayDtos.equals(actualHolidayDtos)) {
            System.out.println("Actual list contains:");
            for (HolidayTestDto actualHolidayDto : actualHolidayDtos) {
                System.out.println(actualHolidayDto);
            }
        }

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

        HolidayEntityDto holidayDto = new HolidayEntityDto();
        holidayDto.setName("New Year's Day");
        holidayDto.setStartDate(LocalDate.of(2024, 1, 1));
        holidayDto.setEndDate(LocalDate.of(2024, 1, 1));
        holidayDto.setReccuring(false);

        HolidayService holidayService = Mockito.mock(HolidayService.class);
        Holiday holiday = new Holiday();
        holiday.setId(1L);
        holiday.setName(holidayDto.getName());
        holiday.setStartDate(holidayDto.getStartDate());
        holiday.setEndDate(holidayDto.getEndDate());
        holiday.setReccuring(holidayDto.getReccuring());
        Mockito.when(holidayService.classIsUnique(Mockito.any(Holiday.class))).thenReturn(true);
        Mockito.when(holidayService.create(Mockito.any(Holiday.class))).thenReturn(holiday);


        HolidayController controller = new HolidayController(holidayService);


        ResponseEntity<HolidayEntityDto> response = controller.createHoliday(holidayDto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(holiday.getName(), response.getBody().getName());
        assertEquals(holiday.getStartDate(), response.getBody().getStartDate());
        assertEquals(holiday.getEndDate(), response.getBody().getEndDate());
        assertEquals(holiday.getReccuring(), response.getBody().getReccuring());
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
                        get("/api/v1/holidays/1")
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

        var mvcResult1 = mockMvc.perform(
                        get("/api/v1/holidays/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        if (mvcResult1.getResponse().getStatus() != HttpStatus.OK.value()) {
            return;
        }
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/holidays/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        var mvcResult2 = mockMvc.perform(
                        get("/api/v1/holidays")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Holiday> holidayList = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(),
                new TypeReference<List<Holiday>>() {
                });
        Assertions.assertFalse(holidayList.stream().anyMatch(h -> h.getId() == 2));
    }
}
