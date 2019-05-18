package fresh.fish.repository;


import fresh.fish.domain.WaitingList;

import java.util.List;

public interface WaitingDao extends GenericDao <WaitingList, Long>{

    List<WaitingList> search(String query, Integer limit, Integer offset);
    List<Long> batchUpdate(List<WaitingList> waitingLists);

}
