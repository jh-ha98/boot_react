package hjh.mag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hjh.mag.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
  
}
