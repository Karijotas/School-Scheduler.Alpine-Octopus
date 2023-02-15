package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleEntityDto;
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
//@MockBeans({@MockBean(RoomController.class), @MockBean(RoomService.class), @MockBean(RoomRepository.class)})
@AutoConfigureMockMvc
public class ModuleControllerTest {

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
    void getModules_returnsCorrectDtos() throws Exception {
        //operacija_kokiusduomenis_expectedresult

        var moduleDto1 = new ModuleEntityDto(1l, "Tinklapiai","tinklalapiu kurimas naudojant kelias programas",null,null);
        var moduleDto2 = new ModuleEntityDto(2l, "Technologijos", "spring ir react pamokeles",null,null);
        var moduleDto3 = new ModuleEntityDto(3l, "Testavimas", "rankinis ir automatinis",null,null);
        var moduleDto4 = new ModuleEntityDto(4l, "Testavimas", "rankinis ir automatinis",null,null);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/modules")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ModuleEntityDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(moduleDto1, moduleDto2, moduleDto3, moduleDto4);
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