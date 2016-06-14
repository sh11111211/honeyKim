package com.hb.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.hb.spring.AlreadyExistingMemberException;
import com.hb.spring.ChangePasswordService;
import com.hb.spring.IdPasswordNotMatchingException;
import com.hb.spring.MemberInfoPrinter;
import com.hb.spring.MemberListPrinter;
import com.hb.spring.MemberNotFoundException;
import com.hb.spring.MemberRegisterService;
import com.hb.spring.RegisterRequest;
import com.hb.spring.VersionPrinter;

public class Main1 {

	// private static Assembler asm = new Assembler();
	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {

		ctx = new GenericXmlApplicationContext(
				"classpath:/com/hb/main/appCtx1.xml");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("명령어를 입력하세요.");
			// String command = scan.next();
			String command = reader.readLine();
			if (command.equals("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			if (command.startsWith("new ")) {
				// regist 로직
				processNewCommand(command.split(" "));
				continue;
			} else if (command.startsWith("change ")) {
				// changePassword 로직
				processChangeCommand(command.split(" "));
				continue;
			} else if (command.startsWith("list")) {
				processListCommand(command.split(" "));
				continue;
			} else if (command.startsWith("info ")) {
				processInfoCommand(command.split(" "));
				continue;
			} else if (command.startsWith("version")) {
				processVersionCommand();
				continue;
			}
			printHelp();
		}

	}

	private static void processVersionCommand() {
		VersionPrinter printer = ctx.getBean("versionPrinter", VersionPrinter.class);
		printer.print();
	}

	private static void processInfoCommand(String[] split) {
		if (split.length != 2) {
			printHelp();
			return;
		}
		MemberInfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		infoPrinter.printMemberInfo(split[1]);
	}

	private static void processListCommand(String[] split) {
		MemberListPrinter mlp = ctx.getBean("listPrinter", MemberListPrinter.class);
		mlp.printAll();
	}

	private static void processNewCommand(String[] arg) {
		if (arg.length != 5) {
			printHelp();
			return;
		}
		// MemberRegisterService regSvc = asm.getRegSvc();
		MemberRegisterService regSvc = ctx.getBean("regSvc", MemberRegisterService.class);
		RegisterRequest reg = new RegisterRequest();
		reg.setEmail(arg[1]);
		reg.setName(arg[2]);
		reg.setPassword(arg[3]);
		reg.setConfirmPassword(arg[4]);

		if (!reg.isPasswordEqualToConfirmPassword()) {
			System.out.println("암호와 확인이 일치하지 않습니다");
			return;
		}
		try {
			regSvc.regist(reg);
			System.out.println("등록하였습니다");
		} catch (AlreadyExistingMemberException e) {
			System.out.println("이미 존재하는 이메일입니다.");
		}
	}

	private static void processChangeCommand(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		}
		// ChangePasswordService pwdSvc = asm.getPwdSvc();
		ChangePasswordService pwdSvc = ctx.getBean("pwdSvc", ChangePasswordService.class);
		try {
			pwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("등록하였습니다");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다.");
		} catch (IdPasswordNotMatchingException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다.");
		}
	}

	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요");
		System.out.println("명령어 사용법");
		System.out.println("new 이메일 이름 암호 암호확인");
		System.out.println("change 이메일 현재비번 변경비번");
		System.out.println();
	}

}
