package ae.accumed.thynkrequestsexecutor.service;

import ae.accumed.thynkrequestsexecutor.engine.RuleResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ThynkRequestService {
    private static final Logger logger = LoggerFactory.getLogger(ThynkRequestService.class);
    private final ThynkRuleEngineService thynkRuleEngineService;
    private final MongoTemplate mongoTemplate;


    @Value("${spring.application.name}")
    private String consumerName;

    @Autowired
    public ThynkRequestService(ThynkRuleEngineService thynkRuleEngineService, MongoTemplate mongoTemplate) {
        this.thynkRuleEngineService = thynkRuleEngineService;
        this.mongoTemplate = mongoTemplate;
    }

    public void processMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode json = (ObjectNode) mapper.readTree(message);
            String payloadString = json.get("payload").asText();
            ObjectNode payloadJson = (ObjectNode) mapper.readTree(payloadString);
            int priority = payloadJson.get("priority").asInt();
            String id = payloadJson.get("_id").get("$oid").asText();
            logger.info("Received message with id {} and priority {}", id, priority);
            logger.info("Processing message with id {} and priority {}", id, priority);

            List<RuleResult> results = thynkRuleEngineService.validateClaim(payloadJson.get("requestData").toString());

            payloadJson.put("updatedAt", new Date().getTime());
            logger.info("Processed message with id {} and priority {}", id, priority);
            logger.info("Saving result..");
            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), new Update()
                            .set("updatedAt", Instant.now().toEpochMilli())
                            .set("status", "finished")
                            .set("consumerName", consumerName)
                            .set("requestResults", results),
                    "thynkrequest");

            logger.info("Result saved to MongoDB");
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }
}
