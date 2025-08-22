package repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;
import domain.User;

public interface UserRepository {

	// commit: 현재 상태를 파일에 저장하고 tmpUsers 갱신
	void commit();

	// rollback: tmpUsers 상태로 되돌린 후 파일에 덮어쓰기
	void rollback();

	User saveUser(User user);
	
	User findUserByEmail(String email);
	
	User findUserByUserId(String userId);
	
	User findUserByUsername(String username);
	
	User authorizeUser(User user);
	
	User replaceUser(User previousUser, User changedUser);
	
	LocalDateTime saveLoginTime(String username, LocalDateTime now);
	
	LocalDateTime saveLogoutTime(String username, LocalDateTime now);
	
	
	List<LocalDateTime> findLoginByDay(String username, int year, int month, int day) ;
	
	List<LocalDateTime> findLoginByMonth(String username , int year, int moneth);
	
	List<LocalDateTime> findLogoutByDay(String username ,int year, int month, int day);
	
	List<LocalDateTime> findLogoutByMonth(String username , int year, int moneth);
	
	
	List<LocalDateTime> getLoginTime(String username);
	
	List<LocalDateTime> getLogoutTime(String username);
	
	
	List<User> getUsersList();
	
	public void resetData();

	void delete(User user);
}
