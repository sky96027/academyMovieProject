package kr.kyg.portfolio.service;

import java.util.List;

import kr.kyg.portfolio.vo.MemberVO;


public interface AdminService {

	List<MemberVO> getMemberList();

	boolean updateAuthority(MemberVO member);

}
