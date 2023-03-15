package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramTestDto;
import lt.techin.AlpineOctopusScheduler.model.Program;
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
class ProgramControllerTest {

    //    @MockBean
//    ProgramRepository programRepository;
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
    void getPrograms_returnsCorrectDtos() throws Exception {
        //operacija_kokiusduomenis_expectedresult


        var programDto1 = new ProgramTestDto(1L);
        var programDto2 = new ProgramTestDto(2L);
        var programDto3 = new ProgramTestDto(3L);
        var programDto4 = new ProgramTestDto(4l);
        var programDto5 = new ProgramTestDto(5l);
        var programDto6 = new ProgramTestDto(6l);
        var programDto7 = new ProgramTestDto(7l);
        var programDto8 = new ProgramTestDto(8l);
        var programDto9 = new ProgramTestDto(9l);
        var programDto10 = new ProgramTestDto(10l);
        var programDto11 = new ProgramTestDto(11l);
        var programDto12 = new ProgramTestDto(12l);
        var programDto13 = new ProgramTestDto(13l);
        var programDto14 = new ProgramTestDto(14l);


        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/programs/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ProgramTestDto>>() {
        });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(programDto1, programDto2, programDto3, programDto4, programDto5, programDto6,
                        programDto7, programDto8, programDto9, programDto10, programDto11, programDto12, programDto13, programDto14);

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
    void createProgram_shouldCreateProgram() throws Exception {
        Program newProgram = new Program();
        newProgram.setName("Reikalingieji amatai");
        newProgram.setDescription("Labai reikalingų specialybių programa");

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/programs")
                                .content(asJsonString(newProgram))
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
    void updateProgram_shouldUpdateProgram() throws Exception {
        Program newProgram2 = new Program();
        newProgram2.setName("Programele");
        newProgram2.setDescription("Super sudetinga");


        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/programs/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newProgram2)))
                .andReturn();


        assertEquals("Programele", newProgram2.getName());
        assertEquals("Super sudetinga", newProgram2.getDescription());
    }

    @Test
    @Order(3)
    void getProgram_shouldReturnCorrectProgram() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/programs/11")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ProgramDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Normali");
    }

    @Test
    @Order(5)
    void removeProgram_shouldSetBooleanToTrue() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/programs/delete/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Program mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Program>() {
                });

        Assertions.assertTrue(mappedResponse.getDeleted());
    }

    @Test
    void restoreProgram_shouldSetBooleanToFalse() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/programs/restore/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Program mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Program>() {
                });

        Assertions.assertFalse(mappedResponse.getDeleted());
    }
}


