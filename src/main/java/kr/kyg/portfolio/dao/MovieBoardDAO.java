package kr.kyg.portfolio.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import kr.kyg.portfolio.vo.MovieBoardVO;
import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.vo.FileVO;
import kr.kyg.portfolio.vo.LikesVO;
import kr.kyg.portfolio.vo.MemberVO;

public interface MovieBoardDAO {


	void insertFile(@Param("file")FileVO fileVo);

	void insertMovieBoard(@Param("movieBoard")MovieBoardVO movieBoard);

	List<MovieBoardVO> getMovieBoardList(@Param("cri")Criteria cri);

	int selectCountMovieBoard(@Param("cri")Criteria cri);

	MovieBoardVO getMovieBoard(@Param("mv_num")Integer mv_num);

	List<FileVO> selectFileList(@Param("mv_num")Integer mv_num);

	void deleteMovieBoard(@Param("mv_num")Integer mv_num);
	
	void deleteFile(@Param("fi_num")int fi_num);

	void updateMovieBoard(@Param("movieboard")MovieBoardVO dbBoard);

	FileVO selectFile(@Param("mv_num")int mv_num, @Param("fi_type")String type);

	LikesVO selectLikes(@Param("likes")LikesVO likes);

	void insertLikes(@Param("likes")LikesVO likes);

	void updateBoardLikes(@Param("likes")LikesVO likes);

	void updateLikes(@Param("likes")LikesVO likes);

	List<MovieBoardVO> getLockerList(@Param("cri")Criteria cri, @Param("user")MemberVO user);

}
