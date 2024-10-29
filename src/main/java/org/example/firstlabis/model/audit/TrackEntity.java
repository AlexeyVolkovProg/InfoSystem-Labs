package org.example.firstlabis.model.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.firstlabis.model.security.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class TrackEntity {

    @CreatedDate
    @Column(name="create_date", nullable = false, updatable = false)
    public LocalDateTime createDate;

    @LastModifiedDate
    @Column(name="last_modify_date", insertable = false)
    private LocalDateTime lastModifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false, updatable = false)
    @CreatedBy
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modify_user")
    @LastModifiedBy
    private User lastModifyUser;

    @Column(name = "edit_admin_status",  nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean editAdminStatus = false;
}
