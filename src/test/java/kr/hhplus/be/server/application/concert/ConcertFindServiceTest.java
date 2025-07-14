package kr.hhplus.be.server.application.concert;

import kr.hhplus.be.server.application.ServiceTest;
import kr.hhplus.be.server.application.concert.dto.ConcertInfo;
import kr.hhplus.be.server.domain.concert.model.Concert;
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;
import kr.hhplus.be.server.exception.ConcertNotFoundException;
import kr.hhplus.be.server.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ServiceTest
public class ConcertFindServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ConcertFindServiceTest.class);

    @InjectMocks
    private ConcertFindService concertFindService;

    @Mock
    private ConcertRepository concertRepository;

    private Concert CONCERT_NOW;
    private Concert CONCERT_BEFORE;
    private Concert CONCERT_TOMORROW;

    @BeforeEach
    void setUp() {
        log.info("=== ConcertFindService 인스턴스 생성 ===");

        CONCERT_NOW = new Concert(1L, "예시콘서트", "아티스트1", LocalDateTime.now());
        CONCERT_BEFORE = new Concert(2L, "끝난 콘서트", "아티스트1", LocalDateTime.now().minusDays(5));
        CONCERT_TOMORROW = new Concert(3L, "내일 콘서트", "아티스트1", LocalDateTime.now().plusDays(1));
    }

    @Test
    void 예약가능한_콘서트_목록을_반환한다() {
        // given
        List<Concert> concerts = List.of(CONCERT_NOW, CONCERT_TOMORROW);
        given(concertRepository.findAvailableConcerts()).willReturn(concerts);

        // when
        List<ConcertInfo> results = concertFindService.findAvailableConcerts();

        // then
        assertThat(results).containsExactlyInAnyOrder(ConcertInfo.from(CONCERT_NOW), ConcertInfo.from(CONCERT_TOMORROW));
    }

    @Test
    void 콘서트_아이디로_조회시_존재하는_콘서트를_성공적으로_반환한다() {
        // given
        Long concertId = CONCERT_NOW.getId();
        given(concertRepository.findById(concertId)).willReturn(CONCERT_NOW);

        // when
        ConcertInfo concertInfo = concertFindService.findConcertById(concertId);

        // then
        assertThat(concertInfo.concertId()).isEqualTo(concertId);
    }

    @Test
    void 존재하지_않는_콘서트_아이디로_조회시_예외를_던진다() {
        // given
        Long concertId = 4L;
        given(concertRepository.findById(concertId)).willThrow(new ConcertNotFoundException(ErrorCode.CONCERT_NOT_FOUND));

        // when, then
        assertThatThrownBy(() -> concertFindService.findConcertById(concertId))
                .isInstanceOf(ConcertNotFoundException.class)
                .hasMessage(ErrorCode.CONCERT_NOT_FOUND.getMessage());
    }
}
