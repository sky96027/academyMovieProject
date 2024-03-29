package kr.kyg.portfolio.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	public static String uploadFile(String uploadPath, String originalName, byte[] 	
			fileData)throws Exception{
		//UUID를 발급, 파일 이름이 중복돼도 해당 파일이 유일하게 되도록 발급하는 일련번호
		UUID uid = UUID.randomUUID();
		//파일명 앞에 UUID를 추가하는 코드. UUID_파일명
		String savedName = uid.toString() +"_" + originalName;
		//savedPath는 현재 날짜를 기준으로 된 폴더 경로 문자열
		//2022/01/14
		String savedPath = calcPath(uploadPath);
		//uploadPath와 savedPath로 이루어진 경로에 savedName인 빈 파일을 생성
		File target = new File(uploadPath + savedPath, savedName);
		//파일을 복사. fileData에 있는 파일을 target에 복사
		FileCopyUtils.copy(fileData, target);
		//savedPath와 savedName을 이용하여 문자열을 만듬
		String uploadFileName = makeIcon(savedPath, savedName);
		return uploadFileName;
	}
	//uploadPath를 기준으로 현재 날짜 경로를 알려주는 메소드
	//이 때, 현재 날짜 경로에 맞는 폴더가 없으면 폴더를 생성
	
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.separator+cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator 
            + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String datePath = monthPath + File.separator 
            + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;
 
	}
	public static void makeDir(String uploadPath, String... paths) {
		if(new File(uploadPath+paths[paths.length-1]).exists())
			return;
		for(String path : paths) {
			//폴더를 경로로 가진 임시 폴더 생성
			File dirPath = new File(uploadPath + path);
			//해당 경로에 임시 폴더와 같은 폴더가 없으면
			if( !dirPath.exists())
				//해당 폴더를 만듬
				dirPath.mkdir();
		}
	}
	private static String makeIcon(String path, String fileName)
        	throws Exception{
		String iconName = path + File.separator + fileName;
		return iconName.replace(File.separatorChar, '/');
	}
}
