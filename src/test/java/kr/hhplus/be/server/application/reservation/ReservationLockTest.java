package kr.hhplus.be.server.application.reservation;

import kr.hhplus.be.server.application.ServiceTest;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationCommand;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;
import kr.hhplus.be.server.application.seat.SeatCommandService;
import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.domain.concert.model.Concert;
import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.domain.seat.model.Seat;
import kr.hhplus.be.server.domain.seat.repository.SeatLockRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ServiceTest
public class ReservationLockTest {
    private static final Logger log = LoggerFactory.getLogger(ReservationLockTest.class);

    @InjectMocks
    private ReservationCommandService reservationCommandService;

    @Mock
    private SeatLockRepository seatLockRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatCommandService seatCommandService;

    private Concert CONCERT;
    private Seat SEAT;
    private PlaceReservationCommand PLACE_COMMAND;
    private Long USER_ID;

    @BeforeEach
    void setup() {
        log.info("=== ReservationLockTest 인스턴스 생성 ===");

        CONCERT = new Concert(1L, "2025 오아시스 내한 콘서트", "오아시스", LocalDateTime.now().plusMonths(3));
        SEAT = new Seat(2L, CONCERT.getId(), 250000L, SeatStatus.AVAILABLE, null);
        PLACE_COMMAND = new PlaceReservationCommand(CONCERT.getId(), SEAT.getId());
        USER_ID = 100L;
    }

    @Test
    void 좌석_락을_획득하면_예약이_정상적으로_처리된다() {
        // given
        Reservation reservation = Reservation.create(USER_ID, PLACE_COMMAND.seatId());

        given(seatLockRepository.acquire(SEAT.getId(), USER_ID)).willReturn(true);
        given(reservationRepository.save(any())).willReturn(reservation);

        // when
        PlaceReservationResult result = reservationCommandService.placeWithLock(USER_ID, PLACE_COMMAND);

        // then
        verify(seatCommandService).reserveSeat(USER_ID, PLACE_COMMAND.seatId());
        verify(reservationRepository).save(any());
        verify(seatLockRepository).release(SEAT.getId(), USER_ID);
    }

    @Test
    void 좌석_락을_획득_실패하면_예외를_던진다() {
        // given
        given(seatLockRepository.acquire(SEAT.getId(), USER_ID)).willReturn(false);

        // when, then
        assertThatThrownBy(() -> reservationCommandService.placeWithLock(USER_ID, PLACE_COMMAND))
                .isInstanceOf(ApiException.class)
                .hasMessage(ErrorCode.SEAT_ALREADY_SELECTED.getMessage());
    }

    @Test
    void 예약_중_예외가_발생해도_락은_해제된다() {
        // given
        given(seatLockRepository.acquire(SEAT.getId(), USER_ID)).willReturn(true);
        willThrow(new RuntimeException("예약 실패"))
                .given(seatCommandService).reserveSeat(USER_ID, SEAT.getId());

        // when, then
        assertThatThrownBy(() -> reservationCommandService.placeWithLock(USER_ID, PLACE_COMMAND))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("예약 실패");

        verify(seatLockRepository).release(SEAT.getId(), USER_ID);
    }
}
