package kr.kyg.portfolio.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import kr.kyg.portfolio.service.MemberService;
import kr.kyg.portfolio.vo.MemberVO;


public class AutoLoginInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Cookie cookie = WebUtils.getCookie(request, "loginCookie");
		//자동 로그인을 해야하면
		if(cookie != null) {
			MemberVO user = memberService.selectMemberBySessionId(cookie.getValue());
			if(user != null) {
				session.setAttribute("user", user);
			}
		}
		return true;
	}

	
}
