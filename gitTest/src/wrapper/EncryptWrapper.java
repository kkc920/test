package wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// HttpServletRequestWrapper를 상속 받기
public class EncryptWrapper extends HttpServletRequestWrapper {

	// 반드시 명시적으로 HttpServletRequest를 매개변수로 하는 생성자를 작성
	public EncryptWrapper(HttpServletRequest request) {
		super(request);
	}
	
	// ServletRequestWrapper의 getParameter 메소드를 오버라이딩
	@Override
	public String getParameter(String key) {
		String value = "";
		
		if(key != null && (key.equals("userPwd") || (key.equals("newPwd")))) {
			// request 객체에 담긴 파라미터 key값이 userPwd 또는 newPwd인 경우 암호화
			value = getSha512(super.getParameter(key));
			// getSha512 메소드를 통해 암호화 작업을 한다 -> 아래에 getSha512 메소드 만들어주기
		}else {
			// request 객체에 담긴 파라미터 key 값이 userPwd, newPwd가 아닌 그 외의 값은
			// 기존의 값을 그대로 가져온다.
			value = super.getParameter(key);
		}
		
		return value;
	}
	
	public String getSha512(String userPwd) {
		// sha512 해쉬 함수를 사용하여 암호화 한다.
		String encPwd = null;
		
		// SHA-512 방식의 암호화 객체
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-512");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// 전달 받은 비밀번호를 바이트 배열로 리턴함
		byte[] bytes = userPwd.getBytes(Charset.forName("UTF-8"));
		
		// md 객체에 userPwd 바이트 배열을 전달해서 갱신
		md.update(bytes);
		
		// java.util.Base64인코더를 이용해서 암호화된 바이트 배열을 인코딩해서 문자열로 출력
		encPwd = Base64.getEncoder().encodeToString(md.digest());
		return encPwd;
	}
	
	// 이제 이 EncryptWrapper를 필터에 적용시키기 위해 EncryptFilter를 만들러 가자!!

}
