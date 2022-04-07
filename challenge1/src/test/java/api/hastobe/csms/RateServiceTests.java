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


    @Test
    public void rateTest1() throws Exception {
        RateDTO rateDTO = new RateDTO();
        rateDTO.setEnergy(0);
        rateDTO.setTime(0);
        rateDTO.setTransaction(0);

        CdrDTO cdrDTO = new CdrDTO();
        cdrDTO.setMeterStart(0L);
        cdrDTO.setMeterStop(0L);
        cdrDTO.setTimestampStart(null);
        cdrDTO.setTimestampStop(null);

        RateInputDTO rateInputDTO = new RateInputDTO();
        rateInputDTO.setRate(rateDTO);
        rateInputDTO.setCdr(cdrDTO);

        String content = objectMapper.writeValueAsString(rateInputDTO);
        ResultActions resultActions = mockMvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isBadRequest());

    }


    @Test
    public void rateTest2() throws Exception {
        // if meter start is more than or equal to meter stop
        RateDTO rateDTO = new RateDTO();
        rateDTO.setEnergy(0.3f);
        rateDTO.setTime(2);
        rateDTO.setTransaction(1);

        CdrDTO cdrDTO = new CdrDTO();
        cdrDTO.setMeterStart(1215230L);
        cdrDTO.setMeterStop(1215230L);
        cdrDTO.setTimestampStart("2021-04-05T10:04:00Z");
        cdrDTO.setTimestampStop("2021-04-05T11:27:00Z");

        RateInputDTO rateInputDTO = new RateInputDTO();
        rateInputDTO.setRate(rateDTO);
        rateInputDTO.setCdr(cdrDTO);

        String content = objectMapper.writeValueAsString(rateInputDTO);
        ResultActions resultActions = mockMvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isBadRequest());

    }

    @Test
    public void rateTest3() throws Exception {
        // if timestamp start is more than or equal to meter stop
        RateDTO rateDTO = new RateDTO();
        rateDTO.setEnergy(0.3f);
        rateDTO.setTime(2);
        rateDTO.setTransaction(1);

        CdrDTO cdrDTO = new CdrDTO();
        cdrDTO.setMeterStart(1204307L);
        cdrDTO.setMeterStop(1215230L);
        cdrDTO.setTimestampStart("2022-04-05T11:27:00Z");
        cdrDTO.setTimestampStop("2021-04-05T11:27:00Z");

        RateInputDTO rateInputDTO = new RateInputDTO();
        rateInputDTO.setRate(rateDTO);
        rateInputDTO.setCdr(cdrDTO);

        String content = objectMapper.writeValueAsString(rateInputDTO);
        ResultActions resultActions = mockMvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isBadRequest());

    }

    @Test
    public void rateTest4() throws Exception {
        // different timestamp format
        RateDTO rateDTO = new RateDTO();
        rateDTO.setEnergy(0.3f);
        rateDTO.setTime(2);
        rateDTO.setTransaction(1);

        CdrDTO cdrDTO = new CdrDTO();
        cdrDTO.setMeterStart(1204307L);
        cdrDTO.setMeterStop(1215230L);
        cdrDTO.setTimestampStart("2021.04.05.11.27");
        cdrDTO.setTimestampStop("2021.04.05.10.04");

        RateInputDTO rateInputDTO = new RateInputDTO();
        rateInputDTO.setRate(rateDTO);
        rateInputDTO.setCdr(cdrDTO);

        String content = objectMapper.writeValueAsString(rateInputDTO);
        ResultActions resultActions = mockMvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isBadRequest());

    }

}
