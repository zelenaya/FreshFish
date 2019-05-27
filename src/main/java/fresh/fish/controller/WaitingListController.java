package fresh.fish.controller;



import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.controller.requests.WaitingListCreateRequest;
import fresh.fish.domain.jdbc_template.WaitingList;
import fresh.fish.repository.jdbc_template.WaitingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/waitinglists")
public class WaitingListController {

    @Autowired
    private WaitingDao waitingDao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<WaitingList>> getWaitingLists() {
        return new ResponseEntity<>(waitingDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <String> getWaitingListById(@PathVariable Long id) {
        WaitingList waitingList = waitingDao.findById(id);
        return new ResponseEntity<>(waitingList.toString(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WaitingList> createWaitingList(@RequestBody WaitingListCreateRequest request) {
        WaitingList waitingList = new WaitingList();
        setFields(waitingList, request);

        WaitingList savedWaitingList = waitingDao.save(waitingList);
        return new ResponseEntity<>(savedWaitingList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateWaitingList(@PathVariable("id")  Long waitId, @RequestBody WaitingListCreateRequest request) {

        WaitingList waitingList = waitingDao.findById(waitId);
        setFields(waitingList, request);
        waitingDao.update(waitingList);

        return new ResponseEntity<>(waitingList.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteProduct (@PathVariable ("id") Long waitId) {
        waitingDao.delete(waitId);
        return new ResponseEntity<>(waitId, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WaitingList>> searchWaitingList(@ModelAttribute SearchCriteria criteria) {
        List <WaitingList> searchResult = waitingDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(WaitingList wList, WaitingListCreateRequest req) {
        wList.setCustomerId(req.getCustomerId());
        wList.setProductId(req.getProductId());
        wList.setCount(req.getCount());
    }

}
