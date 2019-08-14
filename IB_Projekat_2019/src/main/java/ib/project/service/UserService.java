package ib.project.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ib.project.model.User;
import ib.project.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
    
    public User dodajUsera(User user) {
    	User user1 = new User();
    	user1.setId(user.getId());
    	user1.setUsername(user.getUsername());
    	user1.setPassword(user.getPassword());
    	user1.setCertificate(user.getCertificate());
    	user1.setEmail(user.getEmail());
    	user1.setActive(false);
    	user1.setAuthority(user.getAuthority());
    	userRepository.save(user);
		return user1;
    }
    
    
    public String odobriUsera(String username) {
    	User u  = userRepository.findByUsername(username);
    	u.setActive(true);
    	userRepository.save(u);
    	return u.getUsername();
    }
    
    public ArrayList<User> getAll() {
    	return  (ArrayList<User>) userRepository.findAll();
    }
    
    public String proveriUsera(String username, String password) {
    	User user2 = userRepository.findByUsername(username);
    	if(user2 == null) {
    		System.out.println("Nema korisnika sa tim usernamom");
    	}else if (user2.getPassword().equals(password)){
    		if(user2.getAuthority().equals("Regular")) {
    			System.out.println("Ulogovali ste se kao regular");
    		}else {
    			System.out.println("Ulogovali ste se kao admin");
    		}
    	}else {
    		System.out.println("Greska");
    		
    	}
    	return user2.getAuthority();
    }
}

