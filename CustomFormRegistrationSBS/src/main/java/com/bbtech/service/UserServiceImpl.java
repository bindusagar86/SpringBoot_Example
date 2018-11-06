package com.bbtech.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bbtech.model.Role;
import com.bbtech.model.User;
import com.bbtech.repository.UserRepository;
import com.bbtech.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=repo.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
	}

	@Override
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public User save(UserRegistrationDto regUser) {
		User user=new User();
		user.setFirstName(regUser.getFirstName());
		user.setLastName(regUser.getLastName());
		user.setEmail(regUser.getEmail());
		user.setPassword(encoder.encode(regUser.getPassword()));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		
		
		return repo.save(user);
	}

}
