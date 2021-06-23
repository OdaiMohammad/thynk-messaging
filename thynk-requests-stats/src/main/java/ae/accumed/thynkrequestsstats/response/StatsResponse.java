package ae.accumed.thynkrequestsstats.response;

import lombok.Data;

import java.util.Map;

@Data
public class StatsResponse {
    private final Map<String, Integer> stats;
}
