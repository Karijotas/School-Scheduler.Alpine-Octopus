package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftTestDto;
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
public class ShiftControllerTest {

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
    void getShifts_returnsCorrectDtos() throws Exception {

        var shiftDto1 = new ShiftTestDto(1l);
        var shiftDto2 = new ShiftTestDto(2l);
        var shiftDto3 = new ShiftTestDto(3l);
        var shiftDto4 = new ShiftTestDto(4l);

        var mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/shifts/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ShiftTestDto>>() {
                });

        assertThat(mappedResponse)
                .containsExactlyInAnyOrder(shiftDto1, shiftDto2, shiftDto3, shiftDto4);
    }
}
