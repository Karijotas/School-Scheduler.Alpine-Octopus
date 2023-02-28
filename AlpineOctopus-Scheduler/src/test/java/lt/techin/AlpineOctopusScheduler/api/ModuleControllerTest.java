//package lt.techin.AlpineOctopusScheduler.api;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lt.techin.AlpineOctopusScheduler.api.dto.ModuleDto;
//import lt.techin.AlpineOctopusScheduler.api.dto.ModuleEntityDto;
//import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
//import lt.techin.AlpineOctopusScheduler.model.Subject;
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
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper.toSubject;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
//    @Autowired
//    Subject subject;
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
//        //operacija_kokiusduomenis_expectedresult
//        Set<Subject> moduleSubjects1 = new HashSet<>();
//        Subject subject1 = new Subject(1l, "Operacines sistemos","labai idomi paskaitele",null,null, subject.subjectModules1 );
//        Subject subject2 = new Subject(2l,"Duomenu bazes","labai idomi paskaitele",null,null,subjectModules );
//
//        moduleSubjects1.add(subject1);
//        moduleSubjects1.add(subject2);
//
//        Set<Subject> moduleSubjects2 = new HashSet<>();
//        Subject subject3 = new Subject(3l, "Reactas","labai idomi paskaitele",null,null,subjectModules );
//        Subject subject4 = new Subject(4l,"Java","labai idomi paskaitele",null,null,subjectModules );
//
//        moduleSubjects2.add(subject3);
//        moduleSubjects2.add(subject4);
//
//        Set<Subject> moduleSubjects3 = new HashSet<>();
//        Subject subject5 = new Subject(1l, "Operacines sistemos","labai idomi paskaitele",null,null,subjectModules);
//        Subject subject6 = new Subject(3l, "Reactas","labai idomi paskaitele",null,null,subjectModules );
//
//        moduleSubjects3.add(subject5);
//        moduleSubjects3.add(subject6);
//
//        Set<Subject> moduleSubjects4 = new HashSet<>();
//        Subject subject7 = new Subject(2l,"Duomenu bazes","labai idomi paskaitele",null,null,subjectModules );
//        Subject subject8 = new Subject(4l,"Java","labai idomi paskaitele",null,null,subjectModules );
//
//        moduleSubjects4.add(subject7);
//        moduleSubjects4.add(subject8);
//
//
////        Set<Module> subjectModules1 = new HashSet<>();
////        Module module1 = new ModuleDto("Tinklapiai","tinklalapiu kurimas naudojant kelias programas",null,null);
////        Module module3 = new ModuleDto("Testavimas","rankinis ir automatinis",null,null);
//
//        var moduleDto1 = new ModuleEntityDto( "Tinklapiai","tinklalapiu kurimas naudojant kelias programas",null,null,moduleSubjects1, 1l);
//        var moduleDto2 = new ModuleEntityDto("Technologijos", "spring ir react pamokeles",null,null, moduleSubjects2, 2l);
//        var moduleDto3 = new ModuleEntityDto("Testavimas", "rankinis ir automatinis",null,null,moduleSubjects3, 3l);
//        var moduleDto4 = new ModuleEntityDto("Testavimas", "rankinis ir automatinis",null,null, moduleSubjects4, 4l);
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