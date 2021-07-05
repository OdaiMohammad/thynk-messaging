package ae.accumed.thynkrequestsprioritizor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ThynkRequestPrioritizor {

    @Value("${topic.vip.name}")
    private String vipTopic;

    @Value("${topic.normal.name}")
    private String normalTopic;

    @Value("${topic.bulk.name}")
    private String bulkTopic;

    private final KafkaTemplate<String, JsonNode> kafkaTemplate;

    @Autowired
    public ThynkRequestPrioritizor(KafkaTemplate<String, JsonNode> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(id = "prioritization-consumer", topics = "thynk.thynkrequest")
    private void listener(String message, Acknowledgment acknowledgment) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode messageJson = (ObjectNode) mapper.readTree(message);
            String payloadString = messageJson.get("payload").asText();
            ObjectNode payloadJson = (ObjectNode) mapper.readTree(payloadString);
            int priority = payloadJson.get("priority").asInt();
            switch (priority) {
                case 1:
                    kafkaTemplate.send(vipTopic, messageJson);
                    break;
                case 2:
                    kafkaTemplate.send(normalTopic, messageJson);
                    break;
                case 3:
                    kafkaTemplate.send(bulkTopic, messageJson);
                    break;
                default:
                    break;
            }
            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
