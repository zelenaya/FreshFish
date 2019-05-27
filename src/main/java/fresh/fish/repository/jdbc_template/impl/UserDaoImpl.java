package fresh.fish.repository.jdbc_template.impl;

import fresh.fish.domain.jdbc_template.User;
import fresh.fish.repository.jdbc_template.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    public static final String USER_ID = "user_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }
}
