package board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MyFileRenamePolicy;
import member.model.vo.Member;

/**
 * Servlet implementation class ThumbnailInsertServlet
 */
@WebServlet("/insert.th")
public class ThumbnailInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// String title = request.getParameter("title");
		// *** form 전송을 multipart/form-data로 전송하는 경우
		// 기존처럼 request.getParameter()로 값을 받을 수 없다 ***
		
		// cos.jar가 파일도 받고 form의 다른 값들도 받아주는 역할을 한다
		// com.orelilly.servlet의 약자이다.
		// http://www.servlets.com 에 접속하여 다운 받은
		// cos.jar 파일을 lib 폴더에 복사하기!!!
		
		// enctype이 multipart/form-data로 전송 되었는지 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// 1_1. 전송 파일 용량 제한 : 10Mbyte로 제한하는 경우
			//      1Kbyte = 1024byte
			//      1Mbyte = 1024kbyte
			//      10Mbyte = 1024 * 1024 * 10 byte
			int maxSize = 1024 * 1024 * 10;
			
			// 1_2. 웹 서버 컨테이너 경로(WebContent) 추출
			// 해당 컨테이너의 구동중인 웹 어플리케이션의 루트 경로 알아냄
			String root = request.getSession().getServletContext().getRealPath("/");
			
			// System.out.println("root : " + root);
			
			// 1_3. 파일이 실제로 저장될 경로 (root/resources/thumbnail_uploadFile/)
			String savePath = root + "/resources/thumbnail_uploadFile/";
			
			/* 2. 파일명 변환 및 저장 작업
			 * 
			 * HttpServletRequest --> MultipartRequest로 변경
			 * MultipartReqeust multiRequest 
			 *   = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			 * 
			 * 위의 객체 생성과 동시에 업로드한 파일이 서버에 업로드 완료 됨
			 * 즉, 문제가 있든 없든 간에 우선 서버에 업로드 되는 것이므로 문제가 발생한 경우
			 * 업로드 된 파일을 삭제해야한다.
			 * 
			 * 그리고 사용자가 올린 파일명을 그대로 저장하지 않는 것이 일반적
			 * - 같은 파일명이 있는 경우 이전 파일을 덮어쓸 수 있다.
			 * - 한글로 된 파일명, 특수 기호나 띄어쓰기 등은 서버에 따라 문제가 생길 수 있다.
			 * 
			 * DefaultFileRenamePolicy는 cos.jar 안에 존재하는 클래스이고
			 * 위의 multiRequest 객체 생성 시 DefaultFileRenamePolicy 클래스의 rename
			 * 메소드가 실행되면서 파일명 수정 작업이 일어남
			 * 같은 파일명이 존재하는 지를 검사하고 있을 경우에는 파일명 뒤에 카운팅 된 숫자를 붙여줌
			 * ex : aaa.zip, aaa1.zip, aaa2.zip
			 * 
			 * 하지만 우리는 DefaultFileRenamePolicy를 사용하지 않고 직접 우리 방식대로
			 * rename 작업을 하기 위한 클래스를 만들 것
			 * common 패키지 안에 MyFileRenamePolicy 클래스를 FileRenamePolicy를 상속 받아 만들어주자!
			 * 
			 * */
			
			// 2_1. 1번에서 작업한 내용(저장경로 설정, 용량제한) + 인코딩, 파일명 변환 기능 클래스
			// 들을 지정해서 request-> MultipartRequest 형으로 변환
			MultipartRequest multiRequest 
			= new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			// --> 이 순간에 MyFileRenamePolicy의 rename 메소드가 실행됨
			//     그리고 rename 된 파일이 폴더에 저장 됨
			
			// 2_2. DB에 저장하기 위해 change_name과 origin_name각각의 리스트 들을 만들어 주는 작업
			
			// 다중 파일을 묶어서 업로드 하기에 컬렉션을 사용
			// 실제로 저장된 파일의 이름(변경명)을 저장할 ArrayList를 생성
			ArrayList<String> changeFiles = new ArrayList<String>();
			// 원본 파일의 이름을 저장할 ArrayList 생성
			ArrayList<String> originFiles = new ArrayList<String>();
			
			// getFileNames() - 폼에서 전송 된 파일 리스트들의 name값을 반환
			Enumeration<String> files = multiRequest.getFileNames();
			// -> 전송 순서 역순으로 쌓여 있음
			
			while(files.hasMoreElements()) {
				
				//files에 담겨있는 파일 리스트들의 name 값을 반환
				String name = files.nextElement();
				
				// 해당 파일이 null이 아닌 경우
				if(multiRequest.getFilesystemName(name) != null) {
					// getFilesystemName() - MyRenamePolicy의 rename 메소드에서
					// 작성한대로 rename 된 파일명
					String changeName = multiRequest.getFilesystemName(name);
					
					// getOriginalFileName() - 실제 사용자가 업로드 할 때 파일명
					String originName = multiRequest.getOriginalFileName(name);
					
					changeFiles.add(changeName);
					originFiles.add(originName);
				}
			}
			
			// 3_1. 파일 외에 게시판 제목, 내용, 작성자 회원 번호 받아와서 Board 객체 생성
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String bWriter = String.valueOf(((Member)request.getSession().getAttribute("loginUser")).getUserNo());
			
			Board b = new Board();
			b.setbTitle(title);
			b.setbContent(content);
			b.setbWriter(bWriter);
			
			// 3_2. Attachment 테이블에 값 삽입할 것들 작업하기
			// Attachment 객체들을 담을 리스트 만들어 주기
			ArrayList<Attachment> fileList = new ArrayList<>();
			// 전송 순서 역순으로 파일이 changeFiles, originFiles에 저장 되었기 때문
			// 에 반복문을 역으로 수행함
			for(int i = originFiles.size() - 1; i >= 0; i--) {
				
				Attachment at = new Attachment();
				at.setFilePath(savePath);
				at.setOriginName(originFiles.get(i));
				at.setChangeName(changeFiles.get(i));
				
				// 타이틀 이미지인 경우 fileLevel을 0으로, 일반 이미지면 fileLevel이 1
				// 타이틀 이미지가 originFiles에서 마지막 인덱스이기 때문에
				if(i == originFiles.size() -1) {
					at.setFileLevel(0);
				}else {
					at.setFileLevel(1);
				}
				
				fileList.add(at);
			}
			
			// 4. 사진 게시판 작성용 비즈니스 로직을 처리할 서비스 요청
			// (board 객체, Attachment 리스트 전달)
			int result = new BoardService().insertThumbnail(b, fileList);
			
			if(result > 0) {
				response.sendRedirect("list.th");
			}else {
				// 실패 시 저장된 사진 삭제
				for(int i = 0; i < changeFiles.size(); i++) {
					// 파일 시스템에 저장 된 이름으로 파일 객체 생성함
					File failedFile = new File(savePath + changeFiles.get(i));
					failedFile.delete();
				}
				
				request.setAttribute("msg", "사진 게시판 등록 실패!!");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
				
			}
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
