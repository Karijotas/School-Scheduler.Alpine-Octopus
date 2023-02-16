//package lt.techin.AlpineOctopusScheduler.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
//import com.fasterxml.jackson.core.type.TypeReference;
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
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class SubjectControllerTest {
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup(){
//
//    }
//    @AfterEach
//    void tearDown(){
//
//    }
////    @Test
////    void getRooms_returnCorrectDtos() throws Exception {
////        var roomDto1= new RoomEntityDto(1l,"jp-01","lakunu g.3","java programavimas rytas");
////        var roomDto2= new RoomEntityDto(2l,"jp-02","lakunu g.2","java programavimas vakaras");
////
////        var mvcResult = mockMvc.perform(
////                        MockMvcRequestBuilders
////                                .get("/rooms")
////                                .accept(MediaType.APPLICATION_JSON)
////                )
////                .andExpect(status().isOk())
////                .andReturn();
////
////        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
////                new TypeReference<List<RoomEntityDto>>() {
////                });
////
////        assertThat(mappedResponse)
////                .containsExactlyInAnyOrder(subjectDto1, subjectDto2, subjectDto3, subjectDto4);
//    }
//}
