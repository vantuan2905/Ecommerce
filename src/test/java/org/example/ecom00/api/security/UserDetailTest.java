package org.example.ecom00.api.security;

import org.example.ecom00.model.DAO.localUserDAO;
import org.example.ecom00.model.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Primary
public class UserDetailTest implements UserDetailsService {
    /** The Local User DAO. */
    @Autowired
    private localUserDAO l;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> opUser = l.findByUsernameIgnoreCase(username);
        if (opUser.isPresent())
            return  opUser.get();
        return null;

    }
}
