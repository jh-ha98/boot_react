package hjh.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hjh.api.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("select c from Comment c join fetch c.member m where c.board = :board c.createdDate asc")
  Optional<Comment> findByComment();
}
