package fresh.fish.service;

import fresh.fish.domain.jdbc_template.Role;
import fresh.fish.domain.jdbc_template.User;
import fresh.fish.repository.jdbc_template.RoleDao;
import fresh.fish.repository.jdbc_template.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDao.findByLogin(username);
            List<Role> roles = roleDao.getRolesByUserId(user.getUserId());
            if(user.getUserId() == null){
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                return new org.springframework.security.core.userdetails.User(
                        user.getLogin(),
                        user.getPassword(),
                        AuthorityUtils.commaSeparatedStringToAuthorityList(roles.get(0).getRoleName())
                );
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
}