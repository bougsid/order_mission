package com.bougsid.login;

import com.bougsid.employe.Employe;
import com.bougsid.employe.EmployeProfile;
import com.bougsid.employe.EmployeUserDetails;
import com.bougsid.employe.IEmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
@Service
public class EmployeUserDetailsService implements UserDetailsService {

    @Autowired
    private IEmployeService employeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employe employe = this.employeService.findEmployeByUsername(username);
        System.out.println("Employe =" + employe);
        if (employe == null) {
            throw new UsernameNotFoundException("Invalid Username");
        }
        EmployeUserDetails employeUserDetails = new EmployeUserDetails();
        employeUserDetails.setEmploye(employe);
        employeUserDetails.setAuthorities(getGrantedAuthorities(employe));
        return employeUserDetails;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Employe employe) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (EmployeProfile employeProfile : employe.getEmployeProfiles()) {
            System.out.println("UserProfile : " + employeProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + employeProfile.getType()));
        }
        System.out.print("authorities :" + authorities);
        return authorities;
    }
}
