package hjh.mag.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Board extends EntityBase {

  @Id
  @GeneratedValue
  @Column
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  // 작성자 입장에서는 다대일 관계
  @JoinColumn(name = "member_id")
  private Member member;

  @OrderBy("createTime asc") // 댓글 작성시 최근 순으로 볼 수 있도록 정렬
  @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<Comment> comments = new ArrayList<Comment>();

}
