package hjh.mag.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentInfo {

  private Long commentId;
  private String comment;
  private LocalDateTime createTime;
  private String createTimeStr;
  private String loginId;

  public static CommentInfo generate(Comment comment) {
    CommentInfo commentInfo = new CommentInfo();

    commentInfo.commentId = comment.getId();
    commentInfo.comment = comment.getComment();
    commentInfo.loginId = comment.getMember().getLoginId();
    commentInfo.setCreateTime(comment.getCreateTime());
    
    return commentInfo;
  }

  private void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
    setCreateTimeStr();
  }

  private void setCreateTimeStr() {
    if (createTime == null)
      return;
    
    createTimeStr = createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

}
