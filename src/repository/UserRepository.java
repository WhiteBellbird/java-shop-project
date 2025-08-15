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
	
	LocalDateTime saveLoginTime(LocalDateTime now);
	
	LocalDateTime saveLogoutTime(LocalDateTime now);
	
	
	List<LocalDateTime> findLoginByDay(int year, int month, int day) ;
	
	List<LocalDateTime> findLoginByMonth(int year, int moneth);
	
	List<LocalDateTime> findLogoutByDay(int year, int month, int day);
	
	List<LocalDateTime> findLogoutByMonth(int year, int moneth);
	
	
	List<LocalDateTime> getLoginTime();
	
	List<LocalDateTime> getLogoutTime();
	
	
	List<User> getUsersList();
	
	public void resetData();
	
}
