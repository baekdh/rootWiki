package com.root.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.root.domain.MemberVO;
import com.root.persistence.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	private MemberDAO dao;

	@Override
	public void insertMember(MemberVO vo) throws Exception {
		dao.insertMember(vo);
	}

	@Override
	public MemberVO getMember(MemberVO vo) throws Exception {
		return dao.getMember(vo);
	}

	@Override
	public int idCheck(String memberid) throws Exception {
		return dao.idCheck(memberid);
	}
	
	@Override
	public int adminCheck(String memberid) throws Exception {
		return dao.adminCheck(memberid);
	}

	@Override
	public int withdrawMember(MemberVO userinfo) throws Exception {
		return dao.withdrawMember(userinfo);
	}

	@Override
	public void blockMember(String memberid) throws Exception {
		dao.blockMember(memberid);
	}
	
	@Override
	public void blockIp(String ip) throws Exception {
		dao.blockIp(ip);
	}

	@Override
	public int blockIDCheck(String memberid) throws Exception {
		return dao.blockIDCheck(memberid);
	}

}
