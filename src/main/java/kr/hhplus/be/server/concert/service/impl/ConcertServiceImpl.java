package kr.hhplus.be.server.concert.service.impl;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.repository.ConcertRepository;
import kr.hhplus.be.server.concert.service.ConcertService;
import kr.hhplus.be.server.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {
    private final ConcertRepository concertRepository;

    @Override
    public List<Concert> findAllConcerts() {
        return concertRepository.findAll();
    }

    @Override
    public Concert findConcertById(Long concertId) {
        return concertRepository.findById(concertId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "콘서트 정보가 존재하지 않습니다."));
    }
}
