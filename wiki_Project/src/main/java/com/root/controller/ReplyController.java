package com.root.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.root.domain.MemberVO;
import com.root.domain.ReplyVO;
import com.root.service.ReplyService;

@Controller
@SessionAttributes("user")
@RequestMapping("/reply")
public class ReplyController {

	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Inject
	private ReplyService service;
	
	@RequestMapping(value="/replyInsert", method=RequestMethod.POST)
	public String replyInsert(@ModelAttribute ReplyVO rvo, HttpServletRequest request) throws Exception{
		logger.info("replyInsert start");
		logger.info("rvo = "+rvo);

		String ip = getUserIP(request);
		logger.info("client ip : " + ip);
		
		try{ // 로그인 상태일 경우
			MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
			logger.info("userinfo : " + userinfo);
			rvo.setR_writer(userinfo.getMemberid()); // 작성자 = id
			rvo.setR_memberId(userinfo.getMemberid()); // r_memberId = id
		}catch(Exception e){ // 비회원일 경우 -> ip 값을 writer로 저장
			rvo.setR_writer(ip);
			rvo.setR_memberId(null);
			logger.info("ip : "+ ip);
		}
		
		rvo.setR_ip(ip); // ip 삽입
		logger.info("rvo = "+rvo);
		service.replyInsert(rvo);
		
		logger.info("replyInsert end");
		
		return "redirect:../board/content?boardNo=" + rvo.getR_boardNo() + "&replyPage=1";
	}
	
	@RequestMapping(value = "/replyDelete", method=RequestMethod.GET)
	public String replyDelete(@ModelAttribute ReplyVO rvo, HttpServletRequest request) throws Exception{
		logger.info("------------------------replyDelete start-------------------------");
		logger.info("rvo = "+rvo);
		service.replyDelete(rvo.getR_no());
		logger.info("------------------------replyDelete end-------------------------");
		return "redirect:../board/content?boardNo=" + rvo.getR_boardNo() + "&replyPage=1";
	}
	
	@RequestMapping(value="/replyUpdate", method=RequestMethod.GET)
	public String replyUpdate(HttpServletRequest request, Model model)throws Exception{
		logger.info("------------------ update start ----------------------");
		int r_no = Integer.parseInt(request.getParameter("r_no"));
		ReplyVO rvo = service.replyUpdateWindow(r_no);
		logger.info("rvo : "+rvo);
		model.addAttribute("rvo",rvo);
		return "reply/replyUpdateForm";
	}
	
	@RequestMapping(value="/replyUpdateForm")
	public void replyUpdateForm()throws Exception{
		logger.info("replyUpdateForm");
	}
	
	//수정 : 반환형 타입 void->String , return 추가
	@RequestMapping(value="/replyUpdateAction", method=RequestMethod.POST)
	public String replyUpdateAction(@ModelAttribute ReplyVO rvo)throws Exception{
		logger.info("replyUpdateAction");
		service.replyUpdate(rvo);
		logger.info("rvo : " + rvo );
		return "reply/reply_result";
	}
	
	public String getUserIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		logger.info("TEST : X-FORWARDED-FOR : "+ip);
		if (ip == null) {
		ip = request.getHeader("Proxy-Client-IP");
		logger.info("TEST : Proxy-Client-IP : "+ip);
		}
		if (ip == null) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		logger.info("TEST : WL-Proxy-Client-IP : "+ip);
		}
		if (ip == null) {
		ip = request.getHeader("HTTP_CLIENT_IP");
		logger.info("TEST : HTTP_CLIENT_IP : "+ip);
		}
		if (ip == null) {
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		logger.info("TEST : HTTP_X_FORWARDED_FOR : "+ip);
		}
		if (ip == null) {
		ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
