package kr.hhplus.be.server.application.point.dto;

import kr.hhplus.be.server.common.enums.TransactionType;
import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;

public record PointResult(
        Long pointBefore,
        Long amount,
        Long pointAfter,
        TransactionType transactionType
) {

    public static PointResult from(PointHistory history) {
        return new PointResult(
                history.getPointBefore(),
                history.getAmount(),
                history.getPointAfter(),
                history.getTransactionType()
        );
    }
}
