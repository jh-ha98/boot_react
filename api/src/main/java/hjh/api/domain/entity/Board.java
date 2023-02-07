package hjh.api.domain.entity;

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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

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

  public Board(String title, String content, Member member) {
    this.title = title;
    this.content = content;
    this.member = member;
  }

}
