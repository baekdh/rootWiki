package com.root.domain;

import java.util.Date;

public class BoardVO {
	private int boardNo;
	private String title;
	private String content;
	private String ip;
	private Date regdate;
	private String memberId;
	
	public BoardVO() {}
	
	public BoardVO(BackupBoardVO bvo) {
		this.boardNo = bvo.getBoardNo();
		this.title = bvo.getTitle();
		this.content = bvo.getContent();
		this.ip = bvo.getIp();
		this.regdate = bvo.getRegdate();
		this.memberId = bvo.getMemberId();
	}
	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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
	@Override
	public String toString() {
		return "BoardVO [boardNo=" + boardNo + ", title=" + title + ", content=" + content
				+ ", ip=" + ip + ", regdate=" + regdate + ", memberId=" + memberId + "]";
	}
}
