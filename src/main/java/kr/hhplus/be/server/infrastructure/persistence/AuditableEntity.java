package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FilterDef(name = "deletedFilter", defaultCondition = "deleted = false")
@Filters({
        @Filter(name = "deletedFilter")
})
public class AuditableEntity {
    @Column(nullable = false)
    public Boolean deleted = false;

    @CreatedDate
    public LocalDateTime createdAt;

    @LastModifiedDate
    public LocalDateTime updatedAt;

    public void delete() {
        this.deleted = true;
    }
}
