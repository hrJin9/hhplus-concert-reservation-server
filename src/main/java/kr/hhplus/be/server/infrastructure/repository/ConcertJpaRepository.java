package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.concert.exception.ConcertNotFoundException;
import kr.hhplus.be.server.domain.concert.model.Concert;
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.infrastructure.persistence.ConcertEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcertJpaRepository implements ConcertRepository {
    private final SpringConcertJpa jpa;

    public ConcertJpaRepository(SpringConcertJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Concert> findAll() {
        return jpa.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Concert findById(Long concertId) {
        return jpa.findById(concertId)
                .map(this::toDomain)
                .orElseThrow(() -> new ConcertNotFoundException(ErrorCode.CONCERT_NOT_FOUND));
    }

    private Concert toDomain(ConcertEntity e) {
        return Concert.reconstitute(
                e.id,
                e.name,
                e.artist,
                e.date
        );
    }

    private ConcertEntity toEntity(Concert c) {
        ConcertEntity e = new ConcertEntity();
        e.id = c.getId();
        e.name = c.getName();
        e.artist = c.getArtist();
        e.date = c.getDate();
        return e;
    }


}
