package api.hastobe.csms.rest;

import api.hastobe.csms.service.RateService;
import api.hastobe.csms.service.dto.RateInputDTO;
import api.hastobe.csms.service.dto.RateOutputDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     *
     * @param input
     * @return
     */
    @PostMapping("/rate")
    public ResponseEntity<RateOutputDTO> rate(@RequestBody RateInputDTO input) {
        RateOutputDTO rateOutputDTO = rateService.calculateRates(input);
        return ResponseEntity.ok(rateOutputDTO);
    }
}
