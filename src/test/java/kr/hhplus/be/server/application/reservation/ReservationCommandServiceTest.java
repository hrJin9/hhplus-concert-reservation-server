package kr.hhplus.be.server.application.reservation;

import kr.hhplus.be.server.application.ServiceTest;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationCommand;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;
import kr.hhplus.be.server.application.seat.SeatCommandService;
import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.domain.concert.model.Concert;
import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.seat.model.Seat;
import kr.hhplus.be.server.domain.seat.repository.SeatLockRepository;
import kr.hhplus.be.server.domain.seat.repository.SeatRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
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
public class ReservationCommandServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ReservationCommandServiceTest.class);

    @InjectMocks
    private ReservationCommandService reservationCommandService;

    @Mock
    private SeatLockRepository seatLockRepository;

    @Mock
    private SeatCommandService seatCommandService;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private Concert CONCERT;
    private Seat SEAT;
    private PlaceReservationCommand PLACE_COMMAND;
    private Long USER_ID;

    @BeforeEach
    void setUp() {
        log.info("=== ReservationCommandServiceTest 인스턴스 생성 ===");

        CONCERT = new Concert(1L, "2025 오아시스 내한 콘서트", "오아시스", LocalDateTime.now().plusMonths(3));
        SEAT = new Seat(2L, CONCERT.getId(), 250000L, SeatStatus.AVAILABLE, null);

        PLACE_COMMAND = new PlaceReservationCommand(CONCERT.getId(), SEAT.getId());
        USER_ID = 100L;
    }

    @Test
    void 예약가능한_좌석을_예약요청할_시_정상적으로_예약에_성공한_뒤_예약_정보를_반환한다() {
        // given
        Reservation reservation = Reservation.create(USER_ID, SEAT.getId());
        given(reservationRepository.save(any())).willReturn(reservation);

        // when
        PlaceReservationResult result = reservationCommandService.reserve(USER_ID, PLACE_COMMAND);

        // then
        verify(seatCommandService).reserveSeat(USER_ID, SEAT.getId());
        verify(reservationRepository).save(any());

        assertThat(result).isNotNull();
    }

    @Test
    void 결제_가능시간이_지난_예약은_해당_좌석과_함께_만료로_상태를_변경한다() {
        // given
        Reservation expired1 = new Reservation(3L, 88L, 77L, ReservationStatus.HOLD);
        Reservation expired2 = new Reservation(4L, 81L, 72L, ReservationStatus.HOLD);

        List<Reservation> expiredReservations = List.of(expired1, expired2);

        given(reservationRepository.findAllExpired()).willReturn(expiredReservations);

        // when
        reservationCommandService.cancelExpiredReservations();

        // then
        assertThat(expired1.getStatus()).isEqualTo(ReservationStatus.EXPIRED);
        assertThat(expired2.getStatus()).isEqualTo(ReservationStatus.EXPIRED);

        verify(seatCommandService).expireSeat(expired1.getSeatId());
        verify(seatCommandService).expireSeat(expired2.getSeatId());

        verify(reservationRepository).saveAll(expiredReservations);
    }
}
