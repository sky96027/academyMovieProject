package kr.kyg.portfolio.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;

@Controller
public class AjaxController {
	@RequestMapping(value ="/ajax/test1")
	@ResponseBody
	public String ajaxTest1(String str){
	    System.out.println(str);
	    return "success : " + str;
	}
	@RequestMapping(value ="/ajax/test2")
	@ResponseBody
	public Map<String, Object> ajaxTest2(@RequestBody String str){
	    HashMap<String, Object>map = new HashMap<String, Object>();
	    map.put("name", "홍길동");
	    map.put("address", "청주시");
	    map.put("data", str);
	    return map;
	}
	@RequestMapping(value ="/ajax/test3")
	@ResponseBody
	public Map<String, Object> ajaxTest3(@RequestBody MemberDTO member){
	    HashMap<String, Object>map = new HashMap<String, Object>();
	    map.put("name", "홍길동");
	    map.put("address", "청주시");
	    map.put("data", member);
	    return map;
	}
}
@Data
class MemberDTO{
	String id;
	String pw;
}

















