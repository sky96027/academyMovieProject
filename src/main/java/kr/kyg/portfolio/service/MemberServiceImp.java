package kr.kyg.portfolio.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kyg.portfolio.dao.MemberDAO;
import kr.kyg.portfolio.vo.MemberVO;

@Service
public class MemberServiceImp implements MemberService {
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	MemberDAO memberDao;
	@Autowired
	JavaMailSender mailSender;
	@Override
	public MemberVO login(MemberVO member) {
		if(member == null || member.getMe_id() == null)
			return null;
		MemberVO user = memberDao.getMember(member.getMe_id());
		if(user == null)
			return null;
		if(passwordEncoder.matches(member.getMe_pw(), user.getMe_pw()))
			return user;
		return null;
	}
	@Override
	public boolean signup(MemberVO user) {
		if(user == null)
			return false;
		if(user.getMe_id()== null)
			return false;
		if(user.getMe_pw()==null)
			return false;
		if(memberDao.getMember(user.getMe_id()) != null)
			return false;
		String encPw = passwordEncoder.encode(user.getMe_pw());
		user.setMe_pw(encPw);
		memberDao.insertMember(user);
		return true;
	}
	@Override
	public boolean idDuplicated(String id) {
		MemberVO user = memberDao.getMember(id);
		if(user == null)
			return false;
		return true;
	}
	@Override
	public MemberVO updateMember(MemberVO input, MemberVO user) {
		if(input == null || user == null)
			return null;
		if(input.getMe_name() == null 
				|| input.getMe_birth() == null
				|| input.getMe_gender() == null)
			return null;
		input.setMe_id(user.getMe_id());
		if(input.getMe_pw() == null || input.getMe_pw().length() == 0) {
			input.setMe_pw(user.getMe_pw());
		}else {
			String encPw = passwordEncoder.encode(input.getMe_pw());
			input.setMe_pw(encPw);
		}
		if(input.getMe_address() == null || input.getMe_address().length() == 0) {
			input.setMe_address(user.getMe_address());
		}
		memberDao.updateMember(input);
		return input;
	}
	@Override
	public void updateAutoLogin(MemberVO user) {
		if(user == null)
			return;
		memberDao.updateAutoLogin(user);
		
	}
	@Override
	public MemberVO selectMemberBySessionId(String me_session_id) {
		return memberDao.selectMemberBySessionId(me_session_id);
	}
	@Override
	public String findId(MemberVO member) {
		if(member == null)
			return "";
		MemberVO user = memberDao.findMember(member);
		if(user == null)
			return "";
		return user.getMe_id();
	}

	@Override
	public String findPw(MemberVO member) {
		//예외처리
		if(member == null)
			return "false";
		//회원정보를 가져옴
		MemberVO user = memberDao.getMember(member.getMe_id());
		//회원정보가 없으면 종료
		if(user == null || !user.getMe_email().equals(member.getMe_email()))
			return "false";
		
		//임시 비밀번호 생성
		String newPw = createRandomPw(6);
		//생성된 비밀번호를 DB에 저장(암호화 시켜서 저장)
		String encPw = passwordEncoder.encode(newPw);
		user.setMe_pw(encPw);
		memberDao.updateMember(user);
		
		//이메일로 새 비번을 전송(암호화 안된 비번 전송)
		
		String setfrom = "sky960271@gmail.com";         
	    String tomail  = member.getMe_email();  // 받는 사람 이메일
	    String title   = "새 비밀번호입니다.";      // 제목
	    String content = "새 비밀번호는 " + newPw + "입니다.";    // 내용
	
	    try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper messageHelper 
	            = new MimeMessageHelper(message, true, "UTF-8");
	
	        messageHelper.setFrom(setfrom);  // 보내는사람 생략하거나 하면 정상작동을 안함
	        messageHelper.setTo(tomail);     // 받는사람 이메일
	        messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
	        messageHelper.setText(content);  // 메일 내용
	
	        mailSender.send(message);
	    } catch(Exception e){
	        System.out.println(e);
	        return "error";
	    }
			return "true";
	}
	private String createRandomPw(int maxSize) {
		String newPw = "";
		//maxSize 개수로 이루어진 비번으로, 영어와 숫자로 이루어짐
		//a~z,A~Z,0~9 : 62
		for(int i = 0; i<maxSize; i++) {
			//랜덤 수를 생성(0~61)
			int max = 61, min = 0;
			int r = (int)(Math.random() * (max - min + 1) + min);
			//int r = (int)(Math.random() * 62);
			//랜덤 수가 0~9이면 문자 0~9
			if(0 <= r && r <= 9) {
				newPw += (char)('0' + r);
			}
			//랜덤 수가 10~35이면 문자 a~z
			else if(r <= 35) {
				newPw += (char)('a' + (r - 10));
			}
			//랜덤 수가 36~61이면 문자 A~Z
			else if(r <= 61) {
				newPw += (char)('A' + (r - 36));
			}
		}
		return newPw;
	}
	

	
}
