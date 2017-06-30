package com.root.domain;

public class HitVO {
	private int H_boardNO;
	private int H_backupNo; // Ãß°¡
	private String H_memberID;
	private String H_IP;
	private int Hit;
	
	public int getH_boardNO() {
		return H_boardNO;
	}
	public void setH_boardNO(int h_boardNO) {
		H_boardNO = h_boardNO;
	}
	public int getH_backupNo() {
		return H_backupNo;
	}
	public void setH_backupNo(int h_backupNo) {
		H_backupNo = h_backupNo;
	}
	public String getH_memberID() {
		return H_memberID;
	}
	public void setH_memberID(String h_memberID) {
		H_memberID = h_memberID;
	}
	public String getH_IP() {
		return H_IP;
	}
	public void setH_IP(String h_IP) {
		H_IP = h_IP;
	}
	public int getHit() {
		return Hit;
	}
	public void setHit(int hit) {
		Hit = hit;
	}
	@Override
	public String toString() {
		return "HitVO [H_boardNO=" + H_boardNO + ", H_backupNo=" + H_backupNo + ", H_memberID=" + H_memberID + ", H_IP="
				+ H_IP + ", Hit=" + Hit + "]";
	}
	
	
}

