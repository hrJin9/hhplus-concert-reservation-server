package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.SeatStatus;

@Entity
@Table(name = "seat")
public class SeatEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long concertId;
    public Long seatId;
    public Long price;
    @Enumerated(EnumType.STRING)
    public SeatStatus seatStatus;
    public Long assignedUserId;
}
