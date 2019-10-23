package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class InsertMemberServlet
 */
// @WebServlet("/insert.me") // 암호화 처리 전
@WebServlet(name = "InsertMemberServlet", urlPatterns = "/insert.me")
// -> 암호화 처리 후
public class InsertMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 한글이 있을 경우 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		// 2. request에 담겨있는 값들 꺼내서 변수에 저장 및 객체 생성
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		// checkbox와 같은 경우 배열로 받게 되므로 getParameterValues 이용
		String arr[] = request.getParameterValues("interest");
		
		String interest = "";
		if(arr != null) {
			interest = String.join(",", arr);
		}
		
		Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
		
		//System.out.println(m);
		
		// 3. 비지니스 로직을 수행할 서비스 메소드 전달하고 그 결과 값 받기
		int result = new MemberService().insertMember(m);
		
		// 4. 받은 결과에 따라 성공 / 실패 페이지로 내보내기
		if(result > 0) {
			request.getSession().setAttribute("msg", "회원가입 성공!!");
			// menubar.jsp에 alert창 출력하도록 설정
			response.sendRedirect(request.getContextPath());
		} else {
			request.setAttribute("msg", "회원 가입에 실패하였습니다.");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
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
