package hjh.mag.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class BoardInfo {

  private Long boardId;
  private String title;
  private String content;
  private String memberId;
  private String email;

  private List<BoardInfo> boardInfos = new ArrayList<>();

  private BoardInfo() {
  }

  // public BoardInfo(Board board) {
  // this.boadId = board.getId();
  // this.title = board.getTitle();
  // this.content = board.getContent();
  // this.memberId = board.getMember().getLoginId();
  // this.email = board.getMember().getEmail();
  // }

  public BoardInfo(List<Board> boards) {
    for (Board board : boards) {
      BoardInfo boardInfo = new BoardInfo();
      boardInfo.boardId = board.getId();
      boardInfo.title = board.getTitle();
      boardInfo.content = board.getContent();
      boardInfo.memberId = board.getMember().getLoginId();
      boardInfo.email = board.getMember().getEmail();
      this.boardInfos.add(boardInfo);
    }
  }

}
