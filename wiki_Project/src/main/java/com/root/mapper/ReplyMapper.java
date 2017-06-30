package com.root.mapper;

import java.util.List;

import com.root.domain.ReplyVO;

public interface ReplyMapper {
	
	void replyInsert(ReplyVO rvo);
	
	List<ReplyVO> replySelect(ReplyVO vo);

	int replyCount(int boardNo);
	
	void replyDelete(int r_no);
		
	ReplyVO replyUpdateWindow(int r_no);
	
	void replyUpdate(ReplyVO rvo);
}
