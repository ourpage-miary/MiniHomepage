package Miary.miniWeb;

import Miary.miniWeb.MemberManager.Member;
import Miary.miniWeb.MemberManager.MemberRepository;
import Miary.miniWeb.diary.Diary;
import Miary.miniWeb.diary.DiaryRepository;
import Miary.miniWeb.diary.image.Image;
import Miary.miniWeb.service.DiaryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ForeignKeyTest {
    
    @PersistenceContext
    EntityManager em;

    @Autowired
    DiaryRepository diaryRepository;



    @Test
    void DiaryTest(){
        Member m = new Member();

        m.setId("abc");
        m.setJoindate(LocalDateTime.now());
        m.setAge(21);
        m.setPassword("1234");
        m.setTel("010-3434-2323");
        m.setName("haeun");
        em.persist(m);
        

        Diary d = new Diary();
        d.setCreated(LocalDateTime.now());
        d.setContent("ajsdnijdsnfvijdsfv");
        d.setTitle("title!!");
        d.setMember(m);

        em.persist(d);

        List<Diary> diary = diaryRepository.findByMemberId(d.getMember().getMemberIdx());

    }

    
}
