package kr.hhplus.be.server.application.concert;

import kr.hhplus.be.server.application.concert.dto.ConcertInfo;
import kr.hhplus.be.server.domain.concert.model.Concert;
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;

import java.util.List;

public class ConcertFindService {
    private final ConcertRepository concertRepository;

    public ConcertFindService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<ConcertInfo> findAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        return concerts.stream()
                .map(ConcertInfo::from)
                .toList();
    }

    public ConcertInfo findConcertById(Long concertId) {
        Concert concert = concertRepository.findById(concertId);
        return ConcertInfo.from(concert);
    }
}
