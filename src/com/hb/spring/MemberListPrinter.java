package com.hb.spring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberListPrinter {

	private MemberDAO dao;
	private MemberPrinter printer;
	
	public MemberListPrinter() {
		
	}
	@Autowired(required = false)
	public MemberListPrinter(MemberDAO memberDao1, MemberPrinter printer) {
		this.dao = memberDao1;
		this.printer = printer;
	}
	
	public void printAll(){
		Collection<MemberVO> members = dao.selectAll();
		for(MemberVO vo : members){
			printer.print(vo);
		}
	}
	
}



