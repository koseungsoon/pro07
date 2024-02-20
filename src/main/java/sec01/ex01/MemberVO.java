package sec01.ex01;

import java.sql.Date;

import lombok.Data;


@Data

public class MemberVO {

	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joinDate;
	
	MemberVO(){
		System.out.println("MemberVO() 기본생성자 호출됨");
	}
}
