package kr.kyg.portfolio.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.pagination.PageMaker;
import kr.kyg.portfolio.service.MemberService;
import kr.kyg.portfolio.service.MovieBoardService;
import kr.kyg.portfolio.vo.MemberVO;
import kr.kyg.portfolio.vo.MovieBoardVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	MemberService memberService;
	@Autowired
	MovieBoardService movieBoardService; 
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homeGet(ModelAndView mv, Criteria cri) {
		cri.setPerPageNum(10);
		List<MovieBoardVO> list = movieBoardService.getBoardList(cri);
		int totalCount = movieBoardService.getTotalCount(cri);
		PageMaker pm = new PageMaker(totalCount, 5, cri);
		System.out.println(pm);
		System.out.println(list);
		mv.addObject("pm",pm);
		mv.addObject("list",list);
		mv.setViewName("/main/home");
		return mv;
	}
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public ModelAndView loginGet(ModelAndView mv) {
		
		mv.setViewName("/member/login");
		return mv;
	}
	@RequestMapping(value= "/member/login", method=RequestMethod.POST)
	public ModelAndView loginPost(ModelAndView mv, MemberVO member){
		
		MemberVO user = memberService.login(member);
		System.out.println(user);
		if(user == null)
			mv.setViewName("redirect:/member/login");
		else {
			user.setMe_auto_login(member.getMe_auto_login());
			mv.addObject("user",user);
			mv.setViewName("redirect:/");
		}
    return mv;
	}
	@RequestMapping(value = "/member/signup", method = RequestMethod.GET)
	public ModelAndView signupGet(ModelAndView mv, MemberVO user) {
		mv.setViewName("/member/signup");
		return mv;
	}
	@RequestMapping(value = "/member/signup", method = RequestMethod.POST)
	public ModelAndView signupPost(ModelAndView mv, MemberVO user) {
		//MemberVO user = new MemberVO();
		//user.setMe_id(me_id);
		//user.setMe_birth(me_birth);
		
		if(memberService.signup(user)) {
			mv.setViewName("redirect:/");
		}else {
			mv.setViewName("redirect:/member/signup");
		}
		return mv;
	}
	@ResponseBody
	@RequestMapping(value ="/idcheck")
	public String idajax(String id){
		
		if(!memberService.idDuplicated(id))
			return "ok";
		return "no";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutGet(ModelAndView mv, 
			HttpServletRequest request, HttpServletResponse response) {
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
		if(user != null) {
			//세션에 있는 유저 정보를 삭제
			request.getSession().removeAttribute("user");
			//request에 있는 쿠키 들 중에서 loginCookie 정보를 가져옴
			Cookie cookie = WebUtils.getCookie(request, "loginCookie");
			//loginCookie 정보가 있으면 => 자동로그인 했다가 로그아웃하는 경우
			if(cookie != null) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				user.setMe_session_id("none");
				user.setMe_session_limit(new Date());
				memberService.updateAutoLogin(user);
			}
		}
		mv.setViewName("redirect:/");
		return mv;
	}
	@RequestMapping(value = "/member/mypage")
	public ModelAndView mypageGet(ModelAndView mv, MemberVO input
			,HttpServletRequest request) {
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
		MemberVO newUser = memberService.updateMember(input, user);
		if(newUser != null) {
			request.getSession().setAttribute("user", newUser);
		}
		mv.setViewName("/member/mypage");
		return mv;
	}
	@RequestMapping(value = "/member/find")
	public ModelAndView memberFind(ModelAndView mv) {
		mv.setViewName("/member/find");
		return mv;
	}
	@ResponseBody
	@RequestMapping(value = "/member/find/id")
	public String memberFindId(@RequestBody MemberVO member) {
		return memberService.findId(member);
	}
	@ResponseBody
	@RequestMapping(value = "/member/find/pw")
	public String memberFindPw(@RequestBody MemberVO member) {
		
		return memberService.findPw(member);
	}
	
}
