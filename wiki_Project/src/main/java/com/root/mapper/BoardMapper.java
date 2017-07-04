package com.root.mapper;

import java.util.List;

import com.root.domain.BackupBoardVO;
import com.root.domain.BoardVO;
import com.root.domain.HitVO;

public interface BoardMapper {

	List<BoardVO> searchBoard(String search);
	
	List<BackupBoardVO> searchBackupBoard(String search);
	
	void writeBoard(BoardVO vo);
	
	//�߰� : ����üũ
	int titleCheck(String title);

	BoardVO getBoard(int boardNo);
	//�߰�
	List<BackupBoardVO> getBackupList(int boardNo);	//����+������ ����Ʈ�� �޾ƿ���
	//�ѹ�
	int rollback(BackupBoardVO vo);
	//������� ��������
	BackupBoardVO getBackupBoard(int backupNo);
	//�߰�&����
	void writeBackupBoard(BackupBoardVO vo);		//�ۼ����� ������ BackupBoard���̺� ����.
	
	int updateBoard(BoardVO vo);					//update
	
	int getBoard_Hit(int boardNo);

	int getBoard_nonHit(int boardNo);
	
	//�߰� -------------------------------------------
	void setBackupHit(BackupBoardVO bkvo); 
	
	int getBackup_Hit(int backupNo);
	
	int getBackup_nonHit(int backupNo);
	
	HitVO checkHit1(HitVO vo);
	
	HitVO checkHit2(HitVO vo);
	
	void insertHit(HitVO vo);
	
	void deleteHit1(HitVO vo);
	
	void deleteHit2(HitVO vo);
	
	int blockIPCheck(String ip);
	
	//�߰� 
	void rollbackHit(int backupNo);
}
