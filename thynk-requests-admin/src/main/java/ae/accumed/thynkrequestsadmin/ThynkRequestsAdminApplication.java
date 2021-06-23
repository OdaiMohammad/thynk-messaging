package ae.accumed.thynkrequestsadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class ThynkRequestsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThynkRequestsAdminApplication.class, args);
    }

}
