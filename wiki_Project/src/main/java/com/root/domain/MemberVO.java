package com.root.domain;

import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class MemberVO {
	@Length(min=4, max=20)
	private String memberid;
	@Length(min=6, max=20) // ¼öÁ¤
	private String memberpw;
	@Length(min=2, max=6)
	private String membername;
	@Email
	private String email;
	@NotBlank
	private String tel;
	
	private Date regdate;
	private int memberlevel;
	
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getMemberpw() {
		return memberpw;
	}
	public void setMemberpw(String memberpw) {
		this.memberpw = memberpw;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getMemberlevel() {
		return memberlevel;
	}
	public void setMemberlevel(int memberlevel) {
		this.memberlevel = memberlevel;
	}
	@Override
	public String toString() {
		return "MemberVO [memberid=" + memberid + ", memberpw=" + memberpw + ", membername=" + membername + ", email=" + email
				+ ", tel=" + tel + ", regdate=" + regdate + ", memberlevel=" + memberlevel + "]";
	}
}
