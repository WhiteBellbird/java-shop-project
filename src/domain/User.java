package domain;

import java.io.Serializable;
import exception.InvalidatedInputException;
import exception.UserInvalidCoupon;

public class User{
	private String userId;
	private String username;
	private String email;
	private String password;
	private String rank;
	private String address;
	private int point;
	private String coupon;
	private String phone;
	private boolean isAdmin;
	
	
	public User(String userId, String username, String password, String email, String address, String phone) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isAdmin = false;
		this.rank = "SILVER"; // SILVER - GOLD - PLATINUM - DIAMOND
		this.address = address;
		this.point = 0;
		this.coupon = null; // 쿠폰은 한개만 소지가능
		this.phone = phone;
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
		if(point > 0 && (this.point - point < 0)) {
			this.point -= point;
		}else {
			throw new InvalidatedInputException("적절하지않은 포인트입력값입니다. 다시 확인하시고 입력해주십시오");
		}
	}
	//쿠폰을 가지고 있나의 여부에서 쿠폰 적용할지 안할지(관리자가 따로 고객에게 쿠폰지급) 아니면 고객이 주문시에 쿠폰아이디입력시 주문한 총가격에서 자동적용?
	//고객이 가지고있는 쿠폰은 REPOSITORY 파일에 저장된 쿠폰아이디와 일치여부 확인후 적용 
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
	public int getPoint() {
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
}
