package hjh.mag.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import hjh.mag.domain.Board;
import hjh.mag.domain.BoardInfo;
import hjh.mag.domain.Member;
import hjh.mag.domain.MessageBox;
import hjh.mag.domain.Valid;
import hjh.mag.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // log 출력
@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final MemberService memberService;

  public List<BoardInfo> getBoard() {
    // return boardRepository.findAll().stream().map(board -> new BoardInfo(board)).toList();

    List<Board> boards = boardRepository.findAll();

    List<BoardInfo> boardInfos = new ArrayList<>();
    for (Board board : boards) {
      BoardInfo boardInfo = new BoardInfo(board);
      LocalDateTime createTime = boardInfo.getCreateTime();

      if (createTime != null) {
        String createTimeStr = createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        boardInfo.setCreateTimeStr(createTimeStr);
      }

      boardInfos.add(boardInfo);
    }

    return boardInfos;

  }

  public BoardInfo getBoardDetail(Long boardId) {
    Board findBoard = boardRepository.findById(boardId).orElse(new Board());
    return new BoardInfo(findBoard);
  }

  public MessageBox write(Board board, HttpServletRequest request) throws Exception {
    Member sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox(Valid.False, "게시글은 인증된 사용자만 작성할 수 있습니다.");

    try {
      if (board.getTitle().equals("")) {
        return new MessageBox(Valid.False, "제목을 입력해 주세요!");
      }
      if (board.getContent().equals("")) {
        return new MessageBox(Valid.False, "내용을 입력해 주세요!");
      }

      board.setMember(sessionMember);
      Board savedBoard = boardRepository.save(board);

      return new MessageBox(Valid.True, "게시글이 작성 되었습니다.", savedBoard);

    } catch (Exception e) {
      log.error("signUp error:", e);

      return new MessageBox(Valid.False, "알수없는 에러.");
    }

  }

}