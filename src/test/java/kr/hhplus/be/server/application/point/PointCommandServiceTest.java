package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.application.ServiceTest;
import kr.hhplus.be.server.application.point.dto.ChargePointCommand;
import kr.hhplus.be.server.application.point.dto.PointResult;
import kr.hhplus.be.server.application.point.dto.UsePointCommand;
import kr.hhplus.be.server.common.enums.TransactionType;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;
import kr.hhplus.be.server.domain.pointHistory.repository.PointHistoryRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ServiceTest
public class PointCommandServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PointCommandServiceTest.class);

    @InjectMocks
    PointCommandService pointCommandService;
    @Mock
    private PointHistoryRepository pointHistoryRepository;
    @Mock
    private PointRepository pointRepository;

    private Long USER_ID;
    private Point USER_POINT;
    private ChargePointCommand CHARGE_COMMAND;
    private UsePointCommand USE_SUCCESS_COMMAND;
    private UsePointCommand USE_FAIL_COMMAND;
    private PointHistory CHARGE_POINT_HISTORY;
    private PointHistory USE_POINT_HISTORY;

    @BeforeEach
    void setup() {
        log.info("=== ReservationCommandServiceTest 인스턴스 생성 ===");
        USER_ID = 1L;
        USER_POINT = new Point(1L, USER_ID, 100000L);
        CHARGE_COMMAND = new ChargePointCommand(10000L);
        CHARGE_POINT_HISTORY = new PointHistory(2L, USER_ID, USER_POINT.getPoint(), CHARGE_COMMAND.amount(), USER_POINT.getPoint() + CHARGE_COMMAND.amount(), TransactionType.CHARGE);

        USE_SUCCESS_COMMAND = new UsePointCommand(90000L);
        USE_POINT_HISTORY = new PointHistory(3L, USER_ID, USER_POINT.getPoint(), USE_SUCCESS_COMMAND.amount(), USER_POINT.getPoint() - USE_SUCCESS_COMMAND.amount(), TransactionType.USE);

        USE_FAIL_COMMAND = new UsePointCommand(120000L);
    }

    @Test
    void 포인트_충전이_정상적으로_성공하면_내역을_저장한다() {
        // given
        given(pointRepository.findOrCreatePoint(USER_ID)).willReturn(USER_POINT);

        // when
        PointResult result = pointCommandService.charge(USER_ID, CHARGE_COMMAND);

        // then
        verify(pointRepository).save(USER_POINT); // 내부적으로 포인트 충전한 결과 저장
        verify(pointHistoryRepository).save(any(PointHistory.class));

        assertThat(result.pointAfter()).isEqualTo(CHARGE_POINT_HISTORY.getPointAfter());
    }

    @Test
    void 포인트_사용이_정상적으로_성공하면_내역을_저장한다() {
        // given
        given(pointRepository.findByUserId(USER_ID)).willReturn(USER_POINT);

        // when
        PointResult result = pointCommandService.use(USER_ID, USE_SUCCESS_COMMAND.amount());

        // then
        verify(pointRepository).save(USER_POINT);
        verify(pointHistoryRepository).save(any(PointHistory.class));

        assertThat(result.pointAfter()).isEqualTo(USE_POINT_HISTORY.getPointAfter());
    }

    @Test
    void 포인트_잔액이_부족할_경우_예외를_던진다() {
        // given
        given(pointRepository.findByUserId(USER_ID)).willReturn(USER_POINT);

        // when, then
        assertThatThrownBy(() -> pointCommandService.use(USER_ID, USE_FAIL_COMMAND.amount()))
                .hasMessage(ErrorCode.INSUFFICIENT_POINT.getMessage());
    }
}
