package ae.accumed.thynkrequestsexecutor;

import ae.accumed.thynkrequestsexecutor.service.ThynkRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ThynkRequestExecutor {


    private static final String VIP_CONSUMER_ID = "thynk-request-vip-consumer";
    private static final String NORMAL_CONSUMER_ID = "thynk-request-normal-consumer";
    private static final String BULK_CONSUMER_ID = "thynk-request-bulk-consumer";

    private static final String VIP_TOPIC = "thynk-request-vip";
    private static final String NORMAL_TOPIC = "thynk-request-normal";
    private static final String BULK_TOPIC = "thynk-request-bulk";

    private final ThynkRequestService thynkRequestService;
    private final KafkaListenerEndpointRegistry registry;

    @Value("${thynk.request.target.priority}")
    private int targetPriority;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ThynkRequestExecutor(KafkaListenerEndpointRegistry registry, ThynkRequestService thynkRequestService) {
        this.registry = registry;
        this.thynkRequestService = thynkRequestService;
    }

    @KafkaListener(id = VIP_CONSUMER_ID, topics = VIP_TOPIC)
    @KafkaListener(id = NORMAL_CONSUMER_ID, topics = NORMAL_TOPIC)
    @KafkaListener(id = BULK_CONSUMER_ID, topics = BULK_TOPIC)
    public void listenVIP(String message, ConsumerRecordMetadata meta, Acknowledgment acknowledgment) {
        pausePerPriority(meta);
        thynkRequestService.processMessage(message);
        acknowledgment.acknowledge();
    }

    private void pausePerPriority(ConsumerRecordMetadata meta) {
        if (targetPriority == 1) {
            if (meta.topic().equals(VIP_TOPIC)) {
                pauseListenerContainerIfRunning(NORMAL_CONSUMER_ID);
                pauseListenerContainerIfRunning(BULK_CONSUMER_ID);
            } else if (meta.topic().equals(NORMAL_TOPIC)) {
                pauseListenerContainerIfRunning(BULK_CONSUMER_ID);
            } else if (targetPriority == 2) {
                if (meta.topic().equals(NORMAL_TOPIC)) {
                    pauseListenerContainerIfRunning(VIP_CONSUMER_ID);
                    pauseListenerContainerIfRunning(BULK_CONSUMER_ID);
                } else if (meta.topic().equals(VIP_TOPIC)) {
                    pauseListenerContainerIfRunning(BULK_CONSUMER_ID);
                }
            } else if (targetPriority == 3) {
                if (meta.topic().equals(BULK_TOPIC)) {
                    pauseListenerContainerIfRunning(VIP_CONSUMER_ID);
                    pauseListenerContainerIfRunning(NORMAL_CONSUMER_ID);
                } else if (meta.topic().equals(VIP_TOPIC)) {
                    pauseListenerContainerIfRunning(NORMAL_CONSUMER_ID);
                }
            }
        }
    }

    @EventListener
    public void listen(ListenerContainerIdleEvent event) {
        resumePerPriority(event);
    }

    private void resumePerPriority(ListenerContainerIdleEvent event) {
        if (targetPriority == 1) {
            if (event.getListenerId().startsWith(VIP_CONSUMER_ID)) {
                resumeListenerContainerIfPaused(NORMAL_CONSUMER_ID);
            } else if (event.getListenerId().startsWith(NORMAL_CONSUMER_ID)) {
                resumeListenerContainerIfPaused(BULK_CONSUMER_ID);
            }
        } else if (targetPriority == 2) {
            if (event.getListenerId().startsWith(NORMAL_CONSUMER_ID)) {
                resumeListenerContainerIfPaused(VIP_CONSUMER_ID);
            } else if (event.getListenerId().startsWith(VIP_CONSUMER_ID)) {
                resumeListenerContainerIfPaused(BULK_CONSUMER_ID);
            }
        } else if (targetPriority == 3) {
            if (event.getListenerId().startsWith(BULK_CONSUMER_ID)) {
                resumeListenerContainerIfPaused(VIP_CONSUMER_ID);
            } else if (event.getListenerId().startsWith(VIP_CONSUMER_ID)) {
                resumeListenerContainerIfPaused(NORMAL_CONSUMER_ID);
            }
        }
    }

    private void pauseListenerContainerIfRunning(String id) {
        if (!registry.getListenerContainer(id).isContainerPaused())
            registry.getListenerContainer(id).pause();
    }

    private void resumeListenerContainerIfPaused(String id) {
        if (registry.getListenerContainer(id).isContainerPaused())
            registry.getListenerContainer(id).resume();
    }
}
