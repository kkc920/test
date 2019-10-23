package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
// @WebServlet("/login.me") // 암호화 처리 전
@WebServlet(name = "LoginServlet", urlPatterns = "/login.me")
// -> 암호화 처리 후
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 전송 값에 한글이 있을 경우 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		// -> 로그인시 넘어오는 값에는 한글이 없기 때문에 없어도 무방
		
		// 2. 전송 값 꺼내서 변수에 기록 또는 객체에 저장 --> request.getParameter("name");
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// Member VO 만들러 가기
		
		// 3. 비지니스 로직 처리하는 서비스 클래스의 해당 메소드를 실행하고, 그 처리 결과를 받음
		Member loginUser = new MemberService().loginMember(userId, userPwd);
		// MemberService 클래스 만들러 가기
		
		// 4. 응답화면으로 보낼 값에 한글이 있을 경우 인코딩 처리
		response.setContentType("text/html; charset=utf-8");
		
		// 5. 서비스 요청에 해당하는 결과를 가지고 성공/실패에 대한 뷰페이지(파일)을
		// 선택해서 내보냄
		if(loginUser != null) {
			// 이제 member를 어딘가에 담아서 보내줘야 하는데 다음과 같은 방식들이 있다
			// 1. application : jsp, servlet, java 다 접근해서 사용 가능
			// --> 공유 범위가 큼
			// 2. session : 로그인 한 그 사용자만 사용 가능, jsp에서 사용 가능
			// --> 공유 범위가 모든 jsp에서만
			// 3. request : 전달 받은 그 대상 jsp만 사용 가능
			// --> 공유 범위가 제한적
			// 4. page(==this) : 자기 자신만 사용 가능(해당 jsp 파일 내에서만)
			// --> 공유 범위가 제일 작다
			
			// 위의 4개의 객체 모두 setAttribute("이름", 객체)를 이용해 객체 저장 가능
			// 꺼낼 때는 getAttribute("이름")을 이용
			// 삭제 할 때는 removeAttribute("이름")을 이용
			
			/*
			 * Session 객체 : 웹 브라우저당 하나씩 존재하는 객체로 Session에 로그인한
			 * 회원 객체를 한번 등록해 놓으면 어느 페이지던 간에 session에 담겨 있는 회원
			 * 객체에 대한 정보를 사용할 수 있다.
			 */
			
			// 해당 클라이언트에 대한 세션 객체 생성
			HttpSession session = request.getSession();
			
			// session.setMaxInactiveInterval(600);
			// 10분 뒤 자동 로그아웃 기능
			// (마지막 접근 이후 일정 시간 이내에 다시 세션에 접근하지 않을 경우
			//  자동 세션 종료)
			
			session.setAttribute("loginUser", loginUser);
			// 세션 객체에 로그인 한 유저의 정보를 담음
			// 세션 객체에 정보를 저장했기 때문에 request에 담을 필요 없음
			
			// 로그인 완료 후 다시 메인 페이지로
			response.sendRedirect(request.getContextPath());
			
		} else { // 실패일 경우
			request.setAttribute("msg", "로그인 실패");
			
			// common - errorPage.jsp로 보내주기
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
