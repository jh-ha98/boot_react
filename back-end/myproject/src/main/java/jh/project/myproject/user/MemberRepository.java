package jh.project.myproject.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // public List<Member> findById(String id);
    // public List<Member> findByName(String name);
    // public List<Member> findByPassword(String password);
    // public List<Member> findByNameLike(String keyword);

    Optional<Member> findByLoginId(String loginId);
    /* 유효성 검사 - 중복 체크
	 * 중복 : true
	 * 중복이 아닌 경우 : false
	 */
    Boolean existsByLoginId(String loginId);
    Boolean existsByEmail(String email);
}