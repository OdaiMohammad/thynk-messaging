package ae.accumed.thynkrequestsexecutor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaService {
    private final KafkaTemplate<String, JsonNode> kafkaTemplate;

    @Value("${topic.vip.name}")
    private String vipTopic;

    @Autowired
    public KafkaService(KafkaTemplate<String, JsonNode> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendVipThynkRequest(String message) throws JsonProcessingException {
        kafkaTemplate.send(vipTopic, new ObjectMapper().readTree(message));
    }
}
