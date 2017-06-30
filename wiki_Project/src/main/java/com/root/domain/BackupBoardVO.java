package com.root.domain;

import java.util.Date;

public class BackupBoardVO {
	private int backupNo;
	private String purpose;
	private String title;
	private String content;
	private String ip;
	private Date regdate;
	private String memberId;
	private int boardNo;
	public int getBackupNo() {
		return backupNo;
	}
	public void setBackupNo(int backupNo) {
		this.backupNo = backupNo;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	@Override
	public String toString() {
		return "BackupBoardVO [backupNo=" + backupNo + ", purpose=" + purpose + ", title=" + title + ", content="
				+ content + ", ip=" + ip + ", regdate=" + regdate + ", memberId=" + memberId + ", boardNo=" + boardNo
				+ "]";
	}

	
}



