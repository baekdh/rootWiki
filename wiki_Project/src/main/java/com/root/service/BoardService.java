package com.root.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.root.domain.BackupBoardVO;
import com.root.domain.BoardVO;
import com.root.domain.HitVO;

public interface BoardService {

	public List<BoardVO> searchBoard(String search) throws Exception;
	
	public List<BackupBoardVO> searchBackupBoard(String search) throws Exception;
	
	public void writeBoard(BoardVO vo) throws Exception;
	
	//추가 : 제목 중복체크
	public int titleCheck(String title) throws Exception;
	
	public BoardVO getBoard(int boardNo) throws Exception;
	
	public void writeBackupBoard(BackupBoardVO vo) throws Exception;

	public int updateBoard(BoardVO vo) throws Exception;

	public List<BackupBoardVO> getBackupList(int boardNo) throws Exception;
	
	public int getBoard_Hit(int boardNo) throws Exception;

	public int getBoard_nonHit(int boardNo) throws Exception;
	
	//추가:백업리스트 글선택시 보드로 보여주기
	public BoardVO getBackupContent(int backupNo) throws Exception;
	//롤백
	public int rollback(BackupBoardVO vo) throws Exception;
	//백업보드 가져오기
	public BackupBoardVO getBackupBoard(int backupNo) throws Exception;
	//--------------------------------------------------------------
	//??:?? ???? ?????? ??
	public void setBackupHit(BackupBoardVO bkvo) throws Exception;
	//???? ??
	public int getBackup_Hit(int backupNo) throws Exception;
	public int getBackup_nonHit(int backupNo) throws Exception;
	//--------------------------------------------------------------
	
	public HitVO checkHit1(HitVO vo) throws Exception;
	
	public HitVO checkHit2(HitVO vo) throws Exception;
	
	public void insertHit(HitVO vo) throws Exception;
	
	public void deleteHit1(HitVO vo) throws Exception;
	
	public void deleteHit2(HitVO vo) throws Exception;
	
	public int blockIPCheck(String ip) throws Exception;

	public void rollbackHit(int backupNo) throws Exception;
	
	public String totalParser(String content);
	public StringBuffer tagParser(StringBuffer sb);
	public String quotParser(String sb);
	public String replyParser(String content);
	public String getUserIP(HttpServletRequest request);
	
}
