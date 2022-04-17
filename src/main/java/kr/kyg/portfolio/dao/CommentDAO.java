package kr.kyg.portfolio.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.vo.CommentVO;

public interface CommentDAO {

	void insertComment(@Param("comment")CommentVO comment);

	List<CommentVO> selectCommentList(@Param("co_mv_num")Integer co_mv_num, @Param("cri")Criteria cri);

	int selectTotalCountComment(@Param("co_mv_num")Integer co_mv_num);

	CommentVO selectComment(@Param("co_num")Integer co_num);

	void deleteComment(@Param("co_num")Integer co_num);

	void updateComment(@Param("comment")CommentVO comment);

}
