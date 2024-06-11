package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.UserDTO;
import com.meusprojetos.Game.List.entities.Role;
import com.meusprojetos.Game.List.entities.User;
import com.meusprojetos.Game.List.projections.UserDetailsProjection;
import com.meusprojetos.Game.List.repositories.UserRepository;
import com.meusprojetos.Game.List.utils.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserUtil customUserUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if (result.size() == 0) {
            throw new UsernameNotFoundException("User not found!");
        }
        User user = new User();
        user.setEmail(username);

        user.setpassword(result.get(0).getPassword());

        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User user =  authenticated();
        return new UserDTO(user);
    }

    protected User authenticated() {
        try {
            String username = customUserUtil.getLoggerUsername();
            return userRepository.findByEmail(username);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Email não encontrado");
        }
    }
}
