package hjh.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hjh.api.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
  
}
