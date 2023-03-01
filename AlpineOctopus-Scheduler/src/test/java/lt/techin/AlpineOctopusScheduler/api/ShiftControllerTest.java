package lt.techin.AlpineOctopusScheduler.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ShiftEntityDto;
import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.service.ShiftService;

@SpringBootTest
@MockBeans({ @MockBean(ShiftController.class), @MockBean(ShiftService.class), @MockBean(ShiftRepository.class) })
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
		// operacija_kokiusduomenis_expectedresult

		var shiftDto1 = new ShiftEntityDto("Rytaz", 9, 13, LocalDateTime.now(), LocalDateTime.now(), false, 122l);
		var shiftDto2 = new ShiftEntityDto("Vakaraz", 14, 18, LocalDateTime.now(), LocalDateTime.now(), false, 125l);

		var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shifts").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		var mappedResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<ShiftEntityDto>>() {
				});

		assertThat(mappedResponse).containsExactlyInAnyOrder(shiftDto1, shiftDto2);

	}

}
