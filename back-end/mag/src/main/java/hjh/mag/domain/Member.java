package hjh.mag.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.Data;

@Data // @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
// 어노테이션을 한꺼번에 설정해주는 어노테이션
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoll roll;

    @PrePersist
    private void prePersist() {
        this.roll = this.roll == null ? MemberRoll.User : this.roll;
    }

}
