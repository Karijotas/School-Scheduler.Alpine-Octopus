package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.AlpineOctopusSchedulerApplication;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsDto;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsTestDto;
import lt.techin.AlpineOctopusScheduler.model.Groups;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = AlpineOctopusSchedulerApplication.class)
public class GroupControllerTest {


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
    void getGroups_returnsCorrectDtos() throws Exception {

        var groupDto1 = new GroupsTestDto(1l);
        var groupDto2 = new GroupsTestDto(2l);
        var groupDto3 = new GroupsTestDto(3l);
        var groupDto4 = new GroupsTestDto(4l);
        var groupDto5 = new GroupsTestDto(5l);
        var groupDto6 = new GroupsTestDto(6l);
        var groupDto7 = new GroupsTestDto(7l);
        var groupDto8 = new GroupsTestDto(8l);
        var groupDto9 = new GroupsTestDto(9l);
        var groupDto10 = new GroupsTestDto(10l);
        var groupDto11 = new GroupsTestDto(11l);
        var groupDto12 = new GroupsTestDto(12l);
        var groupDto13 = new GroupsTestDto(13l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/groups/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<GroupsTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(groupDto1, groupDto2, groupDto3, groupDto4, groupDto5, groupDto6, groupDto7, groupDto8, groupDto9, groupDto10, groupDto11, groupDto12, groupDto13);
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
    void createGroup_shouldCreateGroup() throws Exception {
        Groups newGroup = new Groups();
        newGroup.setName("Pirma grupee");
        newGroup.setSchoolYear(2024);
        newGroup.setStudentAmount(36);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/groups?programId=3&shiftId=2")
                                .content(asJsonString(newGroup))
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
    void updateGroup_shouldUpdateGroup() throws Exception {
        GroupsDto newGroup2 = new GroupsDto();
        newGroup2.setName("Grupele");
        newGroup2.setSchoolYear(2022);
        newGroup2.setStudentAmount(55);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/groups/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newGroup2)))
                .andReturn();


        assertEquals("Grupele", newGroup2.getName());
        assertEquals(2022, newGroup2.getSchoolYear());
    }

    @Test
    @Order(2)
    void getGroup_shouldReturnCorrectGroup() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/groups/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<GroupsDto>() {
                });

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(mappedResponse.getName(), "Akademija.it JP 22/1");
    }

    @Test
    @Order(5)
    void removeGroup_shouldSetBooleanToTrue() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/groups/delete/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Groups mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Groups>() {
                });

        Assertions.assertTrue(mappedResponse.getDeleted());
    }

    @Test
    void restoreGroup_shouldSetBooleanToFalse() throws Exception {
        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/groups/restore/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Groups mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Groups>() {
                });

        Assertions.assertFalse(mappedResponse.getDeleted());
    }
}
