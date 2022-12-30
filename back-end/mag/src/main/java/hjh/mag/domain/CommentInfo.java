package hjh.mag.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommentInfo {

  private Long commentId;
  private String comment;
  private LocalDateTime createTime;
  private String loginId;

  public CommentInfo(Comment comment) {
    this.comment = comment.getComment();
    this.createTime = comment.getCreateTime();
    this.commentId = comment.getId();
    this.loginId = comment.getMember().getLoginId();
  }

}
