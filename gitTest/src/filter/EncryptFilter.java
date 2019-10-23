package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import wrapper.EncryptWrapper;

/**
 * Servlet Filter implementation class EncryptFilter
 */
// 이 필터는 암호화가 필요한 서블릿에서만 적용이 되기 때문에 직접 서블릿 명을 통해 필터 적용을 해보자
// 암호화가 필요한 서블릿은 InsertMemberServlet, LoginServlet, UpdatePwdServelt
@WebFilter(filterName = "encrypt", servletNames = {"InsertMemberServlet", "LoginServlet", "UpdatePwdServlet"})
// 서블릿 명으로 필터를 적용할 때 배포명이 반드시 있어야 한다!!!
public class EncryptFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncryptFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// wrapper : 필터에게 넘어온 request, response를 변경할 때 사용되는 개념
		// request나 response를 특정한 형태로 감싸서 변형 시킴
		
		// 전달 받아 온 request를 암호화 래퍼로 감싼 채로 생성
		EncryptWrapper encWrapper = new EncryptWrapper((HttpServletRequest)request);
		
		//chain.doFilter(request, response);
		chain.doFilter(encWrapper, response);
		
		// 이렇게 작성하면 이제부터 InsertMemberServlet, LoginServlet, 
		// UpdatePwdServlet의 request.getParameter(key)는
		// EncryptWrapper에서 오버라이딩 된 getParameter 메소드로 실행 된다
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
