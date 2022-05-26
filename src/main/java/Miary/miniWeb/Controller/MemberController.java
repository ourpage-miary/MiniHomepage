package Miary.miniWeb.Controller;


import Miary.miniWeb.MemberManager.Member;
import Miary.miniWeb.MemberManager.MemberForm;
import Miary.miniWeb.MemberManager.profile.Profile;
import Miary.miniWeb.MemberManager.profile.ProfileForm;
import Miary.miniWeb.MemberManager.profile.profileImage.ProfileImage;
import Miary.miniWeb.diary.message.Message;
import Miary.miniWeb.login.LoginForm;
import Miary.miniWeb.service.*;
import Miary.miniWeb.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result, Model model) throws IOException {

        Member member = new Member();
        member.setMemberIdx(form.getMemberIdx());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setId(form.getId());
        member.setAge(form.getAge());
        member.setTel(form.getTel());
        member.setJoindate(LocalDateTime.now());

        member.setProfile(form.getProfile());

        memberService.join(member);
        model.addAttribute("loginForm", new LoginForm());

        return "redirect:/login";
    }

    @GetMapping(value = "/members/{memberIdx}/edit")
    public String updateMemberForm(@PathVariable("memberIdx") Long memberIdx, Model model){
        Member member = memberService.findMemberIdx(memberIdx);

        MemberForm memberEditForm = new MemberForm();
        memberEditForm.setMemberIdx(member.getMemberIdx());
        memberEditForm.setId(member.getId());
        memberEditForm.setName(member.getName());
        memberEditForm.setAge(member.getAge());
        memberEditForm.setPassword(member.getPassword());
        memberEditForm.setTel(member.getTel());
        memberEditForm.setJoindate(member.getJoindate());

        model.addAttribute("memberEditForm", memberEditForm);
        model.addAttribute("memberIdx", member.getMemberIdx());
        return "members/updateMemberForm";
    }

    @PostMapping(value = "/members/{memberIdx}/edit")
    public String updateMember(@ModelAttribute("memberEditForm") MemberForm memberEditForm, Model model){
        Member member = new Member();
        member.setMemberIdx(memberEditForm.getMemberIdx());
        member.setId(memberEditForm.getId());
        member.setName(memberEditForm.getName());
        member.setAge(memberEditForm.getAge());
        member.setPassword(memberEditForm.getPassword());
        member.setTel(memberEditForm.getTel());
        member.setJoindate(memberEditForm.getJoindate());
        member.setProfile(memberEditForm.getProfile());
        model.addAttribute("memberIdx", member.getMemberIdx());

        memberService.update(member);

        log.info("update member name = {}", member.getName());
        return "members/updateMemberForm";
    }

    //관리자용 회원 관리
    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //세션 제거
        }
        return "redirect:/";
    }

    @GetMapping("/{memberIdx}/destroy")
    public String deleteMember(@PathVariable("memberIdx") Long memberIdx,HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);

        Member member = memberService.findMemberIdx(memberIdx);
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        memberService.destroy(member);
        return "home";
    }

}
