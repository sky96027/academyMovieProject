package kr.kyg.portfolio.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.kyg.portfolio.vo.FileVO;
import kr.kyg.portfolio.vo.LikesVO;
import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.pagination.PageMaker;
import kr.kyg.portfolio.service.MovieBoardService;
import kr.kyg.portfolio.vo.MemberVO;
import kr.kyg.portfolio.vo.MovieBoardVO;

@Controller
public class MovieBoardController {
	@Autowired
	MovieBoardService movieBoardService;
	@RequestMapping(value = "/movieboard/register", method = RequestMethod.GET)
	public ModelAndView RegisterGet(ModelAndView mv) {
		mv.setViewName("/movieboard/register");
		return mv;
	}
	@RequestMapping(value = "/movieboard/register", method = RequestMethod.POST)
	public ModelAndView RegisterPost(ModelAndView mv, MovieBoardVO movieBoard,
			HttpServletRequest request, List<MultipartFile> files2, MultipartFile thumb) throws Exception {
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
		movieBoard.setMv_me_id(user.getMe_id());
		System.out.println(movieBoard);
		movieBoardService.insertMovieBoard(movieBoard, files2, thumb);
		mv.setViewName("redirect:/");
		return mv;
	}
	@RequestMapping(value="/movieboard/detail")
	public ModelAndView movieboardDetail(ModelAndView mv, Integer mv_num) {
		mv.setViewName("/movieboard/detail");
		//게시글 번호 확인
		//System.out.println("게시글 번호 : " + mv_num);
		MovieBoardVO movieboard = movieBoardService.getMovieBoard(mv_num);
		List<FileVO> files = movieBoardService.getFileList(mv_num);
		//movieBoardService.updateViews(mv_num);
		//가져온 게시글 확인
		//System.out.println(movieboard);
		//화면에게 게시글을 전달
		mv.addObject("movieboard", movieboard);
		mv.addObject("files", files);
		return mv;
	}
	@RequestMapping(value="/movieboard/delete", method=RequestMethod.GET)
	public ModelAndView movieBoardDeleteGet(ModelAndView mv,Integer mv_num, HttpServletRequest request) {
		//게시글 번호 확인
		System.out.println("게시글 번호 : " + mv_num);
		//로그인한 유저 정보를 확인
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
		//System.out.println(user);
		//서비스에게 게시글 번호와 로그인한 유저 정보를 주면서 게시글 삭제하라고 시킴
		//boardSercie.게시글삭제(게시글 번호, 로그인한 유저정보);
		movieBoardService.deleteMovieBoard(mv_num, user);
		mv.setViewName("redirect:/");
		return mv;
	}
	@RequestMapping(value="/movieboard/modify", method=RequestMethod.GET)
	public ModelAndView movieBoardModify(ModelAndView mv, Integer mv_num
			,HttpServletRequest request) {
		//게시글을 검색해서 화면에 전달 => 서비스에게 일을 시킴
		//게시글 번호를 확인
		//System.out.println("게시글 번호 : " + mv_num);
		//서비스에게 번호와 로그인 회원 정보를 알려주면서 
		//번호와 작성자가 일치하는 게시글을 가져오라고 시킴
		//컨트롤러는 서비스가 보내준 게시글 정보를 가지고 정상 접근인지 아닌지 확인
		MemberVO user = (MemberVO) request.getSession().getAttribute("user");
		//게시글 = 서비스.게시글가져오기(번호, 로그인 정보);
		MovieBoardVO board = movieBoardService.getMovieBoard(mv_num, user);
		
		//게시글이 없으면
			//1. 번호가 잘못된 경우
			//2. 본인이 작성자가 아닌 경우
		if(board == null) {
			mv.setViewName("redirect:/");
		}else {
			//첨부파일을 가져옴
			List<FileVO> fileList = movieBoardService.getFileList(mv_num);
			mv.addObject("fileList",fileList);
			mv.addObject("movieboard", board);
			mv.setViewName("/movieboard/modify");
		}
		
		//서비스에게 번호를 알려주면서 게시글을 가져오라고 시킴
		//서비스가 보내준 게시글의 작성자와 로그인한 회원 아이디가 일치하는지 확인
		//컨트롤러가 서비스가 보내준 게시글 정보를 가지고 추가 확인
		return mv;
	}
	@RequestMapping(value="/movieboard/modify", method=RequestMethod.POST)
	public ModelAndView boardModifyPost(ModelAndView mv,MovieBoardVO board, 
			List<MultipartFile> files2, Integer [] fileNums, MultipartFile thumb) {
		//기존 첨부파일 번호인 fileNums 확인
		//화면에서 수정한 게시글 정보가 넘어오는지 확인
		//System.out.println("게시글 : " + board);
		//서비스에게 게시글 정보를 주면서 업데이트하라고 시킴
		//서비스.게시글업데이트(게시글정보)
		movieBoardService.updateMovieBoard(board, files2, fileNums, thumb);
		//게시글 번호를 넘겨줌
		mv.addObject("mv_num", board.getMv_num());
		mv.setViewName("redirect:/movieboard/detail");
		return mv;
	}
	@ResponseBody
	@RequestMapping(value ="/movieboard/likes")
	public String boardLikes(@RequestBody LikesVO likes,
			HttpServletRequest request){
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
	  return movieBoardService.likes(likes, user);
	}
	@RequestMapping(value ="/movieboard/view/likes")
	public String boardViewLikes(@RequestBody LikesVO likes,
			HttpServletRequest request){
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
		return movieBoardService.viewLikes(likes, user);
	}
	@RequestMapping(value ="/movieboard/movielocker")
	public ModelAndView movieLocker(ModelAndView mv, Criteria cri, HttpServletRequest request){
		cri.setPerPageNum(10);
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
		List<MovieBoardVO> list = movieBoardService.getLockerList(cri, user);
		int totalCount = movieBoardService.getTotalCount(cri);
		PageMaker pm = new PageMaker(totalCount, 5, cri);
		System.out.println(pm);
		System.out.println(list);
		mv.addObject("user",user);
		mv.addObject("pm",pm);
		mv.addObject("list",list);
		mv.setViewName("/movieboard/movielocker");
		return mv;
	}
	
}
