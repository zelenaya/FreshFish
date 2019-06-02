package fresh.fish.repository.jdbc_template;


import fresh.fish.domain.jdbc_template.User;
import fresh.fish.repository.GenericDao;

public interface UserDao extends GenericDao<User, Long> {

    User findByLogin(String login);

}
