package member.model.service;

import java.sql.*;
import static common.JDBCTemplate.*;

import member.model.dao.MemberDao;
import member.model.vo.Member;

public class MemberService {
	// 1. 로그인용 서비스
	public Member loginMember(String id, String pwd) {
		//JDBCTemplate 만들기
		Connection conn = getConnection();
		
		Member loginUser = new MemberDao().loginMember(conn, id, pwd);
		// MemberDao 클래스를 만들어 loginMember 메소드 완성 시키기
		
		close(conn);
		
		return loginUser;
		// 다시 loginServlet으로 돌아가자!
	}

	// 2. 회원가입용 서비스
	public int insertMember(Member m) {
		Connection conn = getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	// 3. 회원 정보 수정용 서비스
	public Member updateMember(Member m) {
		Connection conn = getConnection();
		Member updateMem = null;
		
		int result = new MemberDao().updateMember(conn, m);
		
		if(result > 0) {
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return updateMem;
	}

	// 4. 비밀번호 변경용 서비스
	public Member updatePwd(String userId, String userPwd, String newPwd) {
		Connection conn = getConnection();
		
		int result = new MemberDao().updatePwd(conn, userId, userPwd, newPwd);
		
		Member updateMember = null;
		
		if(result > 0) {
			updateMember = new MemberDao().selectMember(conn, userId);
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return updateMember;
	}

	public int deleteMember(String userId) {
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn, userId);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	// 6. 아이디 중복 체크용 서비스
	public int idCheck(String userId) {
		Connection conn = getConnection();
		int result = new MemberDao().idCheck(conn, userId);
		
		close(conn);
		
		return result;
	}

	
	
	
	
	
	
}
