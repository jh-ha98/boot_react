package hjh.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hjh.api.domain.dto.board.BoardInfo;
import hjh.api.domain.dto.board.BoardUpdateForm;
import hjh.api.domain.dto.board.BoardWriteForm;
import hjh.api.domain.dto.common.MessageBox;
import hjh.api.domain.dto.member.MemberInfo;
import hjh.api.domain.entity.Board;
import hjh.api.domain.entity.Member;
import hjh.api.domain.type.MessageBoxValid;
import hjh.api.repository.BoardRepository;
import hjh.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;
  private final MemberService memberService;

  public MessageBox<List<BoardInfo>> getBoard(Integer page) {
    // 게시글이 최신순으로 정렬되도록 Sort추가
    // List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
    // List<Board> boards = boardRepository.findAllByOrderByCreateTimeDesc();
    PageRequest request = PageRequest.of(page, 2);
    Page<Board> pageBoard = boardRepository.findWithMemberAll4(request);
    List<BoardInfo> boardInfos = pageBoard.map(board -> BoardInfo.generate(board, false)).toList();

    return new MessageBox<>(MessageBoxValid.TRUE, pageBoard.isLast() ,boardInfos);
  }

  public BoardInfo getBoardDetail(Long boardId) {
    Board findBoard = boardRepository.findWidthMemberById(boardId).orElseThrow();
    return BoardInfo.generate(findBoard, false);
  }

  /** 게시글 조회 */
  public BoardInfo select(Long boardId) {
    Board findBoard = boardRepository.findById(boardId).orElseThrow();
    return BoardInfo.generate(findBoard, false);
  }

  /** 게시글 작성 */
  @Transactional
  public MessageBox<BoardInfo> write(BoardWriteForm form, HttpServletRequest request) {
    MemberInfo sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox<>(MessageBoxValid.FALSE, "게시글은 인증된 사용자만 작성할 수 있습니다.");

    Member member = memberRepository.findById(sessionMember.getMemberId()).get();
    Board board = new Board(form.getTitle(), form.getContent(), member);
    Board savedBoard = boardRepository.save(board);
    BoardInfo boardInfo = BoardInfo.generate(savedBoard, false);

    return new MessageBox<>(MessageBoxValid.TRUE, "게시글이 작성 되었습니다.", boardInfo);
  }

  /** 게시글 삭제 */
  @Transactional
  public MessageBox<Boolean> delete(Long boardId, HttpServletRequest request) {
    MemberInfo sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox<>(MessageBoxValid.FALSE, "게시글은 인증된 사용자만 삭제할 수 있습니다.");

      Board findBoard = boardRepository.findById(boardId).orElseThrow();

      if (!sessionMember.getLoginId().equals(findBoard.getMember().getLoginId()))
        return new MessageBox<>(MessageBoxValid.FALSE, "해당 게시글을 삭제 할 수 없습니다.");

      boardRepository.deleteById(boardId);
      return new MessageBox<>(MessageBoxValid.TRUE, "게시글이 삭제 되었습니다.");
  }

  /** 게시글 수정 */
  @Transactional
  public MessageBox<BoardInfo> update(BoardUpdateForm form, HttpServletRequest request) {
    MemberInfo sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox<>(MessageBoxValid.FALSE, "게시글은 인증된 사용자만 수정할 수 있습니다.");

    Board findBoard = boardRepository.findById(form.getId()).orElseThrow();

    if (!sessionMember.getLoginId().equals(findBoard.getMember().getLoginId()))
      return new MessageBox<>(MessageBoxValid.FALSE, "본인이 작성한 게시글만 수정 할 수 있습니다.");

    findBoard.changeBoard(form.getTitle(), form.getContent());

    Board savedBoard = boardRepository.save(findBoard);
    BoardInfo boardInfo = BoardInfo.generate(savedBoard, false);

    return new MessageBox<>(MessageBoxValid.TRUE, "게시글이 수정 되었습니다.", boardInfo);
  }
}