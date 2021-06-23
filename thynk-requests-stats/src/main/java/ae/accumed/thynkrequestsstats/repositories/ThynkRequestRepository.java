package ae.accumed.thynkrequestsstats.repositories;

import ae.accumed.thynkrequestsstats.entities.ThynkRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThynkRequestRepository extends MongoRepository<ThynkRequest, String> {

    ThynkRequest findThynkRequestById(String id);
    ThynkRequest findThynkRequestByCreatedAtBetween(String startDate, String endDate);
    int countAllByStatusAndPriority(String status, int priority);
}
