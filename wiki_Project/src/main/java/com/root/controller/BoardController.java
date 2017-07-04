package com.root.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.root.domain.BackupBoardVO;
import com.root.domain.BoardVO;
import com.root.domain.HitVO;
import com.root.domain.MemberVO;
import com.root.domain.ReplyVO;
import com.root.service.BoardService;
import com.root.service.ReplyService;

@Controller
@SessionAttributes({"user","search"})
@RequestMapping("/board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	//추가: ReplyService inject
	@Inject
	private ReplyService re_service;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, HttpServletRequest request) throws Exception {
		logger.info("search");
		String ip = service.getUserIP(request);
		if(service.blockIPCheck(ip) != 0) {
			request.setAttribute("msg", "블락된 IP입니다.");
			return "main";
		}
		String searchStr = request.getParameter("search_str");
		model.addAttribute("search", searchStr);
		
		List<BoardVO> boardList = service.searchBoard(searchStr);
		boolean matchTitle = false;
		int matchBoardNo = 0;
		for(BoardVO vo : boardList) {
			if(searchStr.equals(vo.getTitle())) {
				matchTitle = true;
				matchBoardNo = vo.getBoardNo();
			}
		}
		if(matchTitle) {
			return "redirect:content?boardNo=" + matchBoardNo + "&replyPage=1";
		}
		model.addAttribute("result", boardList);
		return "board/search";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void write(Model model, HttpServletRequest request) {
		BoardVO vo = new BoardVO();
		vo.setTitle(request.getParameter("title"));
		model.addAttribute("boardVO", vo);
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeAction(HttpServletRequest request, BoardVO vo) throws Exception {
		logger.info("writeAction - " + vo);
		String ip = service.getUserIP(request);
		if(service.blockIPCheck(ip) != 0) {
			request.setAttribute("msg", "블락된 IP입니다.");
			return "main";
		}
		
		//추가:제목 중복체크
		String title = request.getParameter("title");
		int titleCheck = service.titleCheck(title);

		if(titleCheck>0){
			request.setAttribute("msg", "haveName");
			return "board/write_result";
		}
		//----------------------------------------------------------
		BoardVO boardInfo = vo;
		try {
			MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
			vo.setMemberId(userinfo.getMemberid());
		} catch(Exception e) {}
		boardInfo.setIp(ip);
		boardInfo.setContent(service.quotParser(boardInfo.getContent()));
		service.writeBoard(boardInfo);
		
		logger.info("writeAction - boardInfo(after) : " + boardInfo);
		
		request.setAttribute("boardNo", boardInfo.getBoardNo());
		
		return "board/write_result";
	}
	
	@RequestMapping(value = "writeChange", method = RequestMethod.POST)
	public String writeChange(BoardVO boardvo, HttpServletRequest request, Model model) throws Exception {
		int mode = Integer.parseInt(request.getParameter("mode"));
		logger.info("uc - mode : " + mode);
		if(mode == 1) {
			boardvo.setContent(service.quotParser(boardvo.getContent()));
			String prevStr = service.totalParser(boardvo.getContent());
			model.addAttribute("mode", "prev");
			model.addAttribute("prevStr", prevStr);
		}
		model.addAttribute("boardVO", boardvo);
		return "board/write";
	}
	
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public void content(HttpServletRequest request,Model model) throws Exception{
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		BoardVO boardvo = service.getBoard(boardNo);
		boardvo.setContent(service.totalParser(boardvo.getContent()));
		logger.info("content - " + boardvo.getContent());
		model.addAttribute("boardVO", boardvo);

		// 추천,비추천 읽어오기
		model.addAttribute("hit_cnt", service.getBoard_Hit(boardNo));
		model.addAttribute("nonhit_cnt", service.getBoard_nonHit(boardNo));
		
		//----------------------------------------------------------
		//추가: 댓글 출력
		logger.info("댓글 출력 start");
		model.addAttribute("crr_ip", service.getUserIP(request));
		int count = re_service.replyCount(boardNo);
		model.addAttribute("count",count);
		int pageNum = Integer.parseInt(request.getParameter("replyPage"));
		try{
			int pagestart = 1+(5*(pageNum-1));
			ReplyVO pgvo = new ReplyVO();
			pgvo.setR_boardNo(boardNo);
			pgvo.setPageStart(pagestart);
			List<ReplyVO> replyList = re_service.replySelect(pgvo);
			for(ReplyVO rvo2 : replyList){
				logger.info("검색결과 : " + rvo2.toString());
				rvo2.setR_content(service.replyParser(rvo2.getR_content()));
			}
			model.addAttribute("replyList",replyList);
		}catch(Exception e){
			logger.info("검색결과 : 없음" );
			e.printStackTrace();
		}
		logger.info("댓글 출력 end");
		//----------------------------------------------------------
	}

	//추가:백업보드리스트 글가져오기
	@RequestMapping(value="/bkcontent", method = RequestMethod.GET)
	public String bkcontent(HttpServletRequest request,Model model) throws Exception{
		//리스트에서 글 선택시
		int backupNo = Integer.parseInt(request.getParameter("backupNo"));
		logger.info("backupNo : "+ backupNo);
		
		//---------------------------------------------------------------------------
		//수정 : backupno 사용을 위해 수정
		BackupBoardVO bkvo = service.getBackupBoard(backupNo);
		bkvo.setContent(service.totalParser(bkvo.getContent()));
		logger.info("bkvo : "+ bkvo);
		
		//추가 : hit_table에서 backupNo가 같은 데이터의 hit, nonhit 출력
		model.addAttribute("hit_cnt", service.getBackup_Hit(backupNo));
		logger.info("getBackup_Hit : "+ service.getBackup_Hit(backupNo));

		model.addAttribute("nonhit_cnt", service.getBackup_nonHit(backupNo));
		logger.info("getBackup_nonHit : "+ service.getBackup_nonHit(backupNo));
		
		
		//추가: 백업보드에서는 댓글작성 버튼 감추기 (0 이면 감추기)
		model.addAttribute("re_hidden","hidden reply");
		model.addAttribute("backupNo",backupNo);
		//---------------------------------------------------------------------------
		model.addAttribute(new BoardVO(bkvo));
		model.addAttribute("msg", "History");
		
		return "board/content";
	}
	
	//추가:history
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public void history(HttpServletRequest request,Model model) throws Exception{
		/* backupBoard 리스트로 받기 */
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		//BoardVO boardvo = service.getBoard(boardNo);
		logger.info("boardNo="+boardNo);
		List<BackupBoardVO> backupboardList = service.getBackupList(boardNo);
		logger.info("list size="+backupboardList.size());
		for(BackupBoardVO vo : backupboardList) {
			logger.info("검색결과 : " + vo.toString());
		}
		if(backupboardList.size() != 0)
			model.addAttribute("backupList", backupboardList);
		String title = service.getBoard(boardNo).getTitle();
		model.addAttribute("title", title);

	}
	//추가	
	@RequestMapping(value = "/rollbackAction", method = RequestMethod.GET)
	public String rollbackAction(HttpServletRequest request,Model model) throws Exception{
		
		logger.info("롤백 시작");
		int backupNo = Integer.parseInt(request.getParameter("backupNo"));
		
		BackupBoardVO backupvo = service.getBackupBoard(backupNo);
		logger.info("backupvo="+backupvo);
		//최신글 백업보드에 저장
		int boardNo = backupvo.getBoardNo();	
		backupvo.setPurpose("롤백");
		service.writeBackupBoard(backupvo);

		// 추가 : 현재글의 hit정보 backupbaord에 저장
		List<BackupBoardVO> backupInfo2 = service.getBackupList(backupvo.getBoardNo());
		service.setBackupHit(backupInfo2.get(0));
		logger.info("백업에 저장 완료 - " + backupvo);
		
		//백업보드에서 선택한 글을 보드에 업데이트
		
		service.rollback(backupvo);
		logger.info("본문수정 완료");
		
		// 추가 : 롤백된 글의 hit 정보 가져오기 -> hit_table의 h_backupNo = 0 으로 변경
		service.rollbackHit(backupNo);
		logger.info("hit정보 수정 완료");
		
		model.addAttribute("boardNo", boardNo);

		return "redirect:content?boardNo=" + boardNo + "&replyPage=1";
	}
	
	@RequestMapping(value = "/rollback", method = RequestMethod.GET)
	public String rollback(HttpServletRequest request,Model model) throws Exception{
		int backupNo = Integer.parseInt(request.getParameter("backupNo"));
		BackupBoardVO backupvo = service.getBackupBoard(backupNo);
		model.addAttribute("boardNo", backupvo.getBoardNo());
		model.addAttribute("backupNo", backupNo);
		return "board/history_result";
	}
	
	//Update.jsp Get 파라미터 받는 부분
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public void update(HttpServletRequest request,Model model) throws Exception{
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		model.addAttribute("boardVO", service.getBoard(boardNo));
	}
	
	//추가
	//1.원본 백업보드테이블에 저장
	//2.보드테이블에있는 글 수정.
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateAction(HttpServletRequest request,BoardVO vo,BackupBoardVO backvo) throws Exception {
		logger.info("updateAction - " + vo);
		String ip = service.getUserIP(request);
		if(service.blockIPCheck(ip) != 0) {
			request.setAttribute("msg", "블락된 IP입니다.");
			return "main";
		}
		BackupBoardVO backupInfo = backvo;
		
		service.writeBackupBoard(backupInfo);
		logger.info("updateAction - backupInfo : " + backupInfo);

		BoardVO boardInfo = vo;
		
		//--------------------------------------------------------------------
		//추가 : purpose == 정보수정 일 경우만 히트테이블에 백업보드번호 저장
		//추가 : 수정하려는 board의 hit/nonhit 값을 backupboard에 저장 
		//백업보드no를 이용하여 히트 테이블에 백업보드번호 저장
		if(backupInfo.getPurpose().equals("정보수정")){
			List<BackupBoardVO> backupInfo2 = service.getBackupList(boardInfo.getBoardNo());
			service.setBackupHit(backupInfo2.get(0));
		}
		//--------------------------------------------------------------------
		
		try {
			MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
			vo.setMemberId(userinfo.getMemberid());
		} catch(Exception e) {}
		boardInfo.setIp(ip);

		boardInfo.setContent(service.quotParser(boardInfo.getContent()));
		service.updateBoard(boardInfo); 
		logger.info("boardInfo(after) : " + boardInfo);
		
		request.setAttribute("boardVO", service.getBoard(boardInfo.getBoardNo()));
		
		return "redirect:content?boardNo=" + vo.getBoardNo() + "&replyPage=1";
	}
	
	@RequestMapping(value = "updateChange", method = RequestMethod.POST)
	public String updateChange(BoardVO boardvo, HttpServletRequest request, Model model) throws Exception {
		int mode = Integer.parseInt(request.getParameter("mode"));
		logger.info("uc - mode : " + mode);
		if(mode == 1) {
			boardvo.setContent(service.quotParser(boardvo.getContent()));
			String prevStr = service.totalParser(boardvo.getContent());
			model.addAttribute("mode", "prev");
			model.addAttribute("prevStr", prevStr);
		}
		logger.info("uc - content : " + boardvo.getContent());
		model.addAttribute("boardVO", boardvo);
		return "board/update";
	}
	
	// 추천 == 1, 비추천  == 0
	// *Hit1 메소드 == 회원
	// *Hit2 메소드 == 비회원
	@RequestMapping(value="/hit", method = RequestMethod.POST)
	public String hitAction(HttpServletRequest request, Model model) throws Exception{
		logger.info("hit start");
		
		// 추천버튼 = 1, 비추천 버튼 = 0
		int hit_val = Integer.parseInt(request.getParameter("hit_val"));
		logger.info("hit_val : " + hit_val);
		
		// 추천 버튼을 누른 게시판의 글 번호
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		logger.info("boardNo : " + boardNo);
		
		//--------------------------------------------------------------------
		//추가: backupboard 글 번호 
		int backupNo;
		try{
			backupNo = Integer.parseInt(request.getParameter("backupNo"));
		}catch (Exception e) {
			backupNo = 0;
		}
		logger.info("backupNo : " + backupNo);
		//--------------------------------------------------------------------
		
		// 현재 접속 IP를 저장
		String ip = service.getUserIP(request);
		logger.info("ip : " + ip);
		
		// memberId 정보를 구하기 위해 session의 user정보를 불러옴
		MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
		String memberId = "";
		
		HitVO hvo = new HitVO();
		
		hvo.setH_boardNO(boardNo);
		hvo.setH_backupNo(backupNo); // 추가  : backupboard 글번호 set
		hvo.setH_IP(ip);
		hvo.setHit(hit_val);
		
		boolean tmp = false;
		
		try{ // 로그인 상태 일 경우 현재 memberid를 저장
			memberId = userinfo.getMemberid();
			tmp = true;
		}catch (Exception e) { // 비회원일 경우 memberId에 null을 저장
			memberId = null;
			tmp = false;
		}
		
		hvo.setH_memberID(memberId);
		logger.info("memberId : " + memberId);
		
		logger.info("hvo : " + hvo);
		HitVO check_hvo = hvo;
		
		// 1) hit_table에 현재 사용자가  추천/비추천 을 누른적이 있는지 확인 
		try{ 
			logger.info("hit_step 1 , tmp => " + tmp);
			if(tmp){
				check_hvo = service.checkHit1(hvo);
				logger.info("checkHit1(memberID O)" + check_hvo);
			}
			else{
				check_hvo = service.checkHit2(hvo);
				logger.info("checkHit2(memberID X)" + check_hvo);
			}
			
			// 추천/비추천을 누른적이 있는 경우 HitVO 정보 출력
			// 없는 경우 강제 Exception 발생 -> catch로 이동
			//logger.info("check_hvo : " + check_hvo);
			if(check_hvo == null){
				throw new Exception(); //강제 에러 발생
			}

			
			// 2) 누른적이 있는 경우
			// 2-1) 같은 버튼을 눌렀을 경우 -> delete
			if(check_hvo.getHit() == hit_val){
				logger.info("hit_step 2-1 delete start");
				if(tmp)	service.deleteHit1(hvo);
				else 	service.deleteHit2(hvo);
				logger.info("hit_step 2-1 delete end");
				
				if(hit_val == 1) model.addAttribute("msg","추천 취소");
				else			 model.addAttribute("msg","비추천 취소");
			}
			
			// 2-2) 다른 버튼을 눌렀을 경우 -> 메세지 출력
			else{
				logger.info("hit_step 2-2");
				model.addAttribute("msg","이미 추천/비추천 하였습니다.");
			}
			
		} // 3) 누른적이 없는 경우 -> hit_val 값을 hit_table에 삽입
		catch (Exception e) {
			logger.info("hit_step 3 insert start");
			service.insertHit(hvo);
			logger.info("hit_step 3 insert end");
			if(hit_val == 1) model.addAttribute("msg","추천");
			else			 model.addAttribute("msg","비추천");
		}
		
		model.addAttribute("boardNo",boardNo);
		
		return "board/hit_result";
		
	}
	
	@RequestMapping(value = "/historyOpen", method = RequestMethod.GET)
	public String backupSearch(Model model, HttpServletRequest request) throws Exception {
		logger.info("backupSearch");
		String searchStr = (String)request.getParameter("bSearch_str");
		logger.info("searchStr : " + searchStr);
		model.addAttribute("search", searchStr);
		
		List<BackupBoardVO> backupBoardList = service.searchBackupBoard(searchStr);
		for(BackupBoardVO bvo : backupBoardList) {
			logger.info("검색결과 : " + bvo.toString());
		}
		model.addAttribute("backupList", backupBoardList);
		return "board/backup_list";
	}
	
}
