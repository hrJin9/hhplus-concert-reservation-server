package kr.hhplus.be.server.interfaces.web.concert;

import kr.hhplus.be.server.application.concert.ConcertFindService;
import kr.hhplus.be.server.application.concert.dto.ConcertInfo;
import kr.hhplus.be.server.interfaces.web.concert.response.ConcertInfoResponse;
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
public class ConcertFindController {
    private final ConcertFindService concertFindService;

    /**
     * 콘서트를 조회한다.
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ConcertInfoResponse>> findAllConcerts() {
        List<ConcertInfo> concerts = concertFindService.findAllConcerts();

        List<ConcertInfoResponse> concertResponses = concerts.stream()
                .map(ConcertInfoResponse::from)
                .toList();
        return ResponseEntity.ok(concertResponses);
    }

    /**
     * 콘서트 상세 정보를 조회한다.
     * @param concertId
     * @return
     */
    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertInfoResponse> findConcertById(@PathVariable Long concertId) {
        ConcertInfo concertResult = concertFindService.findConcertById(concertId);
        return ResponseEntity.ok(ConcertInfoResponse.from(concertResult));
    }
}
