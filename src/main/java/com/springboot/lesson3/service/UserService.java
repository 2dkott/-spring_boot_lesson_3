package com.springboot.lesson3.service;


import com.springboot.lesson3.model.LibRole;
import com.springboot.lesson3.model.LibUser;
import com.springboot.lesson3.repository.LibRoleRepository;
import com.springboot.lesson3.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibRoleRepository roleRepository;

    @PostConstruct
    public void generateValue() {
        LibRole adminLibRole = new LibRole("admin");
        LibRole readerRole = new LibRole("reader");
        roleRepository.save(adminLibRole);
        roleRepository.save(readerRole);
        LibUser admin = new LibUser("admin", "pwd", List.of(adminLibRole));
        LibUser reader = new LibUser("reader", "pwd", List.of(readerRole));
        userRepository.save(admin);
        userRepository.save(reader);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LibUser libUser = userRepository.findByName(username).orElseThrow();
        List<SimpleGrantedAuthority> roles = libUser.getLibRoleList().stream().map(libRole -> new SimpleGrantedAuthority(libRole.getName())).toList();
        return new User(libUser.getName(), libUser.getPwd(), roles);
    }
}
