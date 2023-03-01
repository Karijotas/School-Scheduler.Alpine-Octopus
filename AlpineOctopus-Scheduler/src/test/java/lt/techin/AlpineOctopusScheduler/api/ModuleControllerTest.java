//package lt.techin.AlpineOctopusScheduler.api;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lt.techin.AlpineOctopusScheduler.api.dto.ModuleEntityDto;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
////@MockBeans({@MockBean(RoomController.class), @MockBean(RoomService.class), @MockBean(RoomRepository.class)})
//@AutoConfigureMockMvc
//public class ModuleControllerTest {
//
//    //@MockBean - tuos kuriuos norime izoliuoti nuo sio testo
//
////    @MockBean
////    SubjectRepository subjectRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//
//    @Test
//    void getModules_returnsCorrectDtos() throws Exception {
//
//        var moduleDto1 = new ModuleEntityDto("Tinklapiai", "tinklalapiu kurimas naudojant kelias programas", null, null, 1l, true);
//        var moduleDto2 = new ModuleEntityDto("Technologijos", "spring ir react pamokeles", null, null, 2l, false);
//        var moduleDto3 = new ModuleEntityDto("Kuno kultura", "palakstykite vaikai", null, null, 3l, false);
//        var moduleDto4 = new ModuleEntityDto("Kino kritika", "hmmmm", null, null, 4l, false);
//        var moduleDto5 = new ModuleEntityDto("Joga", "ommmmm", null, null, 5l, false);
//        var moduleDto6 = new ModuleEntityDto("Muzika", "lalalaaaaa", null, null, 6l, false);
//        var moduleDto7 = new ModuleEntityDto("Architektura", "ojojojoj", null, null, 7l, false);
//        var moduleDto8 = new ModuleEntityDto("Pedagogika", "cha cha cha", null, null, 8l, false);
//        var moduleDto9 = new ModuleEntityDto("Sunu dresura", "o jo", null, null, 9l, false);
//        var moduleDto10 = new ModuleEntityDto("Kalbos", "Laba diena", null, null, 9l, false);
//        var moduleDto11 = new ModuleEntityDto("Vadyba", "hmmmm", null, null, 9l, false);
//        var moduleDto12 = new ModuleEntityDto("Istorija", "ilgai ir laimingai", null, null, 9l, false);
//        var moduleDto13 = new ModuleEntityDto("Geografija", "aplink pasauli", null, null, 9l, true);
//        var moduleDto14 = new ModuleEntityDto("Fizika", "desniai ir panasiai", null, null, 9l, true);
//        var moduleDto15 = new ModuleEntityDto("Matematika", "aukstoji ir zemoji", null, null, 9l, true);
//
//
//        var mvcResult = mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .get("/api/v1/modules")
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
//                new TypeReference<List<ModuleEntityDto>>() {
//                });
//
//        assertThat(mappedResponse)
//                .containsExactlyInAnyOrder(moduleDto1, moduleDto2, moduleDto3, moduleDto4);
//    }
//
//
//}