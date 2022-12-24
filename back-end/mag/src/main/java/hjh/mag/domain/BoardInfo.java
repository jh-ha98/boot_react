package hjh.mag.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardInfo {

  private Long boardId;
  private String title;
  private String content;
  private String memberId;
  private String email;
  private LocalDateTime createTime;
  private String createTimeStr;

  public BoardInfo(Board board) {
    this.boardId = board.getId();
    this.title = board.getTitle();
    this.content = board.getContent();
    this.memberId = board.getMember().getLoginId();
    this.email = board.getMember().getEmail();
    this.createTime = board.getCreateTime();
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }

}
