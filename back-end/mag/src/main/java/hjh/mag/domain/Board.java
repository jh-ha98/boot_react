package hjh.mag.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Board {
  
  @Id
  @GeneratedValue
  @Column
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  @Lob // 255개 이상의 문자저장
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  // 작성자 입장에서는 다대일 관계
  @JoinColumn(name = "member_id")
  private Member member;

}
