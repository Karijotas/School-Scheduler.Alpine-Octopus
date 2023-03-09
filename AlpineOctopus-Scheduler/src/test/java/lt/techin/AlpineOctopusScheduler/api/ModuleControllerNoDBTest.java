//package lt.techin.AlpineOctopusScheduler.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
////@MockBeans({@MockBean(RoomController.class), @MockBean(RoomService.class), @MockBean(RoomRepository.class)})
//@AutoConfigureMockMvc
//public class ModuleControllerNoDBTest {
//
//    @MockBean
//    ModuleRepository moduleRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    void updateModule_callsDbMethod() throws Exception {
//        when(moduleRepository.deleteNonRegisteredModules()).thenReturn(3);
//
//        var mvcResult = mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .post("/api/v1/modules/registry/clear")
//                )
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Integer.class);
//
//        assertThat(mappedResponse)
//                .isEqualTo(3);
//    }
//
//}
