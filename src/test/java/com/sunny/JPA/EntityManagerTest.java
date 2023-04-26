package com.sunny.JPA;

import com.sunny.JPA.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
public class EntityManagerTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Member 엔티티를 통해서 DB에 성공적으로 추가되는지 확인.")
    void insertMember() {
        // 비영속 상태.
        Member member = new Member(1L, "sunny");

        // persist() 실행 시 영속 상태. 아직 DB로 넘어가진 않음.
        entityManager.persist(member);

        // flush() 실행 시 insert 쿼리 실행.
        entityManager.flush();
        System.out.println(member.getMemberName());

        // find() 메서드 실행 시 먼저 PersistenceContext 캐시에 값이 있다면 그 곳에서 가져옴.
        // 값이 없다면 그 때 DB를 조회해서 값을 가져옴. 이 땐 select 쿼리 실행.
        Member newMember = entityManager.find(Member.class, 1L);

        // PersistenceContext 캐시에서 값을 가져오기 때문에 두 객체가 동일한 캐시를 사용.
        // 따라서 newMember 객체 값이 바뀔 시 member도 값이 바뀜.
        // 단, db값은 아직 바뀌지 않음.
        newMember.setMemberName("moon");
        System.out.println(member.getMemberName());

        // flush() 진행 시 db에 있던 정보도 update문이 실행 되면서 바뀜.
        entityManager.flush();

        assertThat(newMember).isEqualTo(member);
    }
}
