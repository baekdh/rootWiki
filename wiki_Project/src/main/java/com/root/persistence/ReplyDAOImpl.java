package com.root.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.root.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	@Inject
	private SqlSession session;
	private static String namespace = "com.root.mapper.ReplyMapper";
	
	@Override
	public void replyInsert(ReplyVO rvo) throws Exception{
		session.insert(namespace + ".replyInsert", rvo);
	}

	@Override
	public List<ReplyVO> replySelect(ReplyVO vo) throws Exception {
		return session.selectList(namespace + ".replySelect",vo);
	}
	
	@Override
	public int replyCount(int boardNo) throws Exception {
		return session.selectOne(namespace+".replyCount",boardNo);
	}

	@Override
	public void replyDelete(int r_no) throws Exception {
		session.delete(namespace + ".replyDelete",r_no);
	}

	@Override
	public ReplyVO replyUpdateWindow(int r_no) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".replyUpdateWindow",r_no);
	}

	@Override
	public void replyUpdate(ReplyVO rvo) throws Exception {
		session.update(namespace + ".replyUpdate",rvo);
	}
	
	
	
}
