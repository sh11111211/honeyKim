package com.hb.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoPrinter {
	
	private MemberDAO dao;
	@Autowired(required = false)
	private MemberPrinter printer;
	
	@Autowired
	public void setDao(@Qualifier("sysout") MemberDAO dao) {
		this.dao = dao;
	}
	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
	
	public void printMemberInfo(String email){
		MemberVO vo = dao.selectByEmail(email);
		if(vo == null){
			System.out.println("데이터 없음");
			return;
		}
		printer.print(vo);
		System.out.println();
	}
	
}
