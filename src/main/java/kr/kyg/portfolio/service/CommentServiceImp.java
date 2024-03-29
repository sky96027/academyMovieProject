package kr.kyg.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kyg.portfolio.dao.CommentDAO;
import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.vo.CommentVO;
import kr.kyg.portfolio.vo.MemberVO;

@Service
public class CommentServiceImp implements CommentService {
	@Autowired
	CommentDAO commentDao;

	@Override
	public boolean insertComment(CommentVO comment, MemberVO user) {
		if(user == null || comment == null)
			return false;
		//댓글 작성자에 로그인한 회원 아이디를 넣어줌
		comment.setCo_me_id(user.getMe_id());
		commentDao.insertComment(comment);
		return true;
	}

	@Override
	public List<CommentVO> selectCommentList(Integer co_mv_num, Criteria cri) {
		if(co_mv_num == null || co_mv_num <= 0 || cri==null)
			return null;
		return commentDao.selectCommentList(co_mv_num, cri);
	}

	@Override
	public int selectTotalCount(Integer co_mv_num) {
		if(co_mv_num == null || co_mv_num <= 0)
			return 0;
		return commentDao.selectTotalCountComment(co_mv_num);
	}

	@Override
	public String deleteComment(Integer co_num, MemberVO user) {
		if(co_num == null || co_num <=0 || user== null)
			return "false";
		CommentVO comment =commentDao.selectComment(co_num);
		if(comment == null || !comment.getCo_me_id().equals(user.getMe_id()))
			return "false";
		commentDao.deleteComment(co_num);
		return "true";
	}

	@Override
	public String updateComment(CommentVO comment, MemberVO user) {
		if(comment == null || user == null)
			return "false";
		CommentVO dbComment = commentDao.selectComment(comment.getCo_num());
		if(dbComment == null || !dbComment.getCo_me_id().equals(user.getMe_id()))
			return "false";
		commentDao.updateComment(comment);
		return "true";
	}
}
