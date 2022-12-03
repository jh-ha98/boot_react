package jh.project.myproject.user;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  // public List<Member> findById(String id);
  // public List<Member> findByPassword(String password);
  // public List<Member> findByNameLike(String keyword);

  Optional<Member> findByLoginId(String loginId);

  List<Member> findByLoginIdOrEmail(String loginId, String email);

}