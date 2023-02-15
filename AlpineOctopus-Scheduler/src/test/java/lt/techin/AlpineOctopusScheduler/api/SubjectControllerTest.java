package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@MockBeans({@MockBean(RoomController.class), @MockBean(RoomService.class), @MockBean(RoomRepository.class)})
@AutoConfigureMockMvc
class SubjectControllerTest {

    //@MockBean - tuos kuriuos norime izoliuoti nuo sio testo

//    @MockBean
//    SubjectRepository subjectRepository;

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
    void getSubjects_returnsCorrectDtos() throws Exception {
        //operacija_kokiusduomenis_expectedresult

        var subjectDto1 = new SubjectEntityDto(1l, "Operacines sistemos", "labai idomi paskaitele" ,null,null);
        var subjectDto2 = new SubjectEntityDto(2l, "Duomenu bazes", "labai idomi paskaitele" ,null,null);
        var subjectDto3 = new SubjectEntityDto(3l, "Reactas", "labai idomi paskaitele" ,null,null);
        var subjectDto4 = new SubjectEntityDto(4l, "Java", "labai idomi paskaitele" ,null,null);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/subjects")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                    new TypeReference<List<SubjectEntityDto>>() {
        });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(subjectDto1, subjectDto2, subjectDto3, subjectDto4);
    }

//    @Test
//    void findMarkedSubjects() {
//    }
//
//    @Test
//    void getSubject() {
//    }
//
//    @Test
//    void deleteSubject() {
//    }
//
//    @Test
//    void createSubject() {
//    }
//
//    @Test
//    void updateSubject() {
//    }


}