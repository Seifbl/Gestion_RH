package com.fl2.gd.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fl2.gd.Entities.AppUser;
import com.fl2.gd.Entities.Employee;





public interface UserRepository extends JpaRepository<AppUser, Integer> {
   @Query("SELECT u FROM AppUser u JOIN u.authorities a WHERE a.authority = 'USER' "
   		+ "AND a.authority != 'ADMIN'")
   List<AppUser> findAllUsersWithUserRole();
   Optional<AppUser> findByUsername(String username);
   
   @Query("SELECT u FROM AppUser u WHERE u.username = :username ")
   AppUser findByUsernameNonOptional(String username);
   
   @Query("SELECT emp FROM Employee emp WHERE emp.appUser.userId = :id ")
   Employee getEmployeeInfoFromUser(int id);
   
   @Query("DELETE FROM AppUser u WHERE "
   		+ "u.userId = (SELECT e.appUser.userId FROM Employee e WHERE e.employeeId = :employeeId)")
   void deleteAppUserByEmployeeId(@Param("employeeId") int employeeId);

}

