package api.hastobe.csms.service;

import api.hastobe.csms.model.Rate;
import api.hastobe.csms.service.dto.CdrDTO;
import api.hastobe.csms.service.dto.RateDTO;
import api.hastobe.csms.service.dto.RateInputDTO;
import api.hastobe.csms.service.dto.RateOutputDTO;
import api.hastobe.csms.service.exception.ValueProvidedNotCorrectException;
import api.hastobe.csms.service.mapper.RateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.util.Date;

@Service
public class RateService {

    private static final int K = 1000;


    private static final long DAY_HOURS = 24;
    private static final long MIN_SECS = 60;
    private static final long HOUR_SECS = MIN_SECS * MIN_SECS;
    private static final long DAY_SECS = HOUR_SECS * DAY_HOURS;


    Logger logger = LoggerFactory.getLogger(RateService.class);

    private final RateMapper rateMapper;

    public RateService(RateMapper rateMapper) {
        this.rateMapper = rateMapper;
    }

    public RateOutputDTO calculateRates(RateInputDTO rateInputDTO) {

        RateDTO rateDTO = rateInputDTO.getRate();
        CdrDTO cdrDTO = rateInputDTO.getCdr();
        if (rateDTO == null || cdrDTO == null)
            throw new ValueProvidedNotCorrectException();

        float energy = rateDTO.getEnergy();
        float time = rateDTO.getTime();
        float transaction = rateDTO.getTransaction();

        long meterStart = cdrDTO.getMeterStart();
        long meterStop = cdrDTO.getMeterStop();
        String timestampStartStr = cdrDTO.getTimestampStart();
        String timestampStopStr = cdrDTO.getTimestampStop();

        if (energy <= 0 || time <= 0 || transaction <= 0)
            throw new ValueProvidedNotCorrectException("energy, time, or transaction values are invalid!");

        if (meterStart == 0 || meterStop == 0 || meterStart >= meterStop)
            throw new ValueProvidedNotCorrectException("meter values are either zero or the value for " +
                    "meter start is more than meter stop!");

        if (timestampStartStr == null || timestampStopStr == null)
            throw new ValueProvidedNotCorrectException("timestamp is null!");

        // extract rate and cdr info
        Rate rate = rateMapper.toEntity(rateDTO);

        // calculate meter and time usage
        Long meter = meterStop - meterStart;
        Instant timestampStart = convertStringToInstant(timestampStartStr);
        Instant timestampStop = convertStringToInstant(timestampStopStr);

        if (timestampStart.compareTo(timestampStop) >= 0)
            throw new ValueProvidedNotCorrectException("start time is passed stop time!");

        Long duration = timestampStop.getEpochSecond() - timestampStart.getEpochSecond();
        Long days = duration / DAY_SECS;
        Long hours = duration / HOUR_SECS % DAY_HOURS;
        Long minutes = duration / MIN_SECS % MIN_SECS;

        // setup calculated rate
        rate.setEnergy(rate.getEnergy() * meter / K);
        rate.setTime(rate.getTime() * (DAY_HOURS * days + hours + ((float) minutes / MIN_SECS)));
        rate.setTransaction(rate.getTransaction());

        // create RateOutputDTO
        RateOutputDTO rateOutputDTO = new RateOutputDTO();
        rateOutputDTO.setComponent(rateMapper.toDto(rate));
        rateOutputDTO.setTotal(rate.getEnergy() + rate.getTransaction() + rate.getTime());

        return rateOutputDTO;
    }

    public Instant convertStringToInstant(String instant) {
        try {
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(instant);
            Instant i = Instant.from(ta);
            return i;
        } catch (Exception e) {
            throw new ValueProvidedNotCorrectException("time pattern given is not ISO8610!");
        }

    }


}
