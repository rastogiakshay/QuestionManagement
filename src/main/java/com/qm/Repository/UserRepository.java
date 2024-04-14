package com.qm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.qm.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User getUserByEmail(String userEmail) throws UsernameNotFoundException;
	

}
