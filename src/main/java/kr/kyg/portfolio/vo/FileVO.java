package kr.kyg.portfolio.vo;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileVO {
	private int fi_num;
	private String fi_ori_name;
	private String fi_name;
	private int fi_mv_num;
	private String fi_del;
	private Date fi_del_date;
	private String fi_type;
	public FileVO(String fi_ori_name, String fi_name, int fi_mv_num, String fi_type) {
		this.fi_ori_name = fi_ori_name;
		this.fi_name = fi_name;
		this.fi_mv_num = fi_mv_num;
		this.fi_type = fi_type;
	}
	
	
}
