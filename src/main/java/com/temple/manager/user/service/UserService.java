package com.temple.manager.user.service;

import com.temple.manager.user.entity.User;
import com.temple.manager.user.mapper.UserMapper;
import com.temple.manager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findAllByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return userMapper.entityToDTO(user);
    }
}
