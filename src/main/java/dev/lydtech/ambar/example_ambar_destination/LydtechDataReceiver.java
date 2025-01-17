package dev.lydtech.ambar.example_ambar_destination;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LydtechDataReceiver {
    private static final Logger LOG = LoggerFactory.getLogger(LydtechDataReceiver.class);
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> handle(@RequestHeader HttpHeaders headers,
                                                                 @RequestBody String body) {
//        headers.forEach((key, values) -> logger.info("Header: {} -> Value(s): {}", key, values));

        LOG.info("Request Body: {}", body);

        //todo - dedup?
        //todo CRUD aggregate in postgres

        Map<String, Object> response = Map.of("result", Map.of("success", Map.of()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
