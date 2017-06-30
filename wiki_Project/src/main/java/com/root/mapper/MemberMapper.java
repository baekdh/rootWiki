package com.root.mapper;

import com.root.domain.MemberVO;

public interface MemberMapper {
	
	void insertMember(MemberVO vo);
	
	MemberVO getMember(MemberVO vo);
	
	int idCheck(String memberid);
	
	int adminCheck(String memberid);
	
	int withdrawMember(MemberVO userinfo);
	
	void blockMember(String memberid);
	
	void blockIp(String ip);
	
	int blockIDCheck(String memberid);
}
