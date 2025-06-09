package kr.hhplus.be.server.point.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.exception.InsufficientPointException;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.infrastructure.persistence.AuditableEntity;
import lombok.Getter;

@Entity
@Table(name = "point")
@Getter
public class Point extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    private UserEntity userEntity;
    private Long userId;

    private Long point;

    protected Point(Long userId, Long point) {
        this.userId = userId;
        this.point = point;
    }

    public static Point create(Long userId) {
        return new Point(
                userId,
                0L
        );
    }

    public static Point save(Long userId, Long point) {
        return new Point(
                userId,
                point
        );
    }

    public PointHistory charge(Long amount) {
        Long beforePoint = this.point;
        this.point += amount;

        return PointHistory.charge(
                this.userId,
                beforePoint,
                amount,
                this.point
        );
    }

    public PointHistory use(Long amount) {
        if(this.point < amount) {
            throw new InsufficientPointException(ErrorCode.INSUFFICIENT_POINT);
        }

        Long beforePoint = this.point;
        this.point -= amount;

        return PointHistory.use(
                this.userId,
                beforePoint,
                amount,
                this.point
        );
    }
}
