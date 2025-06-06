package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class ReservationEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long userId;
    public Long concertSeatId;
}
