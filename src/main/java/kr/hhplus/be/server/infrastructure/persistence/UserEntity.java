package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String email;

    public String password;

    public String name;

    public String nickName;

    public String phone;
}
