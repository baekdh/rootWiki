package com.root.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.root.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace = "com.root.mapper.MemberMapper";
	
	@Override
	public void insertMember(MemberVO vo) throws Exception {
		sqlSession.insert(namespace + ".insertMember", vo);
	}

	@Override
	public MemberVO getMember(MemberVO vo) throws Exception {
		MemberVO memberinfo = sqlSession.selectOne(namespace + ".getMember", vo);
		
		return memberinfo;
	}

	@Override
	public int idCheck(String memberid) throws Exception {
		return sqlSession.selectOne(namespace + ".idCheck", memberid);
	}
	
	@Override
	public int adminCheck(String memberid) throws Exception {
		return sqlSession.selectOne(namespace + ".adminCheck", memberid);
	}

	@Override
	public int withdrawMember(MemberVO userinfo) {
		return sqlSession.delete(namespace + ".withdraw", userinfo.getMemberid());
	}

	@Override
	public void blockMember(String memberid) throws Exception {
		sqlSession.insert(namespace + ".blockMember", memberid);
	}
	
	@Override
	public void blockIp(String ip) throws Exception {
		sqlSession.insert(namespace + ".blockIp", ip);
	}

	@Override
	public int blockIDCheck(String memberid) throws Exception {
		return sqlSession.selectOne(namespace + ".blockIDCheck", memberid);
	}

}
