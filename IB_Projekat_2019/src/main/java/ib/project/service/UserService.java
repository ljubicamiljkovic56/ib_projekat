package ib.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ib.project.model.Authority;
import ib.project.model.User;
import ib.project.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
    
    public User dodajUsera(User user) {
    	User user1 = new User();
    	user1.setId(user.getId());
    	user1.setPassword(user.getPassword());
    	user1.setCertificate(user.getCertificate());
    	user1.setEmail(user.getEmail());
    	user1.setEnabled(true);
    	user1.setAuthorities((List<Authority>) user.getAuthorities());
    	userRepository.save(user1);
		return user1;
    }
    
}

