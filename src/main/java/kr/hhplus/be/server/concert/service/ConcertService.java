package kr.hhplus.be.server.concert.service;

import kr.hhplus.be.server.concert.domain.Concert;

import java.util.List;

public interface ConcertService {

    List<Concert> findAllConcerts();

    Concert findConcertById(Long concertId);
}
