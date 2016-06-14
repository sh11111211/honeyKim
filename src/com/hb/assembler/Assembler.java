package com.hb.assembler;

import com.hb.spring.ChangePasswordService;
import com.hb.spring.MemberDAO;
import com.hb.spring.MemberRegisterService;

public class Assembler {
	private MemberDAO memberDao;
	private MemberRegisterService regSvc;
	private ChangePasswordService pwdSvc;
	
	public Assembler() {
		memberDao = new MemberDAO();
		regSvc = new MemberRegisterService(memberDao);
		pwdSvc = new ChangePasswordService(memberDao);
	}

	public MemberDAO getMemberDao() {
		return memberDao;
	}

	public MemberRegisterService getRegSvc() {
		return regSvc;
	}

	public ChangePasswordService getPwdSvc() {
		return pwdSvc;
	}
	
	
}






