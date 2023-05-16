package pack00.project;

import java.util.Scanner;

public class projectMain {
	static projectDTO dto = null;
	
	public void displayMenu() {
		System.out.println("로그인 : 1");
		System.out.println("회원가입 : 2");
		System.out.println("내 선수 검색 : 3");//select => CREATE
		System.out.println("스쿼드 선택 : 4");//insert => Read
		System.out.println("상점 : 구매 : 5");//update
		System.out.println("상점 : 판매 : 6");//delete
		System.out.println("보유 BP 조회 : 7");
		System.out.println("BP 충전 : 8");
		System.out.println("로그아웃 : 9");
		}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		projectMain pm = new projectMain();
		pm.displayMenu();
		projectDAO dao = new projectDAO();
		System.out.println("번호를 입력해주세요.");
		String num = sc.nextLine();
		if(num.equals("1")) {
			System.out.println("아이디를 입력해주세요");
			String MEMBER_ID = sc.nextLine();
			System.out.println("비밀번호를 입력해주세요");
			String MEMBER_PASSWORD = sc.nextLine();
			dao.login(MEMBER_ID, MEMBER_PASSWORD);
		}else if(num.equals("2")) {
			System.out.println("가입할 아이디를 입력해주세요");
			String MEMBER_ID = sc.nextLine();			
			System.out.println("가입하실 비밀번호를 입력해주세요.");
			String MEMBER_PASSWORD =sc.nextLine();
			System.out.println("이름을 입력해주세요.");
			String MEMBER_NAME = sc.nextLine();
			System.out.println("나이를 입력해주세요.");
			String MEMBER_AGE = sc.nextLine();
			System.out.println("충전하실 BP를 입력해주세요");
			String MEMBER_BP = sc.nextLine();
			dao.member(MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_AGE, MEMBER_BP);
		}else if(num.equals("3")) {
		
		}
		else if(num.equals("4")) {
			
		}else if(num.equals("5")) {
			System.out.println("아이디를 입력하세요");
			String MEMBER_ID = sc.nextLine();
			System.out.println("구입할 선수의 이름을 입력해주세요");
			String PLAYER_NAME = sc.nextLine();
			dao.purchase(MEMBER_ID, PLAYER_NAME);
		}else if(num.equals("6")) {
			System.out.println("아이디를 입력하세요");
			String MEMBER_ID = sc.nextLine();
			System.out.println("판매할 선수의 이름을 입력해주세요");
			String PLAYER_NAME = sc.nextLine();
			dao.sell(MEMBER_ID, PLAYER_NAME);
		}
		else if(num.equals("7")) {
			System.out.println("보유 BP 조회");
			System.out.println("아이디를 입력하세요");
			String MEMBER_ID = sc.nextLine();
			dao.checkBP(MEMBER_ID);
		}else if(num.equals("8")) {
			System.out.println("아이디를 입력하세요.");
			String MEMBER_ID = sc.nextLine();
			System.out.println("충전하실 BP의 값을 입력해주세요.");
			int MEMBER_BP = Integer.parseInt(sc.nextLine());
			dao.chargeBP(MEMBER_BP, MEMBER_ID);
			
		}
	}
	}
	
	
