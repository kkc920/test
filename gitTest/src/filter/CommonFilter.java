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

/**
 * Servlet Filter implementation class CommonFilter
 */
// filterName을 통해서 어떤 역할을 하는 필터인지 지정을 해주고
// urlPatterns을 통해서 어떠한 서블릿을 가기 전에 거칠 것인지를 지정해준다
//@WebFilter(filterName="encoding", urlPatterns="/*")
// --> /*로 작성하게 되면 모든 서블릿을 뜻한다

public class CommonFilter implements Filter {
	/* 서블릿 필터는 request, response가 서블릿이나 JSP 등 리소스에 도달하기 전
	 * 필요한 전/후 처리 작업을 맡는다.
	 * 필터는 FilterChain을 통해 여러 개 혹은 연쇄적으로 사용 가능하다.
	 * 
	 * 필터 클래스를 등록하는 방법
	 * 1. WEB-INF/web.xml 파일에 필터를 등록해야만 사용 가능
	 * 2. 하지만 최근에는 web.xml에 등록하지 않고 @WebFilter라는 어노테이션으로 대체해서
	 *    사용하는 추세임
	 * 
	 * Filter 라이프 사이클
	 * - init() : 컨테이너가 필터를 인스턴스화 할 때 호출, 필터 설정 관련 코드 작성 가능
	 * - doFilter() : 컨테이너가 현재 요청에 필터 적요을 하겠다 판단되면 호출
	 *                ServletRequest, ServletResponse, FilterChain 객체
	 * - destroy() : 컨테이너가 필터 인스턴스를 제거할 때 호출
	 */
	
    /**
     * Default constructor. 
     */
    public CommonFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		System.out.println("CommonFilter가 소멸되었습니다.");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		// 서블릿 수행 전 인코딩 필터 동작하기
		System.out.println("-- doFilter() 동작합니다. --");
		
		// 전송 방식이 post일 때 반드시 request에 대해서 인코딩을 한다
		HttpServletRequest hrequest = (HttpServletRequest)request;
		
		if(hrequest.getMethod().equalsIgnoreCase("post")) {
			
			System.out.println("post 전송시에만 encoding 됩니다.");
			request.setCharacterEncoding("utf-8");
			
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
		// -> FilterChain의 doFilter()는 다음 필터를 호출하거나,
		//    마지막 필터라면 servlet으로 넘겨줌
		
		// 서블릿 동작 후 코드 실행
		System.out.println("-- doFilter() 이후 처리되는 코드입니다. --");
		
		// 일반 게시판 작성하기 servlet에서 코드를 지우고 테스트!
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("CommonFilter 초기화");
	}

	
	
	
	
	
	
	
	
	
	
}
