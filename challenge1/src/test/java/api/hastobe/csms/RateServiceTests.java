package api.hastobe.csms;

import api.hastobe.csms.service.dto.CdrDTO;
import api.hastobe.csms.service.dto.RateDTO;
import api.hastobe.csms.service.dto.RateInputDTO;
import api.hastobe.csms.service.dto.RateOutputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CsmsApplication.class)
class RateServiceTests {
    Logger logger = LoggerFactory.getLogger(RateServiceTests.class);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void rateTest() throws Exception {
        RateDTO rateDTO = new RateDTO();
        rateDTO.setEnergy(0.3f);
        rateDTO.setTime(2);
        rateDTO.setTransaction(1);

        CdrDTO cdrDTO = new CdrDTO();
        cdrDTO.setMeterStart(1204307L);
        cdrDTO.setMeterStop(1215230L);
        cdrDTO.setTimestampStart("2021-04-05T10:04:00Z");
        cdrDTO.setTimestampStop("2021-04-05T11:27:00Z");

        RateInputDTO rateInputDTO = new RateInputDTO();
        rateInputDTO.setRate(rateDTO);
        rateInputDTO.setCdr(cdrDTO);

        String content = objectMapper.writeValueAsString(rateInputDTO);
        ResultActions resultActions = mockMvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        RateOutputDTO rateOutputDTO = objectMapper.readValue(result.getResponse().getContentAsString(), RateOutputDTO.class);
        assert(rateOutputDTO.getTotal() == 7.04f);
        assert(rateOutputDTO.getComponent().getEnergy() == 3.277f);
        assert(rateOutputDTO.getComponent().getTime() == 2.767f);
        assert(rateOutputDTO.getComponent().getTransaction() == 1);
    }

}
