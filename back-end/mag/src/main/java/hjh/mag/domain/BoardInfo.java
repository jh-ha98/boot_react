package hjh.mag.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class BoardInfo {

    private String title;
    private String content;
    private String memberId;
    private String email;

    private List<BoardInfo> boardInfos = new ArrayList<>();

    private BoardInfo() {
    }

    public BoardInfo(List<Board> boards) {
        for (Board board : boards) {
            BoardInfo boardInfo = new BoardInfo();
            boardInfo.title = board.getTitle();
            boardInfo.content = board.getContent();
            boardInfo.memberId = board.getMember().getLoginId();
            boardInfo.email = board.getMember().getEmail();
            this.boardInfos.add(boardInfo);
        }
    }

}
