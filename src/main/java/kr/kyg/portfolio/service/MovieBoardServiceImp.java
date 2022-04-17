package kr.kyg.portfolio.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.kyg.portfolio.dao.MovieBoardDAO;
import kr.kyg.portfolio.pagination.Criteria;
import kr.kyg.portfolio.util.UploadFileUtils;
import kr.kyg.portfolio.vo.FileVO;
import kr.kyg.portfolio.vo.LikesVO;
import kr.kyg.portfolio.vo.MemberVO;
import kr.kyg.portfolio.vo.MovieBoardVO;

@Service
public class MovieBoardServiceImp implements MovieBoardService{
	@Autowired
	MovieBoardDAO movieBoardDao;
	String uploadPath = "D:\\java_kyg\\upload";

	
	private void uploadFiles(List<MultipartFile>files, Integer mv_num) {
		if(files == null)
			return;
		for(MultipartFile tmpFile : files) {
			if(tmpFile != null && tmpFile.getOriginalFilename().length() !=0) {
				uploadFile(tmpFile, mv_num, null);
			}
		}
	}
	private void uploadFile(MultipartFile file, Integer mv_num, String mv_type) {
		if(file != null && file.getOriginalFilename().length() !=0) {
			System.out.println(file.getOriginalFilename());
			try {
				String path = UploadFileUtils.uploadFile(
					uploadPath, file.getOriginalFilename(), file.getBytes());
				FileVO fileVo = 
					new FileVO(file.getOriginalFilename(), path, mv_num, mv_type);
				movieBoardDao.insertFile(fileVo);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	@Override
	public void insertMovieBoard(MovieBoardVO movieBoard, List<MultipartFile> files, MultipartFile thumb) {
		if(movieBoard == null 
				|| movieBoard.getMv_name() == null
				|| movieBoard.getMv_outline() == null
				|| movieBoard.getMv_me_id() ==null)
				return;
			
			movieBoardDao.insertMovieBoard(movieBoard);
			uploadFiles(files, movieBoard.getMv_num());
			uploadFile(thumb, movieBoard.getMv_num(),"Thumbnail");
		
	}
	@Override
	public List<MovieBoardVO> getBoardList(Criteria cri) {
		return movieBoardDao.getMovieBoardList(cri);
		
	}
	@Override
	public int getTotalCount(Criteria cri) {
		return movieBoardDao.selectCountMovieBoard(cri);
	}
	@Override
	public MovieBoardVO getMovieBoard(Integer mv_num) {
		//게시글 번호가 없거나 0이하이면 null을 반환
		//존재할수 없는 게시글을 가져오라고 시킴 
		if(mv_num == null || mv_num <= 0)
			return null;
		return movieBoardDao.getMovieBoard(mv_num);
	}
	@Override
	public List<FileVO> getFileList(Integer mv_num) {
		if(mv_num == null || mv_num <= 0)
			return null;
		return movieBoardDao.selectFileList(mv_num);
	}
	@Override
	public void deleteMovieBoard(Integer mv_num, MemberVO user) {
		//유효하지 않은 게시글 번호이면 삭제할 필요 없음
				//번호가 null이거나 음수, 0인 경우
				if(mv_num == null || mv_num <= 0)
					return;
				
				//게시글번호와 일치하는 게시글을 가져옴
				MovieBoardVO board = movieBoardDao.getMovieBoard(mv_num);
				
				//가져온 게시글이 null이면 삭제할 필요 없음
				if(board == null)
					return;
				
				//게시글 작성자와 로그인한 회원 아이디가 같은지 확인하여 다르면 삭제할 필요 없음
				//board.getmv_me_id()와 user.getMe_id()가 다르면
				if(!board.getMv_me_id().equals(user.getMe_id()))
					return;
				//게시글을 삭제
				//게시글의 mv_del을 Y로 수정
				//다오에게 수정된 게시글을 업데이트하라고 시킴
				//boardDao.게시글삭제(게시글 번호);
				movieBoardDao.deleteMovieBoard(mv_num);
				List<FileVO> fileList = movieBoardDao.selectFileList(mv_num);
				deleteFile(fileList);
				
				/*
				board.setMv_del("Y");
				board.setMv_del_date(new Date());
				boardDao.updateBoard(board);
				*/
		
	}
	private void deleteFile(List<FileVO> fileList) {
		if(fileList != null && fileList.size() != 0) {
			for(FileVO tmpFileVo : fileList) {
				File f = 
					new File(uploadPath+tmpFileVo.getFi_name().replace("/", File.separator));
				if(f.exists()) {
					f.delete();
				}
				movieBoardDao.deleteFile(tmpFileVo.getFi_num());
			}
		}
	}
	@Override
	public MovieBoardVO getMovieBoard(Integer mv_num, MemberVO user) {
		//게시글 번호가 유효한지 체크 => 번호가 없거나 0이하이면 작업할 필요 없음
				if(mv_num == null || mv_num <= 0)
					return null;
				//다오에게 게시글을 가져오라고 시킴
				//게시글 = 다오.게시글가져옴(게시글 번호)
				MovieBoardVO board = movieBoardDao.getMovieBoard(mv_num);
				//가져온 게시글이 있으면 작성자와 user와 비교하여 같은 아이디인지 체크
				if(board == null || !board.getMv_me_id().equals(user.getMe_id()))
					return null;
				return board;
	}
	@Override
	public void updateMovieBoard(MovieBoardVO board, List<MultipartFile> files2, Integer[] fileNums,  MultipartFile thumb) {
		//다오에게 게시글 번호와 일치하는 게시글을 가져오라고 시킴
				//게시글 = 다오.게시글가져오기(게시글번호)
				//MovieBoardVO dbBoard = movieBoardDao.getMovieBoard(board.getMv_num());
			
				//가져온 게시글의 제목과 내용을 board의 제목과 내용으로 덮어쓰기를 함
				//dbBoard.setMv_name(board.getMv_name());
				//dbBoard.setMv_reg_date(board.getMv_reg_date());
				//dbBoard.setMv_outline(board.getMv_outline());
				
				//가져온 게시글의 수정일을 현재 시간으로 업데이트
				//dbBoard.setMv_up_date(new Date());
				System.out.println(board);
				//다오에게 수정된 게시글 정보를 주면서 업데이트 하라고 시킴
				board.setMv_up_date(new Date());
				movieBoardDao.updateMovieBoard(board);
				
				//해당 게시글번호와 일치하는 첨부파일 전체를 가져옴
				List<FileVO> fileList = movieBoardDao.selectFileList(board.getMv_num());
				
				//가져온 첨부파일전체에서 fileNums에 없는 번호들의 첨부파일들을 서버에서 삭제
				if(fileList != null && fileList.size() != 0 
						&& fileNums != null && fileNums.length != 0) {
					List<FileVO> delList = new ArrayList<FileVO>();
					for(FileVO tmpFileVo : fileList) {
						for(Integer tmp: fileNums) {
							if(tmpFileVo.getFi_num() == tmp) {
								delList.add(tmpFileVo);
							}
						}
					}
					fileList.removeAll(delList);
				}
				//위의 조건문을 거치고 난 뒤 fileList는 삭제할 첨부파일들
				//DB에서도 삭제
				deleteFile(fileList);
				
				//새로 추가된 첨부파일 있으면 서버에 업로드
				//새로 추가된 첨부파일을 DB에 추가
				uploadFiles(files2, board.getMv_num());
				if(thumb == null || thumb.getOriginalFilename().length() == 0)
					return;
				FileVO tmp = movieBoardDao.selectFile(board.getMv_num(),"Thumbnail");
				if(tmp != null) {
					ArrayList<FileVO> list = new ArrayList<FileVO>();
					list.add(tmp);
					deleteFile(list);
				}
				uploadFile(thumb,board.getMv_num(),"Thumbnail");
	}
	@Override
	public String likes(LikesVO likes, MemberVO user) {
		if(likes == null || user == null)
			return "fail";
		//DB에서 해당 유저가 해당 게시글을 추천/비추천했는지 확인하기 위해 DB에서 가져옴
		LikesVO dbLikes = movieBoardDao.selectLikes(likes);
		System.out.println(dbLikes);
		//해당 게시글에 추천/비추천을 한적이 없을 때
		if(dbLikes == null) {
			movieBoardDao.insertLikes(likes);
			//해당 게시글에 추천,비추천 수를 계산
			movieBoardDao.updateBoardLikes(likes);
			return ""+likes.getLi_state();
		}
		//취소하는 경우
		if(dbLikes.getLi_state() == likes.getLi_state()) {
			likes.setLi_state(0);
			movieBoardDao.updateLikes(likes);
			movieBoardDao.updateBoardLikes(likes);
			return "0";
		}
		//추천 =>비추천 또는 비추천 =>추천으로 바뀌는 경우
		movieBoardDao.updateLikes(likes);
		movieBoardDao.updateBoardLikes(likes);
		return ""+likes.getLi_state();
	}
	@Override
	public String viewLikes(LikesVO likes, MemberVO user) {
		if(likes == null || user == null) {
			return "0";
		}
		LikesVO dbLikes = movieBoardDao.selectLikes(likes);
		if(dbLikes == null)
			return "0";
		return ""+dbLikes.getLi_state();
	}
	@Override
	public List<MovieBoardVO> getLockerList(Criteria cri, MemberVO user) {
		return movieBoardDao.getLockerList(cri, user);
	}
}
