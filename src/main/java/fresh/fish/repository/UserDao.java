package fresh.fish.repository;


import fresh.fish.domain.User;

public interface UserDao extends GenericDao<User, Long> {

    User findByLogin(String login);

}
