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

    /**
     * 모든 콘서트 목록을 조회한다.
     * @return
     */
    public List<ConcertInfo> findAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        return concerts.stream()
                .map(ConcertInfo::from)
                .toList();
    }

    /**
     * 예약 가능한 콘서트 목록을 조회한다.
     * @return
     */
    public List<ConcertInfo> findAvailableConcerts() {
        List<Concert> concerts = concertRepository.findAvailableConcerts();
        return concerts.stream()
                .map(ConcertInfo::from)
                .toList();
    }

    /**
     * 콘서트 상세를 조회한다.
     * @param concertId
     * @return
     */
    public ConcertInfo findConcertById(Long concertId) {
        Concert concert = concertRepository.findById(concertId);
        return ConcertInfo.from(concert);
    }
}
