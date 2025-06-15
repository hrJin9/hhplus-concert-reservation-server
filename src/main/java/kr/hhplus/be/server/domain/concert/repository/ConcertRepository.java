package kr.hhplus.be.server.domain.concert.repository;

import kr.hhplus.be.server.domain.concert.model.Concert;

import java.util.List;

public interface ConcertRepository {
    List<Concert> findAll();

    Concert findById(Long concertId);
}
