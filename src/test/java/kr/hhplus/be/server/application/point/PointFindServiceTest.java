package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.application.ServiceTest;
import kr.hhplus.be.server.application.point.dto.PointBalanceInfo;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ServiceTest
public class PointFindServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PointFindServiceTest.class);

    @InjectMocks
    private PointFindService pointFindService;
    @Mock
    private PointRepository pointRepository;

    private Long USER_ID;
    private Point POINT;

    @BeforeEach
    void setup() {
        log.info("=== PointFindServiceTest 인스턴스 생성 ===");
        USER_ID = 1L;
        POINT = new Point(2L, USER_ID, 150000L);
        log.info("userId = {}, userPoint = {}", USER_ID, POINT.getPoint());
    }

    @Test
    void 사용자의_포인트_정보를_조회한다() {
        // given
        given(pointRepository.findByUserId(USER_ID)).willReturn(POINT);

        // when
        PointBalanceInfo result = pointFindService.findByUserId(USER_ID);

        // then
        assertThat(result.point()).isEqualTo(POINT.getPoint());
    }

}
