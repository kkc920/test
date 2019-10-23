package board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;

/**
 * Servlet implementation class ThumbnailDownloadServlet
 */
@WebServlet("/download.th")
public class ThumbnailDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fid = Integer.parseInt(request.getParameter("fid"));
		
		Attachment file = new BoardService().selectAttachment(fid);
		
		// 다운로드 하고자 하는 파일 객체 생성
		File downFile = new File(file.getFilePath() + file.getChangeName());
		
		// 다운로드 시킬 때 파일명은 changeName이 아닌 originName으로 다운 받을 수 있도록 처리
		// 네트워크로 전달할 설정들을 헤더라는 설정 공간에 등록
		// originName의 한글명 인코딩 처리
		String originName = new String(file.getOriginName().getBytes("UTF-8"), "ISO-8859-1");
		// 해당 파일명 대로 사용자에게 보여진다
		response.setHeader("Content-Disposition", "attachment; filename=\"" + originName + "\"");
		// 전송할 크기만큼 사용자 컴퓨터에 공간 확보 요청하기
		response.setContentLength((int)downFile.length());
		
		// 클라이언트로 내보낼 출력 스트림 생성
		ServletOutputStream downOut = response.getOutputStream();
		
		// 폴더에서 파일을 읽을 스트림 생성
		BufferedInputStream buf = new BufferedInputStream(new FileInputStream(downFile));
		
		int readBytes = 0;
		// 폴더에서 버퍼를 사용하여 일정 단위로 파일을 입력 받아와서
		while((readBytes = buf.read()) != -1){
			downOut.write(readBytes); // 클라이언트에게 출력
		} 
		
		downOut.close();
		buf.close();
		
		// 보완 작업
		// 1. 매 Servlet마다 한글에 대한 encoding 작업을 함
		//    이를 하나의 filter로 만들어서 매번 거치도록 하자
		// --> filter 패키지를 만들어서 CommonFilter라는 filter 만들기
		
		// 2. 암호화
		// 보통 사이트를 만들 때 사이트 관리자 또한 회원에 대한 비밀번호를 알 수 없어야 함
		// DB에 회원 정보가 저장 될 때 암호화가 된 비밀번호가 저장 되어야 함
		// --> wrapper 패키지를 만들어서 EncryptWrapper 만들기
		
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
