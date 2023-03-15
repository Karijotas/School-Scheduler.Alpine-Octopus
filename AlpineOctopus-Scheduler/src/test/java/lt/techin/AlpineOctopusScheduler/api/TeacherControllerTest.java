package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherTestDto;
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

}
