package kr.kyg.portfolio.service;

import java.util.List;

import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.vo.CommentVO;
import kr.kyg.portfolio.vo.MemberVO;

public interface CommentService {
	boolean insertComment(CommentVO comment, MemberVO user);

	List<CommentVO> selectCommentList(Integer co_mv_num, Criteria cri);

	int selectTotalCount(Integer co_mv_num);

	String deleteComment(Integer co_num, MemberVO user);

	String updateComment(CommentVO comment, MemberVO user);
}
