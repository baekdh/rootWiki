package com.root.persistence;

import com.root.domain.MemberVO;

public interface MemberDAO {

	public void insertMember(MemberVO vo) throws Exception;
	
	public MemberVO getMember(MemberVO vo) throws Exception;
	
	public int idCheck(String memberid) throws Exception;
	
	public int adminCheck(String memberid) throws Exception;

	public int withdrawMember(MemberVO userinfo) throws Exception;
	
	public void blockMember(String memberid) throws Exception;
	
	public void blockIp(String ip) throws Exception;
	
	public int blockIDCheck(String memberid) throws Exception;
	
}
