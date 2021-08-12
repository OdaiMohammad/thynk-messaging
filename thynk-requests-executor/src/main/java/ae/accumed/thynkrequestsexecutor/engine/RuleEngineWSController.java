package ae.accumed.thynkrequestsexecutor.engine;


import com.accumed.ws.wsinterface.rulesengine.service.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RuleEngineWSController {

    public static synchronized Object validateSingleClaim(ScrubScrubbingRequestClaim claim, String receiverId, String[] validationType, String userName, Integer userID, boolean exDBRules, String sender, String callingServer, String callingApp, String callingAppVersion) throws UnknownHostException {

        if (claim != null) {

            ScrubScrubbingRequestHeader header = new ScrubScrubbingRequestHeader(receiverId, getExtendedValidationType(validationType), null, null);

            ScrubScrubbingRequest request = new ScrubScrubbingRequest(sender, callingServer, callingApp, callingAppVersion, userID, userName, exDBRules, 0, header, claim, null);

            AccumedValidatorWSProxy proxy = new AccumedValidatorWSProxy();
            AccumedValidatorWS soap = proxy.getAccumedValidatorWS();
            ScrubResponseReturn response = null;
            boolean serviceError = false;
            String errorMessage = "";

            try {
                Logger.getLogger(RuleEngineWSController.class.getName()).log(Level.INFO, "Calling AccumedValidator for claim ({0})", claim.getID());
                response = soap.scrub(request, "ACCUMED", "@CCUMED", false);
                //  Logger.getLogger(RuleEngineWSController.class.getName()).log(Level.INFO, convertToXML(request), claim.getID());
                Logger.getLogger(RuleEngineWSController.class.getName()).log(Level.INFO, "End calling AccumedValidator for claim ({0})", claim.getID());
            } catch (Exception e) {
                Logger.getLogger(RuleEngineWSController.class.getName()).log(Level.SEVERE,
                        "Error Calling AccumedValidator for claim ({0} the following is the details of the exception:)", claim.getID());
                Logger.getLogger(RuleEngineWSController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                serviceError = true;
                errorMessage = e.getMessage();
            }
            if (!serviceError && response != null && response.getClaim() != null) {
                if (response.getClaim().getClaimType() != null
                        && response.getClaim().getClaimType().length > 0
                        && claim.getClaimType() == null) {
                    ScrubScrubbingRequestClaimClaimType[] detectedType
                            = new ScrubScrubbingRequestClaimClaimType[response.getClaim().getClaimType().length];

                    for (int a = 0; a < response.getClaim().getClaimType().length; a++) {
                        detectedType[a]
                                = new ScrubScrubbingRequestClaimClaimType(response.getClaim().getClaimType()[a].getType());
                    }
                    claim.setClaimType(detectedType);
                }

                return response;
            } else {
                RuleResult r = new RuleResult("", "0");
                r.setShortMsgDescription(String.format("Service Error: %s", errorMessage));
                return r;
            }
        } else {
            RuleResult r = new RuleResult("", "0");
            r.setShortMsgDescription("Service Error");
            return r;
        }

    }

    private static ScrubScrubbingRequestHeaderExtendedValidationType[] getExtendedValidationType(String[] validationType) {
        ScrubScrubbingRequestHeaderExtendedValidationType[] arr = new ScrubScrubbingRequestHeaderExtendedValidationType[validationType.length];
        int i = 0;
        for (String type : validationType) {
            arr[i++] = new ScrubScrubbingRequestHeaderExtendedValidationType(type, null);
        }
        return arr;
    }
}

