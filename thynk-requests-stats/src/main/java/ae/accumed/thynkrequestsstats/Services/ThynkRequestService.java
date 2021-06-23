package ae.accumed.thynkrequestsstats.Services;


import ae.accumed.thynkrequestsstats.entities.ThynkRequest;
import ae.accumed.thynkrequestsstats.repositories.ThynkRequestRepository;
import ae.accumed.thynkrequestsstats.response.StatsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ThynkRequestService {
    private final MongoTemplate mongoTemplate;
    private final ThynkRequestRepository thynkRequestRepository;

    @Autowired
    public ThynkRequestService(MongoTemplate mongoTemplate, ThynkRequestRepository thynkRequestRepository) {
        this.mongoTemplate = mongoTemplate;
        this.thynkRequestRepository = thynkRequestRepository;
    }

    public List<ThynkRequest> findThynkRequestByCreatedAtBetween(String startDate, String endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createdAt").gte(startDate).lte(endDate));
        return mongoTemplate.find(query, ThynkRequest.class);
    }

    public StatsResponse getStats() {
        int pendingVipCount = 0;
        int pendingNormalCount = 0;
        int pendingBulkCount = 0;
        int finishedVipCount = 0;
        int finishedNormalCount = 0;
        int finishedBulkCount = 0;
        try {
            pendingVipCount = thynkRequestRepository.countAllByStatusAndPriority("pending", 1);
            pendingNormalCount = thynkRequestRepository.countAllByStatusAndPriority("pending", 2);
            pendingBulkCount = thynkRequestRepository.countAllByStatusAndPriority("pending", 3);
            finishedVipCount = thynkRequestRepository.countAllByStatusAndPriority("finished", 1);
            finishedNormalCount = thynkRequestRepository.countAllByStatusAndPriority("finished", 2);
            finishedBulkCount = thynkRequestRepository.countAllByStatusAndPriority("finished", 3);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error retrieving thynk requests stats", e);
        }catch (Exception e) {
            log.error("Unknown error occurred", e);
        }
        Map<String, Integer> stats = new HashMap<>();
        stats.put("pendingVipCount", pendingVipCount);
        stats.put("pendingNormalCount", pendingNormalCount);
        stats.put("pendingBulkCount", pendingBulkCount);
        stats.put("finishedVipCount", finishedVipCount);
        stats.put("finishedNormalCount", finishedNormalCount);
        stats.put("finishedBulkCount", finishedBulkCount);
        stats.put("pendingTotal", pendingVipCount + pendingBulkCount + pendingNormalCount);
        stats.put("finishedTotal", finishedVipCount + finishedNormalCount + finishedBulkCount);
        return new StatsResponse(stats);
    }
}
