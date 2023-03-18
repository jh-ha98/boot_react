package hjh.api.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import hjh.api.domain.dto.comment.CommentInfo;
import hjh.api.domain.dto.comment.CommentWriteForm;
import hjh.api.domain.dto.common.MessageBox;
import hjh.api.domain.dto.member.MemberInfo;
import hjh.api.domain.entity.Board;
import hjh.api.domain.entity.Comment;
import hjh.api.domain.entity.Member;
import hjh.api.domain.type.MessageBoxValid;
import hjh.api.repository.BoardRepository;
import hjh.api.repository.CommentRepository;
import hjh.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // log 출력
@RequiredArgsConstructor
@Service
public class CommentService {

  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;
  private final MemberService memberService;

  /** 댓글 저장 */
  public MessageBox<CommentInfo> commentWrite(CommentWriteForm form, HttpServletRequest request) throws Exception {
    MemberInfo sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox<>(MessageBoxValid.FALSE, "댓글은 인증된 사용자만 작성할 수 있습니다.");

    try {
      if (form.getComment().equals("")) {
        return new MessageBox<>(MessageBoxValid.FALSE, "댓글을 입력해 주세요!");
      }

      Board board = boardRepository.findById(form.getBoardId()).orElse(null);

      if (board == null)
        return new MessageBox<>(MessageBoxValid.FALSE, "없는 게시글 입니다.");

      Member member = memberRepository.findById(sessionMember.getMemberId()).get();
      Comment newComment = new Comment(form.getComment(), board, member);
      Comment savedComment = commentRepository.save(newComment);

      return new MessageBox<>(MessageBoxValid.TRUE, "댓글이 작성 되었습니다.", CommentInfo.generate(savedComment));

    } catch (Exception e) {
      log.error("commentWrite error", e);

      return new MessageBox<>(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }

  /** 댓글 삭제 */
  public MessageBox<Boolean> commentDelete(Long commentId, HttpServletRequest request) throws Exception {
    MemberInfo sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox<>(MessageBoxValid.FALSE, "댓글은 인증된 사용자만 삭제할 수 있습니다.");

    try {
      Comment findComment = commentRepository.findById(commentId).orElseThrow();

      if (!sessionMember.getLoginId().equals(findComment.getMember().getLoginId()))
        return new MessageBox<>(MessageBoxValid.FALSE, "본인이 작성한 댓글만 삭제 할 수 있습니다.");

      commentRepository.deleteById(commentId);
      return new MessageBox<>(MessageBoxValid.TRUE, "댓글이 삭제 되었습니다.");
    } catch (Exception e) {
      log.error("boardDelete error:", e);

      return new MessageBox<>(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }

  /** 댓글 수정 */
  public MessageBox<CommentInfo> commentUpdate(Long commentId, String comment, HttpServletRequest request)
      throws Exception {
    MemberInfo sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox<>(MessageBoxValid.FALSE, "댓글은 인증된 사용자만 수정할 수 있습니다.");

    try {
      Comment findComment = commentRepository.findById(commentId).orElseThrow();

      if (!sessionMember.getLoginId().equals(findComment.getMember().getLoginId()))
        return new MessageBox<>(MessageBoxValid.FALSE, "본인이 작성한 댓글만 수정 할 수 있습니다.");

      findComment.changeComment(comment);

      if (findComment.getComment().equals(""))
        return new MessageBox<>(MessageBoxValid.FALSE, "내용을 입력해 주세요!");

      Comment savedComment = commentRepository.save(findComment);
      CommentInfo commentInfo = CommentInfo.generate(savedComment);
      return new MessageBox<>(MessageBoxValid.TRUE, "댓글이 수정 되었습니다.", commentInfo);

    } catch (Exception e) {
      log.error("commentUpdate error:", e);

      return new MessageBox<>(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }
}
