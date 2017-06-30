package com.root.service;

import java.util.List;

import com.root.domain.ReplyVO;

public interface ReplyService {
	
	public void replyInsert(ReplyVO rvo) throws Exception;

	public List<ReplyVO> replySelect(ReplyVO vo) throws Exception;

	public int replyCount(int r_boardNo) throws Exception;
	
	public void replyDelete(int r_no) throws Exception;
	
	public ReplyVO replyUpdateWindow(int r_no) throws Exception;
	
	public void replyUpdate(ReplyVO rvo) throws Exception;
	
}
