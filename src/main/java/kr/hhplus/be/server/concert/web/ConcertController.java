package kr.hhplus.be.server.concert.web;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.web.response.ConcertResponse;
import kr.hhplus.be.server.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertService concertService;

    @GetMapping
    public ResponseEntity<List<ConcertResponse>> findAllConcerts() {
        List<Concert> concerts = concertService.findAllConcerts();
        List<ConcertResponse> concertResponses = concerts.stream()
                .map(ConcertResponse::from)
                .toList();
        return ResponseEntity.ok(concertResponses);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponse> findConcertById(@PathVariable Long concertId) {
        Concert concert = concertService.findConcertById(concertId);
        ConcertResponse concertResponse = ConcertResponse.from(concert);
        return ResponseEntity.ok(concertResponse);
    }
}
