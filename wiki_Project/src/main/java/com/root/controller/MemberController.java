package com.root.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.root.domain.MemberVO;
import com.root.service.MemberService;

@Controller
@SessionAttributes("user")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	private MemberService service;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void main(Model model, HttpServletRequest request) throws Exception {
		try {
			MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
			logger.info("main - " + userinfo.toString());
			model.addAttribute("userid", userinfo.getMemberid());
		} catch(Exception e) {
			logger.info("catch");
			model.addAttribute("userid", null);
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() throws Exception {
		logger.info("login");
		return "member/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginAction(@ModelAttribute MemberVO memberVO, Model model) throws Exception {
		logger.info("memberVO : " + memberVO);
		
		MemberVO memberinfo = service.getMember(memberVO);
		if(memberinfo != null) {
			if(service.blockIDCheck(memberinfo.getMemberid()) != 0) {
				model.addAttribute("msg", "차단된 ID입니다.");
				model.addAttribute("url", "javascript:history.go(-1)");
				return "member/login_result";
			}
			model.addAttribute("user", memberinfo);
			
			model.addAttribute("msg", memberinfo.getMemberid()+"님 환영합니다");
			//model.addAttribute("url", "javascript:history.go(-2)");
		} else {
			model.addAttribute("msg", "아이디 혹은 비밀번호가 틀립니다");
		}
		model.addAttribute("url", "javascript:history.go(-1)");
		
		return "member/login_result";
	}
	
	@RequestMapping(value = "/logout")
	public String logout() throws Exception {
		logger.info("logout");
		return "member/logout";
	}
	// ------------------회원 탈퇴 ----------------------------
	@RequestMapping(value = "/withdraw")
	public String withdraw( HttpServletRequest request, MemberVO membervo, Model model) throws Exception {
		logger.info("withdraw");
		MemberVO userinfo = (MemberVO) request.getSession().getAttribute("user");
		service.withdrawMember(userinfo);
		return "member/withdraw";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String regist(MemberVO memberVO) throws Exception {
		logger.info("regist");
		return "member/regist";
	}
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String registAction(
			@Valid MemberVO memberVO, 
			BindingResult br,
			HttpServletRequest request,
			Model model) throws Exception {
		logger.info("memberVO : " + memberVO);
		
		if(br.hasErrors()) {
			List<ObjectError> list = br.getAllErrors();
			for(ObjectError e : list) {
				logger.info("error : " + e);
			}
			return "member/regist";
		}
		
		service.insertMember(memberVO);
		
		return "redirect:main"; //수정: 주소변경
	}
	
	@RequestMapping(value = "/idCheck")
	public String idCheck(HttpServletRequest request, MemberVO membervo) throws Exception {
		logger.info("idCheck - member : " + membervo);
		
		int search = service.idCheck(membervo.getMemberid());
		if(search == 1) {
			request.setAttribute("msg", "이미 존재하는 ID입니다.");
		} else if(search == 0) {
			request.setAttribute("msg", "사용 가능한 ID입니다.");
			request.setAttribute("idch", "1"); //추가: 중복확인버튼 눌렀는지 확인
			
		}
		return "member/regist";
	}
	
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypage(HttpServletRequest request) {
		logger.info("mypage");
		MemberVO memberinfo = (MemberVO)request.getSession().getAttribute("user");
		if(memberinfo == null) {
			return "main";
		}
		if(memberinfo.getMemberlevel() == 1) {
			return "member/admin_page";
		} else {
			return "member/mypage";
		}
	}
	
	@RequestMapping(value = "/block", method = RequestMethod.POST)
	public String block(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String ip = request.getParameter("ip");
		logger.info("block - id = " + id);
		if(id != null) {
			model.addAttribute("type", 1);
			model.addAttribute("target", id);
		} else if(ip != null) {
			model.addAttribute("type", 2);
			model.addAttribute("target", ip);
		} else {
			model.addAttribute("type", 1);
		}
		return "member/admin_block";
	}
	
	@RequestMapping(value = "/blockAction", method = RequestMethod.POST)
	public String blockAction(HttpServletRequest request, Model model) throws Exception {
		String block_type = (String)request.getParameter("block_type");
		String block_target = (String)request.getParameter("block_target");
		logger.info("blockAction - " + block_type + ", " + block_target);
		
		MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
		
		if(userinfo == null) {
			model.addAttribute("msg", "noUser");
		} else if(userinfo.getMemberlevel() != 1) {
			model.addAttribute("msg", "noAdmin");
		} else if(block_type.equals("회원ID")) {
			if(service.idCheck(block_target) == 0) {
				model.addAttribute("msg", "noTarget");
			} else if(service.adminCheck(block_target) == 1) {
				model.addAttribute("msg", "adminBlock");
			} else {
				service.blockMember(block_target);
				model.addAttribute("msg", "complete");
			}
		} else if(block_type.equals("IP")) {
			service.blockIp(block_target);
			model.addAttribute("msg", "complete");
		}
		model.addAttribute("type", 1);
		
		return "member/admin_block";
	}
}
