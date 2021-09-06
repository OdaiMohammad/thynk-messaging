package ae.accumed.thynkrequestsexecutor.service;

import com.accumed.ws.wsinterface.rulesengine.service.ScrubResponseReturn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ThynkRequestService {
    private static final Logger logger = LoggerFactory.getLogger(ThynkRequestService.class);
    private final ThynkRuleEngineService thynkRuleEngineService;
    private final MongoTemplate mongoTemplate;
    private final KafkaService kafkaService;

    @Value("${spring.application.name}")
    private String consumerName;

    @Autowired
    public ThynkRequestService(ThynkRuleEngineService thynkRuleEngineService, MongoTemplate mongoTemplate, KafkaService kafkaService) {
        this.thynkRuleEngineService = thynkRuleEngineService;
        this.mongoTemplate = mongoTemplate;
        this.kafkaService = kafkaService;
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
            String restrictPackagesJsonString = payloadJson.get("systemPackageId").asText();
            List<String> restrictPackagesList = new ArrayList<>();
            try {
                restrictPackagesList =  mapper.readValue(restrictPackagesJsonString, new TypeReference<List<String>>(){});
            } catch (Exception e) {
                logger.error("Failed to read restrict packages. Falling back to default...");
            }
            String restrictPackages = String.join(",", restrictPackagesList);
            Object results = thynkRuleEngineService.validateClaim(payloadJson.get("requestData").toString(), restrictPackages);
            if (results instanceof ScrubResponseReturn && ((ScrubResponseReturn) results).getOutcome() != null) {
                if (((ScrubResponseReturn) results).getOutcome()[0].getShortMsg().toLowerCase().contains("server exhausted")) {
                    kafkaService.sendVipThynkRequest(message);
                } else
                    saveResults(payloadJson, priority, id, results);
            } else
                saveResults(payloadJson, priority, id, results);
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }

    private void saveResults(ObjectNode payloadJson, int priority, String id, Object results) {
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
    }
}
