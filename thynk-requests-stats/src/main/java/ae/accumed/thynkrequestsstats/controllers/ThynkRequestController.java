package ae.accumed.thynkrequestsstats.controllers;

import ae.accumed.thynkrequestsstats.Services.ThynkRequestService;
import ae.accumed.thynkrequestsstats.repositories.ThynkRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ThynkRequestController {

    private final ThynkRequestRepository thynkRequestRepository;
    private final ThynkRequestService thynkRequestService;

    @Autowired
    public ThynkRequestController(ThynkRequestRepository thynkRequestRepository, ThynkRequestService thynkRequestService) {
        this.thynkRequestRepository = thynkRequestRepository;
        this.thynkRequestService = thynkRequestService;
    }

    @GetMapping
    public ResponseEntity<Object> thynkRequests(@RequestParam(defaultValue = "") String startDate, @RequestParam(defaultValue = "") String endDate) {
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            return new ResponseEntity<>(thynkRequestRepository.findThynkRequestByCreatedAtBetween(startDate, endDate), HttpStatus.OK);
        }
        return new ResponseEntity<>(thynkRequestRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Object> thynkRequestsCount(@RequestParam(defaultValue = "") String startDate, @RequestParam(defaultValue = "") String endDate) {
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            return new ResponseEntity<>(thynkRequestRepository.findThynkRequestByCreatedAtBetween(startDate, endDate), HttpStatus.OK);
        }
        return new ResponseEntity<>(thynkRequestService.getStats(), HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> thynkRequest(@PathVariable String requestId) {
        return new ResponseEntity<>(thynkRequestRepository.findThynkRequestById(requestId), HttpStatus.OK);
    }
}
