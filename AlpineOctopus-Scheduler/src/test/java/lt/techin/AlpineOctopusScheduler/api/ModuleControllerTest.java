package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleTestDto;
import lt.techin.AlpineOctopusScheduler.model.Module;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = AlpineOctopusSchedulerApplication.class)
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
    @Order(1)
    void getModules_returnsCorrectDtos() throws Exception {

//        var moduleDto1 = new ModuleEntityDto("Tinklapiai", "Informacijos išteklis saityne, kuris gali būti pasiektas naudojantis naršykle. Puslapius sujungiant, sudaroma interneto svetainė. Informacija dažniausiai pateikiama HTML[1] arba XHTML, nors šiuo metu tiesiogiai šiomis kalbomis jie rašomi labai retai. Paprastai naudojamos į teksto redaktorius panašios vartotojui draugiškos programos.", null, null, 1l);
//        var moduleDto2 = new ModuleEntityDto("Technologijos", "Informacinių technologijų modulio studijų dalykai parinkti atsižvelgiant į Informatikos bendrųjų programų projektą (IBPP), akcentuojant skaitmeninį raštingumą, informacinį mąstymą, siekiant ugdyti skaitmeninį intelektą.", null, null, 2l);
//        var moduleDto3 = new ModuleEntityDto("Kuno kultura", "Palakstykite vaikai.", null, null, 3l);
//        var moduleDto4 = new ModuleEntityDto("Kino kritika", "Individuali ar kolektyvinė kino kūrinių analizė. Kino kritikas yra savo srities profesionalas, apžvelgiantis kino filmus ir rašantis recenzijas, skirtas plačiajai visuomenei, paprastai laikraščiams, žurnalams ar specialiai auditorijai. Kino kritikai tiria, analizuoja nacionalinių ir tarptautinių kino filmų kūrimą; analizuoja ir vertina tam tikrus kino filmus, autorius, stilius, mokyklas, tendencijas; pateikia nuomones ir vertinimus žiniasklaidoje.", null, null, 4l);
//        var moduleDto5 = new ModuleEntityDto("Joga", "Indų filosofijos mokymas, apimantis įvairius fizinius ir mentalinius pratimus kaip asanas, pranajama, meditaciją arba askezę. Jogos teorinis pagrindas – Samkhja, o joga – praktinis išsivadavimo metodas. Kitaip nei samkhja, joga išpažįsta asmenišką aukščiausiąjį Dievą. Joga grindžiama idėja, kad koncentracija, meditacija bei askeze žmogus gali nuraminti dvasią, pasiekti gilesnę įžvalgą ir išsivaduoti iš materialiosios prakriti.", null, null, 5l);
//        var moduleDto6 = new ModuleEntityDto("Muzika", "Garsų menas, meniškų garsinių artefaktų kūrimas (t. y. garsų ir jų struktūrų improvizavimas, komponavimas, aranžavimas) ir tokių akustinių artefaktų atlikimo (dainavimas, grojimas, dirigavimas) ir jų sąmoningo ar pusiau sąmoningo klausymosi aktai.", null, null, 6l);
//        var moduleDto7 = new ModuleEntityDto("Architektura", "Pastatų projektavimo menas ir mokslas. Pagrindiniai architektūros tikslai yra formuoti aplinkos erdvę, projektuoti statinius bei jų kompleksus. Plačiąja šio žodžio reikšme architektūros objektas yra žmogaus gyvenamosios aplinkos planavimas ir organizavimas, pradedant miestų planavimu, landšafto architektūra ir baigiant interjero dizainu.", null, null, 7l);
//        var moduleDto8 = new ModuleEntityDto("Pedagogika", "Edukologijos dalis – kryptingo ir sistemingo augančiosios kartos ugdymo (auklėjimo) mokslas. Pedagogika yra istoriškai susiformavusi vaikų ir jaunimo rengimo sistema, tikslingai organizuota ir vykdoma ugdytojų ir ugdytinių konstruktyviu bendravimu atsižvelgiant į socialinius tikslus.", null, null, 8l);
//        var moduleDto9 = new ModuleEntityDto("Sunu dresura", "Į šuns auklėjimą turi būti įtraukti visi šeimos nariai.Kas beauklėtų šuniuką, visi privalo taikyti tas pačias taisykles. Šuns dresūra - žaidimas + poilsis + darbas.", null, null, 9l);
//        var moduleDto10 = new ModuleEntityDto("Kalbos", "Domina individualūs kursai, o gal norite suorganizuoti kalbų kursus savo įmonės darbuotojams? Skrivanek komanda mielai pasiūlys Jūsų poreikius atitinkančius mokymus ir padės pagerinti bendrinės bei specializuotos kalbos žinias. Nerandate Jums tinkančio pasiūlymo? Susisiekite su mumis ir padėsime rasti sprendimą Jūsų įmonei. Beje, mokytis galite tiek gyvai, tiek nuotoliniu būdu – tobulėkite iš bet kurio pasaulio krašto.", null, null, 10l);
//        var moduleDto11 = new ModuleEntityDto("Vadyba", "Empiriškai stebimas organizacijos valdymas bei specialioji (konkrečioji arba praktinė) disciplina, nagrinėjanti organizacijos valdymą ir kontrolę. Tarp tipinių vadybos užduočių yra: strateginis planavimas; ilgalaikių rėminių koncepcijų strateginėms veiklos sritims nustatymas. Veiksmingų sisteminių struktūrų sukūrimas, diegimas ir palaikymas, pvz., planavimo ir kontrolės sistemos, organizacinė struktūra.", null, null, 11l);
//        var moduleDto12 = new ModuleEntityDto("Istorija", "Mokslo šaka, tirianti ir interpretuojanti visuomenės raidos procesą, žmonijos praeitį. Pasakojimų apie savo praeitį turi ir primityvios, ir civilizuotos žmonių bendrijos. Rašytinės istorijos tradicija Vakarų civilizacijoje prasideda nuo Herodoto (5 a. pr. Kr.). Modernusis istorijos, kaip praeities tyrinėjimo, mokslas susiformavo 18 a. pabaigoje–19 a. pradžioje. Vėlesnių laikų istorikai siekia atkurti žmonių veiklos rezultatus.", null, null, 12l);
//        var moduleDto13 = new ModuleEntityDto("Geografija", "Graikai yra pirmoji žinoma kultūra, kuri aktyviai domėjosi geografija kaip mokslu ir filosofija, pagrindiniai veikėjai prisidėję prie geografijos plėtros buvo: Talis iš Mileto, Herodotas, Eratostenas, Hiparchas, Aristotelis, Strabonas ir Ptolemėjas. Romėnų sudarinėjami naujai atrastų šalių žemėlapiai papildė geografijos žinias naujais metodais.", null, null, 13l);
//        var moduleDto14 = new ModuleEntityDto("Fizika", "Gamtos mokslas, tiriantis visas materijos formas:[2] nuo submikroskopinių dalelių, iš kurių sudarytos visos įprastinės medžiagos (dalelių fizika), iki visos materialios Visatos elgesio (kosmologija).Kai kurios fizikos studijuojamos savybės yra bendros visoms materialioms sistemoms, pavyzdžiui, energijos tvermės dėsnis.", null, null, 14l);
//        var moduleDto15 = new ModuleEntityDto("Matematika", "Matematikai tyrinėja struktūras, kurios turi atitikmenis kituose tiksliuosiuose moksluose, pavyzdžiui, fizikoje, taip pat apibrėžia naujas struktūras. Pasinaudojant jomis galima rasti ryšius, bendrus analizės metodus tarp labai skirtingų mokslo sričių ir jų tyrimų objektų, palengvinti dažnai atliekamus skaičiavimus.", null, null, 15l);

//        List<ModuleDto> modulesList = new ArrayList<>();
//        modulesList.add(moduleDto1);
//        modulesList.add(moduleDto2);
//        modulesList.add(moduleDto3);
//        modulesList.add(moduleDto4);
//        modulesList.add(moduleDto5);
//        modulesList.add(moduleDto6);
//        modulesList.add(moduleDto7);
//        modulesList.add(moduleDto8);
//        modulesList.add(moduleDto9);
//        modulesList.add(moduleDto10);
//        modulesList.add(moduleDto11);
//        modulesList.add(moduleDto12);
//        modulesList.add(moduleDto13);
//        modulesList.add(moduleDto14);
//        modulesList.add(moduleDto15);

        var moduleDto1 = new ModuleTestDto(1l);
        var moduleDto2 = new ModuleTestDto(2l);
        var moduleDto3 = new ModuleTestDto(3l);
        var moduleDto4 = new ModuleTestDto(4l);
        var moduleDto5 = new ModuleTestDto(5l);
        var moduleDto6 = new ModuleTestDto(6l);
        var moduleDto7 = new ModuleTestDto(7l);
        var moduleDto8 = new ModuleTestDto(8l);
        var moduleDto9 = new ModuleTestDto(9l);
        var moduleDto10 = new ModuleTestDto(10l);
        var moduleDto11 = new ModuleTestDto(11l);
        var moduleDto12 = new ModuleTestDto(12l);
        var moduleDto13 = new ModuleTestDto(13l);
        var moduleDto14 = new ModuleTestDto(14l);
        var moduleDto15 = new ModuleTestDto(15l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/modules/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ModuleTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(moduleDto1, moduleDto2, moduleDto3, moduleDto4, moduleDto5, moduleDto6, moduleDto7, moduleDto8, moduleDto9, moduleDto10, moduleDto11, moduleDto12, moduleDto13, moduleDto14, moduleDto15);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    void createModule_shouldCreateNewModuleSuccesfully() throws Exception {
        Module newModule = new Module();
        newModule.setName("Moduliukas");
        newModule.setDescription("Moduliauskas");

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/modules")
                                .content(asJsonString(newModule))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @Order(3)
    void updateModule_shouldUpdateModule() throws Exception {
        Module newModule2 = new Module();
        newModule2.setName("Modulis");
        newModule2.setDescription("Moduliukas");


        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/modules/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newModule2)))
                .andReturn();


        assertEquals("Modulis", newModule2.getName());
        assertEquals("Moduliukas", newModule2.getDescription());
    }

    @Test
    @Order(2)
    void getModule_shouldReturnCorrectModule() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/modules/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ModuleDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Tinklapiai");
    }

    @Test
    @Order(5)
    void removeModule_shouldSetBooleanToTrue() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/modules/delete/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Module mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Module>() {
                });

        Assertions.assertTrue(mappedResponse.getDeleted());
    }

    @Test
    void restoreModule_shouldSetBooleanToFalse() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/modules/restore/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Module mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Module>() {
                });

        Assertions.assertFalse(mappedResponse.getDeleted());
    }

    @Test
    void getAllSubjectsById_shouldReturnCorrectSubjectCount() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/modules/1/subjects")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ModuleDto>>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(4, mappedResponse.size());

    }
}
