package ae.accumed.thynkrequestsprioritizor;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RetainingRoundRobinPartitioner implements Partitioner {
    private final ConcurrentMap<String, AtomicInteger> topicCounterMap = new ConcurrentHashMap();
    private Object lastValue;

    public RetainingRoundRobinPartitioner() {
    }

    public void configure(Map<String, ?> configs) {
    }

    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        int nextValue = this.nextValue(topic, value);
        List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
        if (!availablePartitions.isEmpty()) {
            int part = Utils.toPositive(nextValue) % availablePartitions.size();
            lastValue = value;
            return ((PartitionInfo) availablePartitions.get(part)).partition();
        } else {
            lastValue = value;
            return Utils.toPositive(nextValue) % numPartitions;
        }
    }

    private int nextValue(String topic, Object value) {
        AtomicInteger counter = (AtomicInteger) this.topicCounterMap.computeIfAbsent(topic, (k) -> {
            return new AtomicInteger(0);
        });
        if (lastValue != value)
            return counter.getAndIncrement();
        else return counter.get();
    }

    public void close() {
    }
}
