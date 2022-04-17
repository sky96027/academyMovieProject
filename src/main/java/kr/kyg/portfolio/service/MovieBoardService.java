package kr.kyg.portfolio.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.vo.FileVO;
import kr.kyg.portfolio.vo.LikesVO;
import kr.kyg.portfolio.vo.MemberVO;
import kr.kyg.portfolio.vo.MovieBoardVO;

public interface MovieBoardService {

	void insertMovieBoard(MovieBoardVO movieBoard, List<MultipartFile> files2, MultipartFile thumb);

	List<MovieBoardVO> getBoardList(Criteria cri);

	int getTotalCount(Criteria cri);

	MovieBoardVO getMovieBoard(Integer mv_num, MemberVO user);

	List<FileVO> getFileList(Integer mv_num);

	void deleteMovieBoard(Integer mv_num, MemberVO user);

	MovieBoardVO getMovieBoard(Integer mv_num);

	void updateMovieBoard(MovieBoardVO board, List<MultipartFile> files2, Integer[] fileNums, MultipartFile thumb);

	String likes(LikesVO likes, MemberVO user);

	String viewLikes(LikesVO likes, MemberVO user);

	List<MovieBoardVO> getLockerList(Criteria cri, MemberVO user);
	
	//void updateViews(Integer mv_num);
}
