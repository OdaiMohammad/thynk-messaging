package ae.accumed.thynkrequestsexecutor.service;


import ae.accumed.thynkrequestsexecutor.engine.RuleEngineWSController;
import ae.accumed.thynkrequestsexecutor.engine.RuleResult;
import com.accumed.ws.wsinterface.rulesengine.service.ScrubScrubbingRequestClaim;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ThynkRuleEngineService {
    private static final Logger logger = LoggerFactory.getLogger(ThynkRuleEngineService.class);

    public Object validateClaim(String claimJson, String restrictPackages) {
        try {
            ArrayList<String> validationTypes = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode scrubbingRequest = objectMapper.readTree(claimJson);
            String sender = scrubbingRequest.get("sender").asText();
            String callingServer = scrubbingRequest.get("callingServer").asText();
            String callingApp = scrubbingRequest.get("callingApp").asText();
            String callingAppVersion = scrubbingRequest.get("callingAppVersion").asText();
            String receiverId = scrubbingRequest.get("header").get("receiverID").asText();
            ArrayNode validationTypesArrayNode = (ArrayNode) scrubbingRequest.get("header").get("extendedValidationType");
            validationTypesArrayNode.forEach(type -> validationTypes.add(type.get("type").asText()));
            String[] validationTypesArray = new String[validationTypes.size()];
            String userName = scrubbingRequest.get("userName").asText();
            Integer userID = scrubbingRequest.get("userID").asInt();
            boolean exDBRules = scrubbingRequest.get("excludeDBRules").asBoolean();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            ScrubScrubbingRequestClaim scrubScrubbingRequestClaim = gson.fromJson(scrubbingRequest.get("claim").toString(), ScrubScrubbingRequestClaim.class);
            return RuleEngineWSController.validateSingleClaim(scrubScrubbingRequestClaim, receiverId, validationTypes.toArray(validationTypesArray), userName, userID, exDBRules, restrictPackages, sender, callingServer, callingApp, callingAppVersion);
        } catch (Exception e) {
            logger.error("Error while validating claim", e);
            RuleResult r = new RuleResult("", "0");
            r.setShortMsgDescription("Service Error, Invalid JSON");
            return r;
        }
    }
}
