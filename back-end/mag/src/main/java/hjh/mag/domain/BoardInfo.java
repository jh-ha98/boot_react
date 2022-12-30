package hjh.mag.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)  //아무런 매개변수가 없는 생성자 (외부 클래스 접근 제한)
public class BoardInfo {

  private Long boardId;
  private String title;
  private String content;
  private String memberId;
  private String email;
  private LocalDateTime createTime;
  private String createTimeStr;
  private List<CommentInfo> comments = new ArrayList<>();

  /** BoardInfo를 생성합니다 */
  public static BoardInfo generate(Board board, Boolean fetchComments) {
    BoardInfo boardInfo = new BoardInfo();

    boardInfo.boardId = board.getId();
    boardInfo.title = board.getTitle();
    boardInfo.content = board.getContent();
    boardInfo.memberId = board.getMember().getLoginId();
    boardInfo.email = board.getMember().getEmail();
    boardInfo.setCreateTime(board.getCreateTime());
    if (fetchComments)
      boardInfo.setComments(board.getComments());

    return boardInfo;
  }

  /** BoardInfo의 List를 생성합니다 */
  public static List<BoardInfo> generate(List<Board> boards, Boolean fetchComments) {

    List<BoardInfo> boardInfos = new ArrayList<>();
    for (Board board : boards) {
      BoardInfo boardInfo = generate(board, fetchComments);
      boardInfos.add(boardInfo);
    }

    return boardInfos;
  }

  private void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
    this.setCreateTimeStr();
  }

  /** createTime을 문자열로 변환합니다 */
  private void setCreateTimeStr() {
    // null 체크
    if (createTime == null)
      return;

    createTimeStr = createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  /** Comment(댓글) 를 CommentInfo로 변환하여 값을 등록합니다 */
  private void setComments(List<Comment> comments) {
    // comments.stream().map(comment -> new CommentInfo(comment)).toList();

    List<CommentInfo> commentInfos = new ArrayList<>();
    for (Comment comment : comments)
      commentInfos.add(new CommentInfo(comment));

    this.comments = commentInfos;
  }

}
