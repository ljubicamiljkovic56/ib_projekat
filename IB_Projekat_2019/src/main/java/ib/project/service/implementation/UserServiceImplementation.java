package ib.project.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ib.project.model.User;
import ib.project.repository.UserRepository;
import ib.project.service.UserService;

import java.util.List;


//@Service
//public class UserServiceImplementation implements UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public User findByEmail( String email ) throws UsernameNotFoundException {
//        User u = userRepository.findByEmail( email );
//        return u;
//    }
//
//    public User findById( Integer id ) throws AccessDeniedException {
//        User u = userRepository.findOne( id );
//        return u;
//    }
//
//    public List<User> findAll() throws AccessDeniedException {
//        List<User> result = userRepository.findAll();
//        return result;
//    }


//}