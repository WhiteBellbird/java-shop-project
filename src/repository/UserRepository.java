package repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;
import domain.User;

public interface UserRepository {

	User saveUser(User user);
	
	User findUserByEmail(String email);
	
	User findUserByUserId(String userId);
	
	User authorizeUser(User user);
	
	User replaceUser(User previousUser, User changedUser);
	
	LocalDateTime saveLoginTime(String email, LocalDateTime now);
	
	LocalDateTime saveLogoutTime(String email, LocalDateTime now);
	
	
	List<LocalDateTime> findLoginByDay(String email, int year, int month, int day) ;
	
	List<LocalDateTime> findLoginByMonth(String email,int year, int moneth);
	
	List<LocalDateTime> findLogoutByDay(String email,int year, int month, int day);
	
	List<LocalDateTime> findLogoutByMonth(String email, int year, int moneth);
	
	
	List<LocalDateTime> getLoginTime(String email);
	
	List<LocalDateTime> getLogoutTime(String email);
	
	
	List<User> getUsersList();
	
	public void resetData();
	
}
