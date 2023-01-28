package hjh.mag.domain.dto.comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hjh.mag.domain.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentInfo {

  private Long commentId;
  private String comment;
  private String loginId;
  private String createTimeStr;
  private String updateTimeStr;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

  public static CommentInfo generate(Comment comment) {
    CommentInfo commentInfo = new CommentInfo();

    commentInfo.commentId = comment.getId();
    commentInfo.comment = comment.getComment();
    commentInfo.loginId = comment.getMember().getLoginId();
    commentInfo.setCreateTime(comment.getCreateTime());
    commentInfo.setUpdateTime(comment.getUpdateTime());

    return commentInfo;
  }

  private void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
    setCreateTimeStr();
  }

  private void setUpdateTime(LocalDateTime updDateTime) {
    this.updateTime = updDateTime;
    setUpdateTimeStr();
  }

  private void setCreateTimeStr() {
    if (createTime == null)
      return;

    createTimeStr = createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

  private void setUpdateTimeStr() {
    if (updateTime == null)
      return;

    updateTimeStr = updateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

}
