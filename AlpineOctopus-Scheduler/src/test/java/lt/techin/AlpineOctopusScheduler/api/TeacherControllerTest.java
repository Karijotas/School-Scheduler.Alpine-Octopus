package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherTestDto;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
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
public class TeacherControllerTest {

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
    void getTeachers_returnsCorrectDtos() throws Exception {

        var teacherDto1 = new TeacherTestDto(1l);
        var teacherDto2 = new TeacherTestDto(2l);
        var teacherDto3 = new TeacherTestDto(3l);
        var teacherDto4 = new TeacherTestDto(4l);
        var teacherDto5 = new TeacherTestDto(5l);
        var teacherDto6 = new TeacherTestDto(6l);
        var teacherDto7 = new TeacherTestDto(7l);
        var teacherDto8 = new TeacherTestDto(8l);
        var teacherDto9 = new TeacherTestDto(9l);
        var teacherDto10 = new TeacherTestDto(10l);
        var teacherDto11 = new TeacherTestDto(11l);
        var teacherDto12 = new TeacherTestDto(12l);
        var teacherDto13 = new TeacherTestDto(13l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/teachers/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<TeacherTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(teacherDto1, teacherDto2, teacherDto3, teacherDto4, teacherDto5, teacherDto6, teacherDto7, teacherDto8, teacherDto9, teacherDto10, teacherDto11, teacherDto12, teacherDto13);
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
    void createTeacher_shouldCreateTeacher() throws Exception {
        Teacher newTeacher = new Teacher();
        newTeacher.setName("teacheris 2");
        newTeacher.setLoginEmail("pastas@gmail.com");

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/teachers")
                                .content(asJsonString(newTeacher))
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
    void updateTeacher_shouldUpdateTeacher() throws Exception {
        Teacher newTeacher2 = new Teacher();
        newTeacher2.setName("Mokytojelis");
        newTeacher2.setLoginEmail("Laiskas@gmail.com");


        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/teachers/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newTeacher2)))
                .andReturn();


        assertEquals("Mokytojelis", newTeacher2.getName());
        assertEquals("Laiskas@gmail.com", newTeacher2.getLoginEmail());
    }

    @Test
    @Order(3)
    void getTeacher_shouldReturnCorrectTeacher() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/teachers/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<TeacherDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Mantvydas Mantukas");
    }

    @Test
    @Order(5)
    void removeTeacher_shouldSetBooleanToTrue() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/teachers/delete/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Teacher mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Teacher>() {
                });

        Assertions.assertTrue(mappedResponse.getDeleted());
    }

    @Test
    void restoreTeacher_shouldSetBooleanToFalse() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/teachers/restore/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Teacher mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Teacher>() {
                });

        Assertions.assertFalse(mappedResponse.getDeleted());
    }

    @Test
    void getAllSubjectsById_shouldReturnCorrectSubjectCount() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/teachers/1/subjects")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<TeacherDto>>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(2, mappedResponse.size());

    }


    @Test
    void getAllShiftsById_shouldReturnCorrectShiftCount() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/teachers/1/shifts")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<TeacherDto>>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(2, mappedResponse.size());

    }
}
