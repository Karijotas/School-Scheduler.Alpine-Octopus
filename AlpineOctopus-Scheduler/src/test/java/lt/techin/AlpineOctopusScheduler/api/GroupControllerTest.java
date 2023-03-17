package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.GroupsTestDto;
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


}
