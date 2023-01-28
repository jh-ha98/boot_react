package hjh.mag.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hjh.mag.domain.dto.board.BoardInfo;
import hjh.mag.domain.dto.board.BoardWriteForm;
import hjh.mag.domain.dto.common.MessageBox;
import hjh.mag.domain.entity.Board;
import hjh.mag.domain.entity.Member;
import hjh.mag.domain.type.MessageBoxValid;
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
    // 게시글이 최신순으로 정렬되도록 Sort추가
    List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
    return BoardInfo.generate(boards, false);
  }

  public BoardInfo getBoardDetail(Long boardId) {
    Board findBoard = boardRepository.findById(boardId).orElseThrow();
    return BoardInfo.generate(findBoard, true);
  }

  public MessageBox write(BoardWriteForm form, HttpServletRequest request) throws Exception {
    Member sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox(MessageBoxValid.FALSE, "게시글은 인증된 사용자만 작성할 수 있습니다.");

    try {
      if (form.getTitle().equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "제목을 입력해 주세요!");
      }
      if (form.getContent().equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "내용을 입력해 주세요!");
      }

      Board board = new Board(form.getTitle(), form.getContent(), sessionMember);
      Board savedBoard = boardRepository.save(board);

      return new MessageBox(MessageBoxValid.TRUE, "게시글이 작성 되었습니다.", savedBoard);

    } catch (Exception e) {
      log.error("boardWrite error:", e);

      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");
    }

  }

  public MessageBox delete(Long boardId, HttpServletRequest request) throws Exception {
    Member sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox(MessageBoxValid.FALSE, "게시글은 인증된 사용자만 삭제할 수 있습니다.");

    try {
      Board findBoard = boardRepository.findById(boardId).orElseThrow();

      if (!sessionMember.getLoginId().equals(findBoard.getMember().getLoginId()))
        return new MessageBox(MessageBoxValid.FALSE, "해당 게시글을 삭제 할 수 없습니다.");

      boardRepository.deleteById(boardId);
      return new MessageBox(MessageBoxValid.TRUE, "게시글이 삭제 되었습니다.");
    } catch (Exception e) {
      log.error("boardDelete error:", e);

      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");
    }

  }

}