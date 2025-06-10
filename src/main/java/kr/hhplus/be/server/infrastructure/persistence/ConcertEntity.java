package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "concert")
public class ConcertEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String artist;

    public LocalDateTime date;
}
