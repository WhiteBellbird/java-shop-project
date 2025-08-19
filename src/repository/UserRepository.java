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
	
	LocalDateTime saveLoginTime(User user1, LocalDateTime now);
	
	LocalDateTime saveLogoutTime(User user1, LocalDateTime now);
	
	
	List<LocalDateTime> findLoginByDay(User user1, int year, int month, int day) ;
	
	List<LocalDateTime> findLoginByMonth(User user1,int year, int moneth);
	
	List<LocalDateTime> findLogoutByDay(User user1,int year, int month, int day);
	
	List<LocalDateTime> findLogoutByMonth(User user1, int year, int moneth);
	
	
	List<LocalDateTime> getLoginTime(User user1);
	
	List<LocalDateTime> getLogoutTime(User user1);
	
	
	List<User> getUsersList();
	
	public void resetData();
	
}
