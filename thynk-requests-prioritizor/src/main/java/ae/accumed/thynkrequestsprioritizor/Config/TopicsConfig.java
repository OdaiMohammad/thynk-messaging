package ae.accumed.thynkrequestsprioritizor.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicsConfig {

    @Value("${topic.vip.name}")
    private String vipTopic;
    @Value("${topic.vip.partitions}")
    private int vipTopicPartitions;
    @Value("${topic.vip.replicas}")
    private int vipTopicReplicas;

    @Value("${topic.normal.name}")
    private String normalTopic;
    @Value("${topic.normal.partitions}")
    private int normalTopicPartitions;
    @Value("${topic.normal.replicas}")
    private int normalTopicReplicas;

    @Value("${topic.bulk.name}")
    private String bulkTopic;
    @Value("${topic.bulk.partitions}")
    private int bulkTopicPartitions;
    @Value("${topic.bulk.replicas}")
    private int bulkTopicReplicas;

    @Value("${topic.thynk.request.name}")
    private String thynkRequestTopic;
    @Value("${topic.thynk.request.partitions}")
    private int thynkRequestPartitions;
    @Value("${topic.thynk.request.replicas}")
    private int thynkRequestReplicas;

    @Bean
    public NewTopic vipTopic() {
        return TopicBuilder.name(vipTopic)
                .partitions(vipTopicPartitions)
                .replicas(vipTopicReplicas)
                .build();
    }

    @Bean
    public NewTopic normalTopic() {
        return TopicBuilder.name(normalTopic)
                .partitions(normalTopicPartitions)
                .replicas(normalTopicReplicas)
                .build();
    }

    @Bean
    public NewTopic bulkTopic() {
        return TopicBuilder.name(bulkTopic)
                .partitions(bulkTopicPartitions)
                .replicas(bulkTopicReplicas)
                .build();
    }

    @Bean
    public NewTopic thynkRequestsTopic() {
        return TopicBuilder.name(thynkRequestTopic)
                .partitions(thynkRequestPartitions)
                .replicas(thynkRequestReplicas)
                .build();
    }
}
