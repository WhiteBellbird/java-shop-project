package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import exception.InvalidatedInputException;
import exception.UserInvalidCoupon;
import java.time.LocalDateTime;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String username;
	private String email;
	private String password;
	private String rank;
	private String address;
	//적립 포인트
	private double point;
	private String coupon;
	private String phone;
	private boolean isAdmin;
	//아이디 만든 시간
	private LocalDateTime createdDate;
	//로그인한 시간
	private List<LocalDateTime> loginTime;
	//로그아웃한 시간
	private List<LocalDateTime> logoutTime;
	//로그인한 상태
	
	
	public User(String userId, String username, String email, String password, String rank, String address, double point,
			String coupon, String phone, boolean isAdmin, LocalDateTime createdDate,
			List<LocalDateTime> loggedInTime, List<LocalDateTime> loggedOutTime) {
		//super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.rank = rank;
		this.address = address;
		this.point = point;
		this.coupon = coupon;
		this.phone = phone;
		this.isAdmin = isAdmin;
		this.createdDate = createdDate;
		this.loginTime = loggedInTime;
		this.logoutTime = loggedOutTime;
	}
	
	public static User createUser(String userId, String username, String email, String password, String phone) {
		User user = new User(userId, username, email, password, username, email, 0, password, phone, false, 
				LocalDateTime.now(), new ArrayList<LocalDateTime>(), new ArrayList<LocalDateTime>());
		return user;
	}
	public void updatePassword (String password) {
		if(password != null && !password.isEmpty()) {
			this.password = password;
		}
	}
	public void accumulatePoint(int point) throws InvalidatedInputException{
		if(point > 0) {
			this.point += point;
		}else {
			throw new InvalidatedInputException("적절하지않은 포인트입력값입니다. 다시 확인하시고 입력해주십시오");
		}
	}
	public void removePoint(int point) throws InvalidatedInputException{
		if(point > 0 && !(this.point - point < 0)) {
			this.point -= point;
		}else {
			throw new InvalidatedInputException("적절하지않은 포인트입력값입니다. 다시 확인하시고 입력해주십시오");
		}
	}
	//쿠폰을 가지고 있나의 여부에서 쿠폰 적용할지 안할지(관리자가 따로 고객에게 쿠폰지급) 
	//아니면
	//고객이 주문시에 쿠폰아이디입력시 주문한 총가격에서 자동적용? -고객이 가지고있는 쿠폰은 REPOSITORY 파일에 저장된 쿠폰아이디와 일치여부 확인후 적용 
	public void addCoupon(String couponId) {
		this.coupon = couponId;
	}
	public void removeCoupon() {
		this.coupon = null;
	}
	public void promoteRank() {
		if(this.rank.equals("SILVER")) {
			this.rank = "GOLD";
		}else if(this.rank.equals("GOLD")) {
			this.rank = "PLATINUM";
		}else if(this.rank.equals("PLATINUM")) {
			this.rank = "DIAMOND";
		}else if(this.rank.equals("DIAMOND")) {
			return;
		}
	}
	public void DemoteRank() {
		if(this.rank.equals("SILVER")) {
			return;	
		}else if(this.rank.equals("GOLD")) {
			this.rank = "SILVER";		
		}else if(this.rank.equals("PLATINUM")) {
			this.rank = "GOLD";
		}else if(this.rank.equals("DIAMOND")) {
			this.rank = "PLATINUM";
		}
	}
	public void giveManagerAuthentication() {
		// 이미 관리자면 메서드 종료
		if(this.isAdmin) return;
		// 관리자로 승격
		this.isAdmin = true;
	} 
	public String getRank() {
		return rank;
	}
	public String getAddress() {
		return address;
	}
	public double getPoint() {
		return point;
	}
	public String getCoupon() {
		return coupon;
	}
	public String getPhone() {
		return phone;
	}
	public String getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public List<LocalDateTime> getLoginTime(){
		return this.loginTime;
	}
	public List<LocalDateTime> getLogoutTime(){
		return this.logoutTime;
	}
	public LocalDateTime saveLoginTime(){
		LocalDateTime time = LocalDateTime.now();
		this.loginTime.add(time);
		return time;
	}
	public LocalDateTime saveLogoutTime(){
		LocalDateTime time = LocalDateTime.now();
		this.loginTime.add(time);
		return time;
	}
	@Override
	public String toString() {
		return "userId: " + userId+ "계정이름: "+username+" | 등급: "+rank+" | 이메일: "+ email+" | 주소: "+address+" | 전화번호: "+phone + " | 관리자 여부: " + isAdmin();	
	}
	//아이디가 같으면 동등하게 취급 hash-code 비교는 불필요
	public boolean equals(User obj) {
		return (this.getUserId().equals(obj.getUserId()) && this.getUsername().equals(obj.getUsername()) 
				&& this.getEmail().equals(obj.getEmail()) && this.getRank().equals(obj.getRank())
				&& this.getUsername().equals(obj.getUsername()));
	}
}
