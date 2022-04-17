package kr.kyg.portfolio.vo;

import java.util.Date;

import kr.kyg.portfolio.vo.CommentVO;
import lombok.Data;

@Data
public class CommentVO {
	private int co_num;
	private int co_mv_num;
	private String co_me_id;
	private Date co_reg_date;
	private String co_del;
	private int co_ori_num;
	private String co_contents;
}
