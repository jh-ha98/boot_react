package hjh.api.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "comment_id")
  private Long id;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String comment; // 댓글 내용

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public Comment(String comment, Board board, Member member) {
    this.comment = comment;
    this.board = board;
    this.member = member;
  }

  /**
   * 댓글의 값을 수정합니다
   * @param comment 변경할 댓글
   */
  public void changeComment(String comment) {
    this.comment = comment;
  }
}
