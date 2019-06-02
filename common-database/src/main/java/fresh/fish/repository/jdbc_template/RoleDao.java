package fresh.fish.repository.jdbc_template;


import fresh.fish.domain.jdbc_template.Role;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface RoleDao extends GenericDao<Role, Long> {

    List<Role> getRolesByUserId(Long userId);
}
