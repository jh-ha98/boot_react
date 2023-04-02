package hjh.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hjh.api.domain.entity.Board;
import hjh.api.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("select c from Comment c join fetch c.member m where c.board = :board order by c.createTime asc")
  List<Comment> findWithMemberByBoard(@Param("board") Board board);

}
