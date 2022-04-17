package kr.kyg.portfolio.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MovieBoardVO {
	private int mv_num;
	private String mv_name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date mv_reg_date;
	private String mv_time;
	private String mv_me_id;
	private String mv_age;
	private String mv_director;
	private String mv_outline;
	private Date mv_up_date;
	private String mv_del;
	private Date mv_del_date;
	private int mv_views;
	private int mv_up;
	private int mv_down;
	private String mv_thumb;
	private String mv_genre;
	public String getMv_reg_date_str() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(mv_reg_date);
		return str;
	}

}
