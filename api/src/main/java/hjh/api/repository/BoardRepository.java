package hjh.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hjh.api.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

  @EntityGraph(attributePaths = { "member", "comments" })
  List<Board> findAllByOrderByCreateTimeDesc();

  @Query("select b, m from Board b join Member m on b.member = m order by b.createTime desc")
  List<Board> findWithMemberAll1();

  @Query("select b, m from Board b join b.member m order by b.createTime desc")
  List<Board> findWithMemberAll2();

  @Query("select b from Board b join fetch b.member order by b.createTime desc")
  List<Board> findWithMemberAll3();

  @Query(//
      countQuery = "select count(b.id) from Board b", //
      value = "select b from Board b join fetch b.member m order by b.createTime desc")
  Page<Board> findWithMemberAll4(Pageable page);

  @Query("select b from Board b join fetch b.member m where b.id = :id")
  Optional<Board> findWidthMemberById(@Param("id") Long id);

}
