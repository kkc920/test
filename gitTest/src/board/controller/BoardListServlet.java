package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/list.bo")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 두 개의 서비스를 호출하기 때문에 참조 변수로 생성해 놓자
		BoardService bService = new BoardService();
		
		// 1_1. 게시판 리스트 총 갯수 구하기
		int listCount = bService.getListCount();
		
		// System.out.println("listCount : " + listCount);
		
		// 1_2. 페이징 처리 추가
		
		// 페이지 수 처리용 변수 선언
		int currentPage;		// 현재 페이지
		int pageLimit;			// 한 페이지 하단에 보여질 페이지 수
		int maxPage;			// 전체 페이지에 가장 마지막 페이지
		int startPage;			// 한 페이지 하단에 보여질 시작 페이지
		int endPage;			// 한 페이지 하단에 보여질 끝 페이지
		
		int boardLimit = 10;	// 한 페이지에 보여질 게시글 최대 수
		
		// * currentPage : 현재 페이지
		// 기본적으로 게시판은 1페이지부터 시작함
		currentPage = 1;
		
		// 하지만 게시판 전환 시 전달 받은 현재 페이지가 있을 경우 해당 페이지를 currentPage로 적용
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		// * pageLimit : 한 페이지 하단에 보여질 페이지 수
		pageLimit = 10;
		
		// * maxPage : 총 페이지의 마지막 수
		// 예를 들어 글 개수가 101개이면 페이지 수는 10 페이지가 아닌 짜투리 1개까지 한페이지로
		// 쳐서 11페이지가 되어야 함
		// (전체 게시글 수 / 한 페이지에 보여질 개수) 결과를 올림 처리
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		// * startPage : 현재 페이지에 보여지는 페이징 바의 시작 수
		// 나의 현재 페이지(currentPage)에서 pageLimit만큼을 나누고 다시 곱한 뒤 1을 더한다 
		// 1 / 10 * 10 + 1 -> 1
		// 5 / 10 * 10 + 1 -> 1
		// 11 / 10 * 10 + 1 -> 11
		// 15 / 10 * 10 + 1 -> 11
		// 여기서 한가지 예외 상황은 10(20, 30, ...)인데 딱 떨어지는 숫자는 몫이 하나 더 늘어남
		// 그것을 처리하기 위해 currentPage-1을 한다
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		// * endPage : 현재 페이지에서 보여질 마지막 페이지 수
		endPage = startPage + pageLimit - 1;
		
		// 하지만 마지막 페이지 수가 총 페이지 수보다 클 경우
		// maxPage가 13페이지인데 endPage가 20페이지 일 수는 없음
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		// 위에서 계산 된 모든 페이지 관련 변수들을 따로 관리하기는 어려우므로
		// 페이지 정보를 공유할 vo 객체 PageInfo 클래스를 만들고 오자
		PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
		
		ArrayList<Board> list = bService.selectList(currentPage, boardLimit);
		
		RequestDispatcher view = request.getRequestDispatcher("views/board/boardListView.jsp");
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
