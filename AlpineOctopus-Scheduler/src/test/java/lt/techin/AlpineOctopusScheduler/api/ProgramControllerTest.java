package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.ProgramEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper.toProgram;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper.toSubject;
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
//
//    @Test
//    void getPrograms_returnsCorrectDtos() throws Exception {
//        //operacija_kokiusduomenis_expectedresult
//        Set<ProgramSubjectHours> hours1 = new HashSet<>();
//        Set<ProgramSubjectHours> hours2 = new HashSet<>();
//        Set<ProgramSubjectHours> hours3 = new HashSet<>();
//        Set<ProgramSubjectHours> hours4 = new HashSet<>();
//        Set<ProgramSubjectHours> hours5 = new HashSet<>();
//        Set<ProgramSubjectHours> hours6 = new HashSet<>();
//
//        Set<ProgramSubjectHours> hours7 = new HashSet<>();
//        Set<ProgramSubjectHours> hours8 = new HashSet<>();
//        Set<ProgramSubjectHours> hours9 = new HashSet<>();
//        Set<ProgramSubjectHours> hours10 = new HashSet<>();
//
//
////        var subjectDto1 = new SubjectEntityDto(1l,"Matematika","skaiciukai", null, null);
////        var subjectDto2 = new SubjectEntityDto(2l,"FIlosofija","raideles", null, null);
////        var subjectDto3 = new SubjectEntityDto(3l,"Istorija","pasakos", null, null);
////        var subjectDto4 = new SubjectEntityDto(4l,"Javascriptas", "kodukai", null, null);
//
////        var programDto1 = new ProgramEntityDto(1l, "Belekokio gerumo","apie viska" , null, null);
////        var programDto2 = new ProgramEntityDto(2l, "Sita kieta is vis", "biski to, biski ano",  null, null);
////        var programDto3 = new ProgramEntityDto(3l, "Normali", "normalus dalykai", null, null);
////        var programDto4 = new ProgramEntityDto(4l, "Labai sunki", "aukstoji matematika ir raketu mokslas",  null, null);
////        var programDto5 = new ProgramEntityDto(5l, "Nuobodoka", "apie kaminus",  null, null);
////        var programDto6 = new ProgramEntityDto(6l, "Pirmunams", "programavimo visokie dalykeliai",  null, null);
//
////        var programSubjectHours1 = new ProgramSubjectHours(1l , toProgram(programDto1) , toSubject(subjectDto2), 20);
////        var programSubjectHours2 = new ProgramSubjectHours(2l , toProgram(programDto1), toSubject(subjectDto1),24);
////        var programSubjectHours3 = new ProgramSubjectHours(3l , toProgram(programDto2), toSubject(subjectDto4), 40);
////        var programSubjectHours4 = new ProgramSubjectHours(4l , toProgram(programDto2), toSubject(subjectDto3), 10);
////        var programSubjectHours5 = new ProgramSubjectHours(5l , toProgram(programDto3), toSubject(subjectDto1), 8);
////        var programSubjectHours6 = new ProgramSubjectHours(6l , toProgram(programDto4), toSubject(subjectDto2), 44);
////        var programSubjectHours7 = new ProgramSubjectHours(7l , toProgram(programDto5), toSubject(subjectDto2), 2);
////        var programSubjectHours8 = new ProgramSubjectHours(8l , toProgram(programDto5), toSubject(subjectDto2), 18);
//
//
//
//        var mvcResult = mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .get("/api/v1/programs")
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ProgramEntityDto>>() {
//        });
//
//        assertThat(mappedResponse)
//                .containsExactlyInAnyOrder(programDto1, programDto2, programDto3, programDto4, programDto5, programDto6);

//        verify(animalRepository, times(1)).deleteNonRegisteredAnimals();
//        assertThat(mappedResponse)
//                .isEqualTo(expectedDtos);
    }



