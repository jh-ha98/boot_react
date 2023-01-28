package hjh.mag.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import hjh.mag.domain.dto.comment.CommentInfo;
import hjh.mag.domain.dto.common.MessageBox;
import hjh.mag.domain.entity.Board;
import hjh.mag.domain.entity.Comment;
import hjh.mag.domain.entity.Member;
import hjh.mag.domain.type.MessageBoxValid;
import hjh.mag.repository.BoardRepository;
import hjh.mag.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // log 출력
@RequiredArgsConstructor
@Service
public class CommentService {

  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;
  private final MemberService memberService;

  /** 댓글 저장 */
  public MessageBox commentWrite(String commentText, Long boardId, HttpServletRequest request) throws Exception {
    Member sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox(MessageBoxValid.FALSE, "댓글은 인증된 사용자만 작성할 수 있습니다.");

    try {
      if (commentText.equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "댓글을 입력해 주세요!");
      }

      Board board = boardRepository.findById(boardId).orElse(null);

      if (board == null)
        return new MessageBox(MessageBoxValid.FALSE, "없는 게시글 입니다.");

      Comment comment = new Comment();
      comment.setBoard(board);
      comment.setComment(commentText);
      comment.setMember(sessionMember);

      Comment savedComment = commentRepository.save(comment);

      return new MessageBox(MessageBoxValid.TRUE, "댓글이 작성 되었습니다.", CommentInfo.generate(savedComment));

    } catch (Exception e) {
      log.error("commentSave error", e);

      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }

  /** 댓글 삭제 */
  public MessageBox commentDelete(Long commentId, HttpServletRequest request) throws Exception {
    Member sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox(MessageBoxValid.FALSE, "댓글은 인증된 사용자만 삭제할 수 있습니다.");

    try {
      Comment findComment = commentRepository.findById(commentId).orElseThrow();

      if (!sessionMember.getLoginId().equals(findComment.getMember().getLoginId()))
        return new MessageBox(MessageBoxValid.FALSE, "본인이 작성한 댓글만 삭제 할 수 있습니다.");

      commentRepository.deleteById(commentId);
      return new MessageBox(MessageBoxValid.TRUE, "댓글이 삭제 되었습니다.");
    } catch (Exception e) {
      log.error("boardDelete error:", e);

      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }

  /** 댓글 수정 */
  public MessageBox commentUpdate(Long commentId, String comment, HttpServletRequest request) throws Exception {
    Member sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox(MessageBoxValid.FALSE, "댓글은 인증된 사용자만 수정할 수 있습니다.");

    try {
      Comment findComment = commentRepository.findById(commentId).orElseThrow();

      if (!sessionMember.getLoginId().equals(findComment.getMember().getLoginId()))
        return new MessageBox(MessageBoxValid.FALSE, "본인이 작성한 댓글만 수정 할 수 있습니다.");

      findComment.setComment(comment);

      Comment savedComment = commentRepository.save(findComment);
      CommentInfo commentInfo = CommentInfo.generate(savedComment);
      return new MessageBox(MessageBoxValid.TRUE, "댓글이 수정 되었습니다.", commentInfo);

    } catch (Exception e) {
      log.error("boardDUpdate error:", e);

      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }
}
