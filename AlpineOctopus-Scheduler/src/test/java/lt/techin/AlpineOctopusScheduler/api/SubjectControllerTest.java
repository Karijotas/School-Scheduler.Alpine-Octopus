//package lt.techin.AlpineOctopusScheduler.api;
//

//import com.fasterxml.jackson.databind.ObjectMapper;
//import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
//import com.fasterxml.jackson.core.type.TypeReference;

//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;

//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
////@MockBeans({@MockBean(RoomController.class), @MockBean(RoomService.class), @MockBean(RoomRepository.class)})
//@AutoConfigureMockMvc
//class SubjectControllerTest {
//
//    //@MockBean - tuos kuriuos norime izoliuoti nuo sio testo
//
////    @MockBean
////    SubjectRepository subjectRepository;
//
//    public void setup() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//
//    @Test
//    void getSubjects_returnsCorrectDtos() throws Exception {
//        //operacija_kokiusduomenis_expectedresult
//
//        Set<Module> subjectModules1 = new HashSet<>();
//        Module module1 = new Module("Tinklapiai","tinklalapiu kurimas naudojant kelias programas",null,null, moduleSubjects);
//        Module module2 = new Module("Technologijos","spring ir react pamokeles",null,null,moduleSubjects);
//
//        subjectModules1.add(module1);
//        subjectModules1.add(module2);
//
//
//        Set<Module> subjectModules2 = new HashSet<>();
//        Module module3 = new Module("Testavimas","rankinis ir automatinis",null,null, moduleSubjects);
//        Module module4 = new Module("Testavimas","rankinis ir automatinis",null,null, moduleSubjects);
//
//        subjectModules2.add(module3);
//        subjectModules2.add(module4);
//
//        Set<Module> subjectModules3 = new HashSet<>();
//        Module module5 = new Module("Tinklapiai","tinklalapiu kurimas naudojant kelias programas",null,null, moduleSubjects);
//        Module module6 = new Module("Testavimas","rankinis ir automatinis",null,null, moduleSubjects);
//
//        subjectModules3.add(module5);
//        subjectModules3.add(module6);
//
//        Set<Module> subjectModules4 = new HashSet<>();
//        Module module7 = new Module("Technologijos","spring ir react pamokeles",null,null,moduleSubjects);
//        Module module8 = new Module("Testavimas","rankinis ir automatinis",null,null, moduleSubjects);
//
//        subjectModules4.add(module7);
//        subjectModules4.add(module8);
//
////        INTO
////                modules_subjects
////        (module_id, subject_id)
////
////        (CAST(1 AS BIGINT), CAST(1 AS BIGINT)),
////        (CAST(1 AS BIGINT), CAST(2 AS BIGINT)),
////        (CAST(2 AS BIGINT), CAST(3 AS BIGINT)),
////        (CAST(2 AS BIGINT), CAST(4 AS BIGINT)),
////        (CAST(3 AS BIGINT), CAST(1 AS BIGINT)),
////        (CAST(3 AS BIGINT), CAST(3 AS BIGINT)),
////        (CAST(4 AS BIGINT), CAST(2 AS BIGINT)),
////        (CAST(4 AS BIGINT), CAST(4 AS BIGINT));
//
////       1 ("Tinklapiai","tinklalapiu kurimas naudojant kelias programas",null,null),
////       2 ("Technologijos","spring ir react pamokeles",null,null),
////       3 ("Testavimas","rankinis ir automatinis",null,null),
////       4 ("Testavimas","rankinis ir automatinis",null,null);
//
//
//
//
//        var subjectDto1 = new SubjectEntityDto(1l, "Operacines sistemos", "labai idomi paskaitele" ,null,null);
//        var subjectDto2 = new SubjectEntityDto(2l, "Duomenu bazes", "labai idomi paskaitele" ,null,null);
//        var subjectDto3 = new SubjectEntityDto(3l, "Reactas", "labai idomi paskaitele" ,null,null);
//        var subjectDto4 = new SubjectEntityDto(4l, "Java", "labai idomi paskaitele" ,null,null);
//
//        var mvcResult = mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .get("/api/v1/subjects")
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
//                                                    new TypeReference<List<SubjectEntityDto>>() {
//        });
//
//        assertThat(mappedResponse)
//                .containsExactlyInAnyOrder(subjectDto1, subjectDto2, subjectDto3, subjectDto4);
//    }
//
////    @Test
////    void findMarkedSubjects() {
////    }
////
////    @Test
////    void getSubject() {
////    }
////
////    @Test
////    void deleteSubject() {
////    }
////
////    @Test
////    void createSubject() {
////    }
////
////    @Test
////    void updateSubject() {
////    }
//
//
//}
