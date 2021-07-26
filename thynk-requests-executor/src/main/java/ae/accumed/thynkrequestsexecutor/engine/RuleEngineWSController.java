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


    public static ArrayList<RuleResult> getAllOutcomes(ScrubResponseReturn response) {

        ArrayList<RuleResult> ret = new ArrayList<>();

        if (response.getOutcome() != null) {
            for (ScrubResponseReturnOutcome outcome : response.getOutcome()) {
                ret.add(new RuleResult("response", "0", outcome));
            }
        }

        if (response.getHeader() != null) {
            if (response.getHeader().getOutcome() != null) {
                for (ScrubResponseReturnHeaderOutcome outcome : response.getHeader().getOutcome()) {
                    ret.add(new RuleResult("header", "0", outcome));
                }
            }
            if (response.getHeader().getWorkflow() != null) {
                if (response.getHeader().getWorkflow().getOutcome() != null) {
                    for (ScrubResponseReturnHeaderWorkflowOutcome outcome : response.getHeader().getWorkflow().getOutcome()) {
                        ret.add(new RuleResult("workflow", "0", outcome));
                    }
                }
            }

            if (response.getHeader().getExtendedValidationType() != null) {
                for (ScrubResponseReturnHeaderExtendedValidationType extendedValidationType : response.getHeader().getExtendedValidationType()) {
                    if (extendedValidationType.getOutcome() != null) {
                        for (ScrubResponseReturnHeaderExtendedValidationTypeOutcome outcome : extendedValidationType.getOutcome()) {
                            ret.add(new RuleResult("ExtendedValidationType", "0", outcome));
                        }
                    }
                }
            }
        }

        ScrubResponseReturnClaim claim = response.getClaim();

        if (claim.getOutcome() != null) {
            for (ScrubResponseReturnClaimOutcome outcome : claim.getOutcome()) {
                ret.add(new RuleResult("claim", claim.getIdCaller().toString(), outcome, claim.getIdCaller().toString(), claim.getNet()));
            }
        }
        if (claim.getEncounter() != null) {
            for (ScrubResponseReturnClaimEncounter encounter : claim.getEncounter()) {
                if (encounter.getOutcome() != null) {
                    for (ScrubResponseReturnClaimEncounterOutcome outcome : encounter.getOutcome()) {
                        ret.add(new RuleResult("encounter", encounter.getIdCaller().toString(), outcome, claim.getIdCaller().toString()));
                    }
                }

                if (encounter.getAuthorisation() != null) {
                    if (encounter.getAuthorisation().getOutcome() != null) {
                        for (ScrubResponseReturnClaimEncounterAuthorisationOutcome outcome : encounter.getAuthorisation().getOutcome()) {
                            ret.add(new RuleResult("authorisation", claim.getIdCaller().toString(), outcome, claim.getIdCaller().toString()));
                        }
                    }
                }
            }
        }
        if (claim.getDiagnosis() != null) {
            for (ScrubResponseReturnClaimDiagnosis diagnosis : claim.getDiagnosis()) {
                if (diagnosis.getOutcome() != null) {
                    for (ScrubResponseReturnClaimDiagnosisOutcome outcome : diagnosis.getOutcome()) {
                        ret.add(new RuleResult("diagnosis", diagnosis.getCode()/*.getIdCaller()*/, outcome, claim.getIdCaller().toString()));
                    }
                }
            }
        }
        if (claim.getActivity() != null) {
            for (ScrubResponseReturnClaimActivity activity : claim.getActivity()) {
                if (activity.getOutcome() != null) {
                    for (ScrubResponseReturnClaimActivityOutcome outcome : activity.getOutcome()) {
                        ret.add(new RuleResult("activity", activity.getCode()/*getIdCaller()*/, outcome, claim.getIdCaller().toString(), activity.getNet()));
                    }
                }
                if (activity.getObservation() != null) {
                    for (ScrubResponseReturnClaimActivityObservation observation : activity.getObservation()) {
                        if (observation.getOutcome() != null) {
                            for (ScrubResponseReturnClaimActivityObservationOutcome outcome : observation.getOutcome()) {
                                ret.add(new RuleResult("observation", observation.getIdCaller().toString(), outcome, claim.getIdCaller().toString()));
                            }
                        }
                    }
                }
            }
        }
        if (claim.getResubmission() != null) {
            if (claim.getResubmission().getOutcome() != null) {
                for (ScrubResponseReturnClaimResubmissionOutcome outcome : claim.getResubmission().getOutcome()) {
                    ret.add(new RuleResult("resubmission", claim.getIdCaller().toString(), outcome, claim.getIdCaller().toString()));
                }
            }
        }
        if (claim.getContract() != null) {
            if (claim.getContract().getOutcome() != null) {
                for (ScrubResponseReturnClaimContractOutcome outcome : claim.getContract().getOutcome()) {
                    ret.add(new RuleResult("contract", claim.getIdCaller().toString(), outcome, claim.getIdCaller().toString()));
                }
            }
        }
        if (claim.getPatient() != null) {
            if (claim.getPatient().getOutcome() != null) {
                for (ScrubResponseReturnClaimPatientOutcome outcome : claim.getPatient().getOutcome()) {
                    ret.add(new RuleResult("patient", claim.getPatient().getIdCaller().toString(), outcome, claim.getIdCaller().toString()));
                }
            }

            if (claim.getPatient().getPatientInsurance() != null) {
                if (claim.getPatient().getPatientInsurance().getOutcome() != null) {
                    for (ScrubResponseReturnClaimPatientPatientInsuranceOutcome outcome : claim.getPatient().getPatientInsurance().getOutcome()) {
                        ret.add(new RuleResult("patientInsurance", claim.getPatient().getPatientInsurance().getIdCaller().toString(), outcome, claim.getIdCaller().toString()));
                    }
                }
            }
        }

        return ret;
    }
}

