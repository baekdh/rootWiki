package com.root.mapper;

import java.util.List;

import com.root.domain.BackupBoardVO;
import com.root.domain.BoardVO;
import com.root.domain.HitVO;

public interface BoardMapper {

	List<BoardVO> searchBoard(String search);
	
	List<BackupBoardVO> searchBackupBoard(String search);
	
	void writeBoard(BoardVO vo);
	
	//추가 : 제목체크
	int titleCheck(String title);

	BoardVO getBoard(int boardNo);
	//추가
	List<BackupBoardVO> getBackupList(int boardNo);	//원본+수정본 리스트로 받아오기
	//롤백
	int rollback(BackupBoardVO vo);
	//백업보드 가져오기
	BackupBoardVO getBackupBoard(int backupNo);
	//추가&수정
	void writeBackupBoard(BackupBoardVO vo);		//글수정시 원본을 BackupBoard테이블에 저장.
	
	int updateBoard(BoardVO vo);					//update
	
	int getBoard_Hit(int boardNo);

	int getBoard_nonHit(int boardNo);
	
	//추가 -------------------------------------------
	void setBackupHit(BackupBoardVO bkvo); 
	
	int getBackup_Hit(int backupNo);
	
	int getBackup_nonHit(int backupNo);
	
	HitVO checkHit1(HitVO vo);
	
	HitVO checkHit2(HitVO vo);
	
	void insertHit(HitVO vo);
	
	void deleteHit1(HitVO vo);
	
	void deleteHit2(HitVO vo);
	
	int blockIPCheck(String ip);
	
	//추가 
	void rollbackHit(int backupNo);
}
