package fresh.fish.repository.jdbc_template;


import fresh.fish.domain.jdbc_template.WaitingList;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface WaitingDao extends GenericDao<WaitingList, Long> {

    List<WaitingList> search(String query, Integer limit, Integer offset);
    List<Long> batchUpdate(List<WaitingList> waitingLists);

}
