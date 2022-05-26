package Miary.miniWeb;

import Miary.miniWeb.MemberManager.Member;
import Miary.miniWeb.MemberManager.MemberRepository;
import Miary.miniWeb.MemberManager.profile.Profile;
import Miary.miniWeb.diary.Diary;
import Miary.miniWeb.diary.DiaryRepository;
import Miary.miniWeb.service.DiaryService;
import Miary.miniWeb.service.MemberService;
import Miary.miniWeb.service.ProfileService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MEmberServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    ProfileService profileService;

    @Test
    void delete(){

        Profile profile = new Profile();
        profile.setNickname("nickname");
        profile.setAboutMe("hi");
        profileService.saveProfile(profile);

        Member m = new Member();
        m.setId("abc");
        m.setJoindate(LocalDateTime.now());
        m.setAge(21);
        m.setPassword("1234");
        m.setTel("010-3434-2323");
        m.setName("haeun");
        m.setProfile(profile);
        em.persist(m);

        Diary d = new Diary();
        d.setCreated(LocalDateTime.now());
        d.setContent("ajsdnijdsnfvijdsfv");
        d.setTitle("title!!");
        d.setMember(m);
        em.persist(d);

        List<Diary> byTitle = diaryService.findByTitle("title!!");
        System.out.println(byTitle.get(0).getTitle());
    }




}
