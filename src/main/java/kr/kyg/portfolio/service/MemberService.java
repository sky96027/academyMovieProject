package kr.kyg.portfolio.service;

import kr.kyg.portfolio.vo.MemberVO;

public interface MemberService {

	MemberVO login(MemberVO member);

	boolean signup(MemberVO user);

	boolean idDuplicated(String id);

	MemberVO updateMember(MemberVO input, MemberVO user);

	void updateAutoLogin(MemberVO user);

	MemberVO selectMemberBySessionId(String value);

	String findId(MemberVO member);

	String findPw(MemberVO member);


}
