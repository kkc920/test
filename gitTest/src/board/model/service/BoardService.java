package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.Reply;

public class BoardService {
	// 1. 게시판 리스트 갯수 조회용 서비스
	public int getListCount() {
		Connection conn = getConnection();

		int listCount = new BoardDao().getListCount(conn);

		close(conn);

		return listCount;
	}

	// 2. 게시판 리스트 조회용 서비스
	public ArrayList<Board> selectList(int currentPage, int boardLimit) {
		Connection conn = getConnection();

		ArrayList<Board> list = new BoardDao().selectList(conn, currentPage, boardLimit);

		close(conn);

		return list;
	}

	// 3. 게시글 작성용 서비스
	public int insertBoard(Board b) {
		Connection conn = getConnection();

		int result = new BoardDao().insertBoard(conn, b);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	// 4. 게시판 상세 보기 서비스(조회수 증가)
	public Board selectBoard(int bId) {
		Connection conn = getConnection();

		BoardDao bDao = new BoardDao();

		// 조회수 증가
		int result = bDao.increaseCount(conn, bId);

		Board b = null;

		if (result > 0) {
			b = bDao.selectBoard(conn, bId);
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return b;
	}

	// 5. 게시글 선택해오기(조회수 증가X)
	public Board selectBoardNoCnt(int bId) {
		Connection conn = getConnection();
		
		Board b = new BoardDao().selectBoard(conn, bId);
		
		close(conn);
		
		return b;
	}
	
	// 6. 게시글 수정 서비스
	public int updateBoard(Board board) {
		Connection conn = getConnection();
		
		int result = new BoardDao().updateBoard(conn, board);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	// 7. 게시글 삭제 서비스
	public int deleteBoard(int bid) {
		Connection conn = getConnection();

		int result = new BoardDao().deleteBoard(conn, bid);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	/* 사진 게시판 리스트에 보여질 게시판 리스트 조회용 서비스
	 * 전달 받은 flag 값이 1인 경우 게시판 정보 리스트가 리턴
	 * 2인 경우 메인 사진 리스트가 리턴 */
	public ArrayList selectList(int flag) {
		Connection conn = getConnection();
		
		ArrayList list = null;
		
		BoardDao bDao = new BoardDao();
		
		if(flag == 1) {
			list = bDao.selectBList(conn);
		}else {
			list = bDao.selectFList(conn);
		}
		
		close(conn);
		
		return list;
	}
	
	// 사진 게시판 글쓰기 서비스
	public int insertThumbnail(Board b, ArrayList<Attachment> fileList) {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		
		int result1 = bDao.insertThBoard(conn, b);
		int result2 = bDao.insertAttachment(conn, fileList);
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1;
	}

	// 사진게시판 상세 서비스(첨부파일 선택)
	public ArrayList<Attachment> selectThumbnail(int bId) {
		Connection conn = getConnection();
		
		ArrayList<Attachment> list = new BoardDao().selectThumbnail(conn, bId);
		
		close(conn);
		
		return list;
	}

	// 첨부파일 다운로드 수 증가, 파일 다운로드용 서비스
	public Attachment selectAttachment(int fid) {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		
		int result = bDao.updateDownloadCount(conn, fid);
		
		Attachment at = null;
		if(result > 0) {
			commit(conn);
			at = bDao.selectAttachment(conn, fid);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return at;
	}
	
	// 댓글 추가 후 새로 갱신 된 댓글 리스트 조회용 서비스
	public ArrayList<Reply> insertReply(Reply r) {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		
		int result = bDao.insertReply(conn, r);
		
		ArrayList<Reply> rlist = null;
		
		if(result > 0) {
			commit(conn);
			rlist = bDao.selectReplyList(conn, r.getRefBid());
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return rlist;
	}

	public ArrayList<Reply> selectReplyList(int bId) {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		ArrayList<Reply> rlist = null;
		
		rlist = bDao.selectReplyList(conn, bId);
		
		close(conn);

		return rlist;
	}
}
