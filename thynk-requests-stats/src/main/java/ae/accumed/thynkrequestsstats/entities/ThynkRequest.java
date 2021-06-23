package ae.accumed.thynkrequestsstats.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "thynkrequest")
@Data
public class ThynkRequest {

    @Id
    private String id;

    private int priority;
    private String createdAt;
    private String updatedAt;
    private String source;
    private String status;
    private String correlationId;

}
