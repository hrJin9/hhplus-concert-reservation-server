package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.model.Concert;

import java.util.List;

public interface ConcertRepository {
    List<Concert> findAll();

    Concert findById(Long concertId);
}
