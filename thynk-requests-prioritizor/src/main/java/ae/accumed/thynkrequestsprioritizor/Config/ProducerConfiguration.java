package ae.accumed.thynkrequestsprioritizor.Config;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.batch-size}")
    private String batchSize;

    @Value("${spring.kafka.producer.properties.partitioner.class}")
    private String partitionerClass;

    @Bean
    public ProducerFactory<String, JsonNode> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        configProps.put(
                ProducerConfig.PARTITIONER_CLASS_CONFIG,
                partitionerClass);
        configProps.put(
                ProducerConfig.BATCH_SIZE_CONFIG,
                batchSize);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, JsonNode> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
