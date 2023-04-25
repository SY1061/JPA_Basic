package com.sunny.JPA;

import com.sunny.JPA.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EntityManagerTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Member 엔티티를 통해서 DB에 성공적으로 추가되는지 확인.")
    void insertMember() {
        Member member = new Member();
        // 비영속 상태.
        member.setMemberId(1L);
        member.setMemberName("sunny");

        // persist() 실행 시 영속 상태. 아직 DB로 넘어가진 않음.
        entityManager.persist(member);

        // flush() 실행 시 쿼리 추가.
        entityManager.flush();

        // find() 메서드 실행 시 먼저 PersistenceContext 캐시에 값이 있다면 그 곳에서 가져옴.
        // 값이 없다면 그 때 DB를 조회해서 값을 가져옴.
        Member newMember = entityManager.find(Member.class, 1L);
        assertThat(newMember).isEqualTo(member);
    }
}
