package com.root.domain;

import java.sql.Date;

/* 댓글번호(pk)
 * 댓글이 달린 글 번호(보드 or 백업보드)
 * 작성자
 * id
 * ip
 * 작성일
 * 내용
 * 댓글 깊이(depth)
 * */


public class ReplyVO {
	private int r_no;
	private int r_boardNo;
	private String r_writer;
	private String r_memberId;
	private String r_ip;
	private Date r_regdate;
	private String r_content;
	private int pageStart;

	@Override
	public String toString() {
		return "ReplyVO [r_no=" + r_no + ", r_boardNo=" + r_boardNo + ", r_writer=" + r_writer + ", r_memberId="
				+ r_memberId + ", r_ip=" + r_ip + ", r_regdate=" + r_regdate + ", r_content=" + r_content
				+ ", pageStart=" + pageStart + "]";
	}
	
	public int getR_no() {
		return r_no;
	}
	public void setR_no(int r_no) {
		this.r_no = r_no;
	}
	public int getR_boardNo() {
		return r_boardNo;
	}
	public void setR_boardNo(int r_boardNo) {
		this.r_boardNo = r_boardNo;
	}
	public String getR_writer() {
		return r_writer;
	}
	public void setR_writer(String r_writer) {
		this.r_writer = r_writer;
	}
	public String getR_memberId() {
		return r_memberId;
	}
	public void setR_memberId(String r_memberId) {
		this.r_memberId = r_memberId;
	}
	public String getR_ip() {
		return r_ip;
	}
	public void setR_ip(String r_ip) {
		this.r_ip = r_ip;
	}
	public Date getR_regdate() {
		return r_regdate;
	}
	public void setR_regdate(Date r_regdate) {
		this.r_regdate = r_regdate;
	}
	public String getR_content() {
		return r_content;
	}
	public void setR_content(String r_content) {
		this.r_content = r_content;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
		
}