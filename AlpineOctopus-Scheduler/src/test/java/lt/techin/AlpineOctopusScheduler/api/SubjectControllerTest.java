package lt.techin.AlpineOctopusScheduler.api;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectTestDto;
import lt.techin.AlpineOctopusScheduler.model.Subject;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = AlpineOctopusSchedulerApplication.class)
class SubjectControllerTest {

    public void setup() {
    }

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
    }


    @Test
    @Order(1)
    void getSubjects_returnsCorrectDtos() throws Exception {

        var subjectDto1 = new SubjectTestDto(1l);
        var subjectDto2 = new SubjectTestDto(2l);
        var subjectDto3 = new SubjectTestDto(3l);
        var subjectDto4 = new SubjectTestDto(4l);
        var subjectDto5 = new SubjectTestDto(5l);
        var subjectDto6 = new SubjectTestDto(6l);
        var subjectDto7 = new SubjectTestDto(7l);
        var subjectDto8 = new SubjectTestDto(8l);
        var subjectDto9 = new SubjectTestDto(9l);
        var subjectDto10 = new SubjectTestDto(10l);
        var subjectDto11 = new SubjectTestDto(11l);
        var subjectDto12 = new SubjectTestDto(12l);
        var subjectDto13 = new SubjectTestDto(13l);
        var subjectDto14 = new SubjectTestDto(14l);
        var subjectDto15 = new SubjectTestDto(15l);
        var subjectDto16 = new SubjectTestDto(16l);
        var subjectDto17 = new SubjectTestDto(17l);
        var subjectDto18 = new SubjectTestDto(18l);
        var subjectDto19 = new SubjectTestDto(19l);
        var subjectDto20 = new SubjectTestDto(20l);
        var subjectDto21 = new SubjectTestDto(21l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/subjects/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<SubjectTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(subjectDto1, subjectDto2, subjectDto3, subjectDto4, subjectDto5, subjectDto6, subjectDto7, subjectDto8, subjectDto9, subjectDto10, subjectDto11, subjectDto12, subjectDto13, subjectDto14, subjectDto15, subjectDto16, subjectDto17, subjectDto18, subjectDto19, subjectDto20, subjectDto21);
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void createSubject_shouldCreateSubject() throws Exception {
        Subject newSubject = new Subject();
        newSubject.setName("Subjectas 1");
        newSubject.setDescription("Aprasymas subjecto");

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/subjects")
                                .content(asJsonString(newSubject))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void updateSubject_shouldUpdateSubject() throws Exception {
        Subject newSubject2 = new Subject();
        newSubject2.setName("Dalykelis");
        newSubject2.setDescription("Labai įdomus");


        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/subjects/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newSubject2)))
                .andReturn();


        assertEquals("Dalykelis", newSubject2.getName());
        assertEquals("Labai įdomus", newSubject2.getDescription());
    }

    @Test
    void getSubject_shouldReturnCorrectSubject() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/subjects/3")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<SubjectDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Reactas");
    }
}
