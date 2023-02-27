package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import lt.techin.AlpineOctopusScheduler.service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static lt.techin.AlpineOctopusScheduler.stubs.GroupsCreator.createGroups;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GroupsController.class)
public class GroupsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GroupService groupService;
    @Autowired
    @MockBean
    private GroupsRepository groupsRepository;
    @Autowired
    @MockBean
    private ProgramRepository programRepository;

    @Test
    void whenGet_thenReturns200() throws Exception {
        mockMvc.perform(get("/api/v1/groups")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenNotValidCreate_thenReturns400() throws Exception {
        Groups groups = new Groups();
        mockMvc.perform(post("/api/v1/groups")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(groups)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenNotValidReplace_thenReturns400() throws Exception {
        Groups groups = createGroups(1l);
        mockMvc.perform(put("/api/v1/groups/{groupId}", 1l)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(groups)))
                .andExpect(status().isBadRequest());
    }
}
