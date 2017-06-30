package com.root.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.root.domain.BackupBoardVO;
import com.root.domain.BoardVO;
import com.root.domain.HitVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace = "com.root.mapper.BoardMapper";

	@Override
	public List<BoardVO> searchBoard(String search) throws Exception {
		return sqlSession.selectList(namespace + ".searchBoard", search);
	}
	
	@Override
	public List<BackupBoardVO> searchBackupBoard(String search) throws Exception {
		return sqlSession.selectList(namespace + ".searchBackupBoard", search);
	}

	@Override
	public void writeBoard(BoardVO vo) throws Exception {
		sqlSession.insert(namespace + ".writeBoard", vo);
	}

	@Override
	public BoardVO getBoard(int boardNo) throws Exception {
		return sqlSession.selectOne(namespace + ".getBoard", boardNo);
	}

	@Override
	public void writeBackupBoard(BackupBoardVO vo) throws Exception {
		//������ backupboard���̺� ����
		sqlSession.insert(namespace + ".writeBackupBoard", vo);
	}
	
	@Override
	public int titleCheck(String title) {
		return sqlSession.selectOne(namespace+".titleCheck",title);
	}

	@Override
	public int updateBoard(BoardVO vo) throws Exception {
		//����
		return sqlSession.update(namespace + ".updateBoard", vo);
	}

	@Override
	public List<BackupBoardVO> getBackupList(int boardNo) throws Exception {
		return sqlSession.selectList(namespace+".getBackupList", boardNo);
	}


	//�߰�:�������Ʈ �ۼ��ý� ����� �����ֱ�
	@Override
	public BoardVO getBackupContent(int backupNo) {
		return sqlSession.selectOne(namespace+".getBackupContent",backupNo);
	}

	//�ѹ�
	@Override
	public int rollback(BackupBoardVO vo) throws Exception {
		return sqlSession.update(namespace+".rollback",vo);
	}

	//������尡������
	@Override
	public BackupBoardVO getBackupBoard(int backupNo) throws Exception {
		return sqlSession.selectOne(namespace+".getBackupBoard",backupNo);
	}
	@Override
	public int getBoard_Hit(int boardNo) throws Exception {
		return sqlSession.selectOne(namespace + ".getBoard_Hit", boardNo);
	}

	@Override
	public int getBoard_nonHit(int boardNo) throws Exception {
		return sqlSession.selectOne(namespace + ".getBoard_nonHit", boardNo);
	}

	@Override
	public HitVO checkHit1(HitVO vo) throws Exception {
		return sqlSession.selectOne(namespace + ".checkHit1", vo);
	}
	
	@Override
	public HitVO checkHit2(HitVO vo) throws Exception {
		return sqlSession.selectOne(namespace + ".checkHit2", vo);
	}

	@Override
	public void insertHit(HitVO vo) throws Exception {
		sqlSession.insert(namespace + ".insertHit", vo);
	}

	@Override
	public void deleteHit1(HitVO vo) throws Exception {
		sqlSession.delete(namespace + ".deleteHit1", vo);
	}
	
	@Override
	public void deleteHit2(HitVO vo) throws Exception {
		sqlSession.delete(namespace + ".deleteHit2", vo);
	}

	// �߰� -----------------------------------------------------
	@Override
	public void setBackupHit(BackupBoardVO bkvo) throws Exception {
		sqlSession.insert(namespace + ".setBackupHit", bkvo);
	}

	@Override
	public int getBackup_Hit(int backupNo) throws Exception {
		return sqlSession.selectOne(namespace + ".getBackup_Hit", backupNo);
	}

	@Override
	public int getBackup_nonHit(int backupNo) throws Exception {
		return sqlSession.selectOne(namespace + ".getBackup_nonHit", backupNo);
	}
	// --------------------------------------------------------------
	
	@Override
	public int blockIPCheck(String ip) throws Exception {
		return sqlSession.selectOne(namespace + ".blockIPCheck", ip);
	}

	//�߰�
	@Override
	public void rollbackHit(int backupNo) throws Exception {
		sqlSession.update(namespace + ".rollbackHit", backupNo);
	}

	
	
	
	
	

}
