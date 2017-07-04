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
	
	//�߰�: ReplyService inject
	@Inject
	private ReplyService re_service;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, HttpServletRequest request) throws Exception {
		logger.info("search");
		String ip = service.getUserIP(request);
		if(service.blockIPCheck(ip) != 0) {
			request.setAttribute("msg", "����� IP�Դϴ�.");
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
			request.setAttribute("msg", "����� IP�Դϴ�.");
			return "main";
		}
		
		//�߰�:���� �ߺ�üũ
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

		// ��õ,����õ �о����
		model.addAttribute("hit_cnt", service.getBoard_Hit(boardNo));
		model.addAttribute("nonhit_cnt", service.getBoard_nonHit(boardNo));
		
		//----------------------------------------------------------
		//�߰�: ��� ���
		logger.info("��� ��� start");
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
				logger.info("�˻���� : " + rvo2.toString());
				rvo2.setR_content(service.replyParser(rvo2.getR_content()));
			}
			model.addAttribute("replyList",replyList);
		}catch(Exception e){
			logger.info("�˻���� : ����" );
			e.printStackTrace();
		}
		logger.info("��� ��� end");
		//----------------------------------------------------------
	}

	//�߰�:������帮��Ʈ �۰�������
	@RequestMapping(value="/bkcontent", method = RequestMethod.GET)
	public String bkcontent(HttpServletRequest request,Model model) throws Exception{
		//����Ʈ���� �� ���ý�
		int backupNo = Integer.parseInt(request.getParameter("backupNo"));
		logger.info("backupNo : "+ backupNo);
		
		//---------------------------------------------------------------------------
		//���� : backupno ����� ���� ����
		BackupBoardVO bkvo = service.getBackupBoard(backupNo);
		bkvo.setContent(service.totalParser(bkvo.getContent()));
		logger.info("bkvo : "+ bkvo);
		
		//�߰� : hit_table���� backupNo�� ���� �������� hit, nonhit ���
		model.addAttribute("hit_cnt", service.getBackup_Hit(backupNo));
		logger.info("getBackup_Hit : "+ service.getBackup_Hit(backupNo));

		model.addAttribute("nonhit_cnt", service.getBackup_nonHit(backupNo));
		logger.info("getBackup_nonHit : "+ service.getBackup_nonHit(backupNo));
		
		
		//�߰�: ������忡���� ����ۼ� ��ư ���߱� (0 �̸� ���߱�)
		model.addAttribute("re_hidden","hidden reply");
		model.addAttribute("backupNo",backupNo);
		//---------------------------------------------------------------------------
		model.addAttribute(new BoardVO(bkvo));
		model.addAttribute("msg", "History");
		
		return "board/content";
	}
	
	//�߰�:history
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public void history(HttpServletRequest request,Model model) throws Exception{
		/* backupBoard ����Ʈ�� �ޱ� */
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		//BoardVO boardvo = service.getBoard(boardNo);
		logger.info("boardNo="+boardNo);
		List<BackupBoardVO> backupboardList = service.getBackupList(boardNo);
		logger.info("list size="+backupboardList.size());
		for(BackupBoardVO vo : backupboardList) {
			logger.info("�˻���� : " + vo.toString());
		}
		if(backupboardList.size() != 0)
			model.addAttribute("backupList", backupboardList);
		String title = service.getBoard(boardNo).getTitle();
		model.addAttribute("title", title);

	}
	//�߰�	
	@RequestMapping(value = "/rollbackAction", method = RequestMethod.GET)
	public String rollbackAction(HttpServletRequest request,Model model) throws Exception{
		
		logger.info("�ѹ� ����");
		int backupNo = Integer.parseInt(request.getParameter("backupNo"));
		
		BackupBoardVO backupvo = service.getBackupBoard(backupNo);
		logger.info("backupvo="+backupvo);
		//�ֽű� ������忡 ����
		int boardNo = backupvo.getBoardNo();	
		backupvo.setPurpose("�ѹ�");
		service.writeBackupBoard(backupvo);

		// �߰� : ������� hit���� backupbaord�� ����
		List<BackupBoardVO> backupInfo2 = service.getBackupList(backupvo.getBoardNo());
		service.setBackupHit(backupInfo2.get(0));
		logger.info("����� ���� �Ϸ� - " + backupvo);
		
		//������忡�� ������ ���� ���忡 ������Ʈ
		
		service.rollback(backupvo);
		logger.info("�������� �Ϸ�");
		
		// �߰� : �ѹ�� ���� hit ���� �������� -> hit_table�� h_backupNo = 0 ���� ����
		service.rollbackHit(backupNo);
		logger.info("hit���� ���� �Ϸ�");
		
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
	
	//Update.jsp Get �Ķ���� �޴� �κ�
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public void update(HttpServletRequest request,Model model) throws Exception{
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		model.addAttribute("boardVO", service.getBoard(boardNo));
	}
	
	//�߰�
	//1.���� ����������̺� ����
	//2.�������̺��ִ� �� ����.
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateAction(HttpServletRequest request,BoardVO vo,BackupBoardVO backvo) throws Exception {
		logger.info("updateAction - " + vo);
		String ip = service.getUserIP(request);
		if(service.blockIPCheck(ip) != 0) {
			request.setAttribute("msg", "����� IP�Դϴ�.");
			return "main";
		}
		BackupBoardVO backupInfo = backvo;
		
		service.writeBackupBoard(backupInfo);
		logger.info("updateAction - backupInfo : " + backupInfo);

		BoardVO boardInfo = vo;
		
		//--------------------------------------------------------------------
		//�߰� : purpose == �������� �� ��츸 ��Ʈ���̺� ��������ȣ ����
		//�߰� : �����Ϸ��� board�� hit/nonhit ���� backupboard�� ���� 
		//�������no�� �̿��Ͽ� ��Ʈ ���̺� ��������ȣ ����
		if(backupInfo.getPurpose().equals("��������")){
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
	
	// ��õ == 1, ����õ  == 0
	// *Hit1 �޼ҵ� == ȸ��
	// *Hit2 �޼ҵ� == ��ȸ��
	@RequestMapping(value="/hit", method = RequestMethod.POST)
	public String hitAction(HttpServletRequest request, Model model) throws Exception{
		logger.info("hit start");
		
		// ��õ��ư = 1, ����õ ��ư = 0
		int hit_val = Integer.parseInt(request.getParameter("hit_val"));
		logger.info("hit_val : " + hit_val);
		
		// ��õ ��ư�� ���� �Խ����� �� ��ȣ
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		logger.info("boardNo : " + boardNo);
		
		//--------------------------------------------------------------------
		//�߰�: backupboard �� ��ȣ 
		int backupNo;
		try{
			backupNo = Integer.parseInt(request.getParameter("backupNo"));
		}catch (Exception e) {
			backupNo = 0;
		}
		logger.info("backupNo : " + backupNo);
		//--------------------------------------------------------------------
		
		// ���� ���� IP�� ����
		String ip = service.getUserIP(request);
		logger.info("ip : " + ip);
		
		// memberId ������ ���ϱ� ���� session�� user������ �ҷ���
		MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
		String memberId = "";
		
		HitVO hvo = new HitVO();
		
		hvo.setH_boardNO(boardNo);
		hvo.setH_backupNo(backupNo); // �߰�  : backupboard �۹�ȣ set
		hvo.setH_IP(ip);
		hvo.setHit(hit_val);
		
		boolean tmp = false;
		
		try{ // �α��� ���� �� ��� ���� memberid�� ����
			memberId = userinfo.getMemberid();
			tmp = true;
		}catch (Exception e) { // ��ȸ���� ��� memberId�� null�� ����
			memberId = null;
			tmp = false;
		}
		
		hvo.setH_memberID(memberId);
		logger.info("memberId : " + memberId);
		
		logger.info("hvo : " + hvo);
		HitVO check_hvo = hvo;
		
		// 1) hit_table�� ���� ����ڰ�  ��õ/����õ �� �������� �ִ��� Ȯ�� 
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
			
			// ��õ/����õ�� �������� �ִ� ��� HitVO ���� ���
			// ���� ��� ���� Exception �߻� -> catch�� �̵�
			//logger.info("check_hvo : " + check_hvo);
			if(check_hvo == null){
				throw new Exception(); //���� ���� �߻�
			}

			
			// 2) �������� �ִ� ���
			// 2-1) ���� ��ư�� ������ ��� -> delete
			if(check_hvo.getHit() == hit_val){
				logger.info("hit_step 2-1 delete start");
				if(tmp)	service.deleteHit1(hvo);
				else 	service.deleteHit2(hvo);
				logger.info("hit_step 2-1 delete end");
				
				if(hit_val == 1) model.addAttribute("msg","��õ ���");
				else			 model.addAttribute("msg","����õ ���");
			}
			
			// 2-2) �ٸ� ��ư�� ������ ��� -> �޼��� ���
			else{
				logger.info("hit_step 2-2");
				model.addAttribute("msg","�̹� ��õ/����õ �Ͽ����ϴ�.");
			}
			
		} // 3) �������� ���� ��� -> hit_val ���� hit_table�� ����
		catch (Exception e) {
			logger.info("hit_step 3 insert start");
			service.insertHit(hvo);
			logger.info("hit_step 3 insert end");
			if(hit_val == 1) model.addAttribute("msg","��õ");
			else			 model.addAttribute("msg","����õ");
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
			logger.info("�˻���� : " + bvo.toString());
		}
		model.addAttribute("backupList", backupBoardList);
		return "board/backup_list";
	}
	
}
