package hjh.mag.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate // 생성 시간 자동 저장
    @Column(updatable = false) // SQL UPDATE문에 해당 컬럼을 포함할지 여부, 읽기 전용 필드
    private LocalDateTime createTime;

    @LastModifiedDate // 수정 시간 자동 저장
    private LocalDateTime updateTime;

}
