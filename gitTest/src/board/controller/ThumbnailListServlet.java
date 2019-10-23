package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;

/**
 * Servlet implementation class ThumbnailListServlet
 */
@WebServlet("/list.th")
public class ThumbnailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일 관련 즉 사진 게시판을 위해 Attachment라는 테이블을 만듦
		// 따라서 Attachment vo 만들고 오기!
		
		// 두 개의 서비스를 요청하기 위해 BoardSevice 참조 변수 미리 선언
		BoardService bService = new BoardService();
		
		// 사진 게시판은 일반 게시판과 달리 페이징 처리는 따로 하지 않을 것 -> 참고해서 스스로 하세용
		
		// 1. 우선 사진 게시판 리스트 정보 불러오기(Board 테이블의 bType이 2인 애들)
		ArrayList<Board> blist = bService.selectList(1);
		
		// 2. 사진 리스트도 불러오기(Attachment 테이블의 fileLevel이 0인 애들)
		ArrayList<Attachment> flist = bService.selectList(2);
		
		if(blist != null && flist != null) {
			request.setAttribute("blist", blist);
			request.setAttribute("flist", flist);
			request.getRequestDispatcher("views/thumbnail/thumbnailListView.jsp").forward(request, response);
			
		}else {
			request.setAttribute("msg", "사진 게시판 조회 실패!!");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
