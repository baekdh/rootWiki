package com.root.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.root.domain.BackupBoardVO;
import com.root.domain.BoardVO;
import com.root.domain.HitVO;
import com.root.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;
	
	@Override
	public List<BoardVO> searchBoard(String search) throws Exception {
		return dao.searchBoard(search);
	}
	
	@Override
	public List<BackupBoardVO> searchBackupBoard(String search) throws Exception {
		return dao.searchBackupBoard(search);
	}

	@Override
	public void writeBoard(BoardVO vo) throws Exception {
		dao.writeBoard(vo);
	}
	
	//추가 : 제목 중복체크
	@Override
	public int titleCheck(String title) throws Exception {
		return dao.titleCheck(title);
	}

	@Override
	public BoardVO getBoard(int boardNo) throws Exception {
		return dao.getBoard(boardNo);
	}

	@Override
	public void writeBackupBoard(BackupBoardVO vo) throws Exception {
		dao.writeBackupBoard(vo);
	}

	@Override
	public int updateBoard(BoardVO vo) throws Exception {
		return dao.updateBoard(vo);
	}

	@Override
	public List<BackupBoardVO> getBackupList(int boardNo) throws Exception {
		return dao.getBackupList(boardNo);
	}
	
	//추가:백업리스트 글선택시 보드로 보여주기
	@Override
	public BoardVO getBackupContent(int backupNo) throws Exception {
		return dao.getBackupContent(backupNo);
	}

	//롤백
	@Override
	public int rollback(BackupBoardVO vo) throws Exception {
		return dao.rollback(vo);
	}

	//백업보드가져오기
	@Override
	public BackupBoardVO getBackupBoard(int backupNo) throws Exception {
		return dao.getBackupBoard(backupNo);
	}

	@Override
	public int getBoard_Hit(int boardNo) throws Exception {
		return dao.getBoard_Hit(boardNo);
	}

	@Override
	public int getBoard_nonHit(int boardNo) throws Exception {
		return dao.getBoard_nonHit(boardNo);
	}
	
	//?? -------------------------------------------------------
	@Override
	public void setBackupHit(BackupBoardVO bkvo) throws Exception {
		dao.setBackupHit(bkvo);
	}

	@Override
	public int getBackup_Hit(int backupNo) throws Exception {
		return dao.getBackup_Hit(backupNo);
	}

	@Override
	public int getBackup_nonHit(int backupNo) throws Exception {
		return dao.getBackup_nonHit(backupNo);
	}
	//-------------------------------------------------------
	

	@Override
	public HitVO checkHit1(HitVO vo) throws Exception {
		return dao.checkHit1(vo);
	}

	@Override
	public HitVO checkHit2(HitVO vo) throws Exception {
		return dao.checkHit2(vo);
	}

	@Override
	public void insertHit(HitVO vo) throws Exception {
		dao.insertHit(vo);
	}

	@Override
	public void deleteHit1(HitVO vo) throws Exception {
		dao.deleteHit1(vo);
	}
	
	@Override
	public void deleteHit2(HitVO vo) throws Exception {
		dao.deleteHit2(vo);
	}
	
	@Override
	public int blockIPCheck(String ip) throws Exception {
		return dao.blockIPCheck(ip);
	}
	
	//추가
	@Override
	public void rollbackHit(int backupNo) throws Exception {
		dao.rollbackHit(backupNo);
		
	}

	@Override
	public String totalParser(String content) {
		System.out.println("totalparser start - " + content);
		InputStream is = new ByteArrayInputStream(content.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String sResult = "";
		String s = "";
		boolean bFirst = true;
		boolean bSubFirst = true;
		int nMain = 1;
		int nSub = 1;
		try {
			System.out.println("totalparser try");
			while((s = br.readLine()) != null) {
				StringBuffer sb = new StringBuffer(s);
				// 맨 앞 공백 또는 연속입력된 공백 처리
				int nCursor = 0;
				while(nCursor < sb.length()) {
					if(sb.indexOf(" ", nCursor) == -1) {
						break;
					} else if(sb.indexOf(" ", nCursor) == 0) {
						sb.delete(0, 1);
						sb.insert(0, "&nbsp;");
						nCursor++;
					} else if(sb.indexOf("  ", nCursor) != -1) {
						int nDBlank = sb.indexOf("  ", nCursor);
						sb.delete(nDBlank, nDBlank+2);
						sb.insert(nDBlank, "&nbsp; ");
						nCursor = nDBlank+2;
					} else {
						nCursor = sb.length()+1;
					}
				}
				int nSubFront = sb.indexOf("===");
				int nFront = sb.indexOf("==");
				int nEnd = 0;
				int nSubEnd = 0;
				// 목차 처리
				if(nSubFront != -1 && nSubFront == nFront) {
					nSubEnd = sb.indexOf("===", nSubFront+3);
					if(nSubEnd > nSubFront) {
						nFront = -1;
						sb.insert(nSubEnd+3, "</h3><hr>");
						sb.delete(nSubEnd, nSubEnd+3);
						sb.delete(nSubFront, nSubFront+3);
						sb.insert(nSubFront, "<h3>" + (nMain-1) + "." + nSub + ". ");
						nSub++;
						if(bSubFirst) {
//							sb.insert(nSubFront, "<ul>\n");
							bSubFirst = false;
						}
					}
				}
				nFront = sb.indexOf("==");
				if(nFront != -1) {
					nEnd = sb.indexOf("==", nFront+2);
					if(nEnd > nFront) {
						sb.insert(nEnd+2, "</h2><hr>");
						sb.delete(nEnd, nEnd+2);
						sb.delete(nFront, nFront+2);
						sb.insert(nFront, "<h2>" + nMain + ". ");
						nMain++;
						if(bFirst) {
//							sb.insert(nFront, "<ul>\n");
							bFirst = false;
						}
						if(!bSubFirst) {
//							sb.insert(nFront, "</ul>\n");
							bSubFirst = true;
							nSub = 1;
						}
					}
				}
				// 태그 처리
				if(nSubFront == -1 && nFront == -1) {
					sb = tagParser(sb);
					sb.append("<br>");
				}
				s = sb.toString();
				sResult += s;
			}
//			sResult += "</ul>";
			System.out.println("totalparser - result : " + sResult);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sResult;
	}

	@Override
	public StringBuffer tagParser(StringBuffer sb) {
		int nFront = -1;
		StringBuffer sbResult = sb;
		do {
			nFront = sbResult.indexOf("[[", nFront+1);
			int nEnd = sbResult.indexOf("]]", nFront+2);
			if(nFront != -1 && nEnd > nFront) {
				String sTag = sbResult.substring(nFront+2, nEnd);
				System.out.println("tagParser - 태그명 : " + sTag);
				sb.insert(nEnd+2, "</a>");
				sb.delete(nEnd, nEnd+2);
				sb.delete(nFront, nFront+2);
				try {
					List<BoardVO> voList = searchBoard(sTag);
					int nTagNo = 0;
					for(BoardVO vo : voList ) {
						if(sTag.equals(vo.getTitle())) {
							nTagNo = vo.getBoardNo();
							System.out.println("tagParser - 문서찾음 : " + nTagNo);
							break;
						}
					}
					if(nTagNo != 0)
						sb.insert(nFront, "<a href='content?boardNo=" + nTagNo + "'>");
					else
						sb.insert(nFront, "<a href='javascript:void(0);'>");
				} catch(Exception e) {
					sb.insert(nFront, "<a href='javascript:void(0);'>");
				}
				System.out.println("tagParser - sbResult : " + sbResult);
			}
		} while(nFront != -1);
		return sbResult;
	}
	
	public String quotParser(String content) {
		InputStream is = new ByteArrayInputStream(content.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String sResult = "";
		String s;
		System.out.println("quotParser");
		try {
			System.out.println("totalparser try");
			int nFront = -1;
			while((s = br.readLine()) != null) {
				StringBuffer sb = new StringBuffer(s);
				do {
					nFront = sb.indexOf("\"", nFront+1);
					if(nFront != -1) {
						sb.delete(nFront, nFront+1);
						sb.insert(nFront, "&quot;");
					}
				} while(nFront != -1);
				System.out.println("quotParser - sb : " + sb);
				sResult += sb + "\n";
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sResult;
	}

	@Override
	public String replyParser(String content) {
		System.out.println("replyparser start - " + content);
		InputStream is = new ByteArrayInputStream(content.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String result = "";
		String s;
		try {
			while((s = br.readLine()) != null) {
				result += s + "<br>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getUserIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		System.out.println("TEST : X-FORWARDED-FOR : "+ip);
		if (ip == null) {
		ip = request.getHeader("Proxy-Client-IP");
		System.out.println("TEST : Proxy-Client-IP : "+ip);
		}
		if (ip == null) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		System.out.println("TEST : WL-Proxy-Client-IP : "+ip);
		}
		if (ip == null) {
		ip = request.getHeader("HTTP_CLIENT_IP");
		System.out.println("TEST : HTTP_CLIENT_IP : "+ip);
		}
		if (ip == null) {
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		System.out.println("TEST : HTTP_X_FORWARDED_FOR : "+ip);
		}
		if (ip == null) {
		ip = request.getRemoteAddr();
		}
		return ip;
	}
	

}
