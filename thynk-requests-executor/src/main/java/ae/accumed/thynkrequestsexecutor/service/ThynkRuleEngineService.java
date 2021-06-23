package ae.accumed.thynkrequestsexecutor.service;


import ae.accumed.thynkrequestsexecutor.engine.RuleEngineWSController;
import ae.accumed.thynkrequestsexecutor.engine.RuleResult;
import com.accumed.ws.wsinterface.rulesengine.service.ScrubScrubbingRequestClaim;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThynkRuleEngineService {
    private static final Logger logger = LoggerFactory.getLogger(ThynkRuleEngineService.class);

    private final ScrubRequestJsonParsingService scrubRequestJsonParsingService;

    @Autowired
    public ThynkRuleEngineService(ScrubRequestJsonParsingService scrubRequestJsonParsingService) {
        this.scrubRequestJsonParsingService = scrubRequestJsonParsingService;
    }

    public List<RuleResult> validateClaim(String claimJson) {
        try {
            ArrayList<String> validationTypes = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode scrubbingRequest = objectMapper.readTree(claimJson);
            String receiverId = scrubbingRequest.get("Header").get("ReceiverID").asText("");
            ArrayNode validationTypesArrayNode = (ArrayNode) scrubbingRequest.get("Header").get("ExtendedValidationType");
            validationTypesArrayNode.forEach(type -> validationTypes.add(type.get("Type").asText("")));
            String[] validationTypesArray = new String[validationTypes.size()];
            String userName = scrubbingRequest.get("UserName").asText("");
            Integer userID = scrubbingRequest.get("UserID").asInt(0);
            boolean exDBRules = scrubbingRequest.get("excludeDBRules").asBoolean();
            ScrubScrubbingRequestClaim scrubScrubbingRequestClaim = scrubRequestJsonParsingService.jsonToScrubScrubbingRequestClaim(scrubbingRequest.get("Claim"));
            return RuleEngineWSController.validateSingleClaim(scrubScrubbingRequestClaim, receiverId, validationTypes.toArray(validationTypesArray), userName, userID, exDBRules);
        } catch (Exception e) {
            logger.error("Error while validating claim", e);
            return new ArrayList<>();
        }
    }
}
