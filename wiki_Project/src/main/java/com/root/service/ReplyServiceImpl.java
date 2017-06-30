package com.root.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.root.domain.ReplyVO;
import com.root.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	private ReplyDAO dao;
	
	@Override
	public void replyInsert(ReplyVO rvo) throws Exception{
		dao.replyInsert(rvo);
	}

	@Override
	public List<ReplyVO> replySelect(ReplyVO vo) throws Exception {
		System.out.println("ReplyServieImpl"+vo);
		return dao.replySelect(vo);
	}
	
	@Override
	public int replyCount(int boardNo) throws Exception {
		return dao.replyCount(boardNo);
	}

	@Override
	public void replyDelete(int r_no) throws Exception {
		dao.replyDelete(r_no);
	}

	@Override
	public ReplyVO replyUpdateWindow(int r_no) throws Exception {
		return dao.replyUpdateWindow(r_no);
	}

	@Override
	public void replyUpdate(ReplyVO rvo) throws Exception {
		dao.replyUpdate(rvo);
		
	}
	
	
	
	
}
