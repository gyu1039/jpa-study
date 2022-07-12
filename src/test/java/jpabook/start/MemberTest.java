package jpabook.start;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


class MemberTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;

    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("jpabook");
        em = emf.createEntityManager();

    }



    @Test
    public void testSave() {

        // given
        Team team1 = new Team("team1", "팀1");
        Member m1 = new Member("member1", "회원1");
        m1.setTeam(team1);

        // when
        em.persist(team1);
        em.persist(m1);
        Member findMember = em.find(Member.class, "member1");

        // then
        Assertions.assertEquals(team1, findMember.getTeam());

    }

    @Test
    public void testSaveNonOwner() {

        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        em.persist(member2);

        Team team1 = new Team("team1", "팀1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(team1);

        Assertions.assertEquals(2, team1.getMembers().size());
    }
}