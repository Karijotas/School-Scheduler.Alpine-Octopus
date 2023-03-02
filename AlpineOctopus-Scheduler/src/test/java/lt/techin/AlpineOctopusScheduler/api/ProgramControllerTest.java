package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramTestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@MockBeans({@MockBean(SubjectController.class), @MockBean(SubjectService.class), @MockBean(SubjectRepository.class)})
@AutoConfigureMockMvc
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

//    } );
//}
    }
}


