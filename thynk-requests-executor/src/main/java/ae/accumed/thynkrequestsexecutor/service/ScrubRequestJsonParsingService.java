package ae.accumed.thynkrequestsexecutor.service;

import com.accumed.ws.wsinterface.rulesengine.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScrubRequestJsonParsingService {

    public ScrubScrubbingRequestClaim jsonToScrubScrubbingRequestClaim(JsonNode claim) {
        ScrubScrubbingRequestClaim scrubScrubbingRequestClaim = new ScrubScrubbingRequestClaim();
        scrubScrubbingRequestClaim.setID(claim.get("ID").asInt(0));
        scrubScrubbingRequestClaim.setIdCaller(claim.get("idCaller").asInt(0));
        scrubScrubbingRequestClaim.setIDPayer(claim.get("IDPayer") != null ? claim.get("IDPayer").asText("") : "");
        scrubScrubbingRequestClaim.setMemberID(claim.get("MemberID").asText(""));
        scrubScrubbingRequestClaim.setPayerID(claim.get("PayerID").asText(""));
        scrubScrubbingRequestClaim.setProviderID(claim.get("ProviderID").asText(""));
        scrubScrubbingRequestClaim.setEmiratesIDNumber(claim.get("EmiratesIDNumber").asText(""));
        scrubScrubbingRequestClaim.setGross((float) claim.get("Gross").asDouble(0.0));
        scrubScrubbingRequestClaim.setPatientShare((float) claim.get("PatientShare").asDouble(0.0));
        scrubScrubbingRequestClaim.setNet((float) claim.get("Net").asDouble(0.0));
        scrubScrubbingRequestClaim.setProviderInvoiceAmount((float) claim.get("ProviderInvoiceAmount").asDouble(0.0));
        scrubScrubbingRequestClaim.setPaymentReference(claim.get("PaymentReference") != null ? claim.get("PaymentReference").asText("") : "");
        scrubScrubbingRequestClaim.setDateSettlement(claim.get("DateSettlement") != null ? claim.get("DateSettlement").asText("") : "");
        scrubScrubbingRequestClaim.setPending(claim.get("Pending").asBoolean());
        scrubScrubbingRequestClaim.setImported(claim.get("Imported").asBoolean());
        scrubScrubbingRequestClaim.setStatus(claim.get("Status").asText(""));
        scrubScrubbingRequestClaim.setEncounter(jsonToEncountersList(claim.get("Encounter")));
        scrubScrubbingRequestClaim.setDiagnosis(jsonToDiagnosisList(claim.get("Diagnosis")));
        scrubScrubbingRequestClaim.setActivity(jsonToActivityList(claim.get("Activity")));
        scrubScrubbingRequestClaim.setContract(jsonToContract(claim.get("Contract")));
        scrubScrubbingRequestClaim.setPatient(jsonToPatient(claim.get("Patient")));
        if (claim.get("XClaim") != null)
            scrubScrubbingRequestClaim.setXClaim(jsonToXClaimList(claim.get("XClaim")));
        if (claim.get("FClaim") != null)
            scrubScrubbingRequestClaim.setFClaim(jsonToFClaimList(claim.get("FClaim")));
        scrubScrubbingRequestClaim.setOutcome(jsonToOutcomeList(claim.get("Outcome")));
        scrubScrubbingRequestClaim.setClaimType(jsonToClaimTypeList(claim.get("ClaimType")));
        return scrubScrubbingRequestClaim;
    }

    private ScrubScrubbingRequestClaimEncounter[] jsonToEncountersList(JsonNode encountersArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimEncounter> encounters = new ArrayList<>();

        encountersArrayNode.forEach(encounter -> encounters.add(jsonToEncounter(encounter)));
        ScrubScrubbingRequestClaimEncounter[] encountersArray = new ScrubScrubbingRequestClaimEncounter[encounters.size()];

        return encounters.toArray(encountersArray);
    }

    private ScrubScrubbingRequestClaimEncounter jsonToEncounter(JsonNode encounterJson) {
        ScrubScrubbingRequestClaimEncounter encounter = new ScrubScrubbingRequestClaimEncounter();
        encounter.setIdCaller(encounterJson.get("idCaller") != null ? encounterJson.get("idCaller").asInt(0) : null);
        encounter.setFacilityID(encounterJson.get("FacilityID") != null ? encounterJson.get("FacilityID").asText("") : "");
        encounter.setType(encounterJson.get("Type") != null ? encounterJson.get("Type").asInt(0) : 0);
        encounter.setPatientID(encounterJson.get("PatientID") != null ? encounterJson.get("PatientID").asText("") : "");
        encounter.setStart(encounterJson.get("Start") != null ? encounterJson.get("Start").asText("") : "");
        encounter.setEnd(encounterJson.get("End") != null ? encounterJson.get("End").asText("") : "");
        encounter.setStartType(encounterJson.get("StartType") != null ? encounterJson.get("StartType").asInt(0) : 0);
        encounter.setEndType(encounterJson.get("EndType") != null ? encounterJson.get("EndType").asInt(0) : null);
        encounter.setNewPatient(encounterJson.get("NewPatient") != null ? encounterJson.get("NewPatient").asInt(0) : 0);
        ScrubScrubbingRequestClaimEncounterAuthorisation authorisation = new ScrubScrubbingRequestClaimEncounterAuthorisation();
        authorisation.setVerbal(encounterJson.get("Authorisation") != null && encounterJson.get("Authorisation").get("Verbal").asBoolean());
        authorisation.setStart("02/02/2020 17:53");
        authorisation.setEnd("02/02/2020 17:53");
        encounter.setAuthorisation(authorisation);
        return encounter;
    }

    private ScrubScrubbingRequestClaimDiagnosis[] jsonToDiagnosisList(JsonNode diagnosisArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimDiagnosis> diagnoses = new ArrayList<>();

        diagnosisArrayNode.forEach(diagnosis -> diagnoses.add(jsonToDiagnoses(diagnosis)));

        ScrubScrubbingRequestClaimDiagnosis[] diagnosesArray = new ScrubScrubbingRequestClaimDiagnosis[diagnoses.size()];

        return diagnoses.toArray(diagnosesArray);
    }

    private ScrubScrubbingRequestClaimDiagnosis jsonToDiagnoses(JsonNode diagnosisJson) {
        ScrubScrubbingRequestClaimDiagnosis diagnosis = new ScrubScrubbingRequestClaimDiagnosis();
        diagnosis.setIdCaller(diagnosisJson.get("idCaller") != null ? diagnosisJson.get("idCaller").asInt(0) : 0);
        diagnosis.setType(diagnosisJson.get("Type") != null ? diagnosisJson.get("Type").asText("") : "");
        diagnosis.setCode(diagnosisJson.get("Code") != null ? diagnosisJson.get("Code").asText("") : "");
        diagnosis.setProviderType(diagnosisJson.get("providerType") != null ? diagnosisJson.get("providerType").asText("") : "");
        diagnosis.setProviderCode(diagnosisJson.get("providerCode") != null ? diagnosisJson.get("providerCode").asText("") : "");
        if (diagnosisJson.get("Outcome") != null)
            diagnosis.setOutcome(jsonToDiagnosisOutcomeList(diagnosisJson.get("Outcome")));
        return diagnosis;
    }

    private ScrubScrubbingRequestClaimDiagnosisOutcome[] jsonToDiagnosisOutcomeList(JsonNode outcomeArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimDiagnosisOutcome> outcomes = new ArrayList<>();

        outcomeArrayNode.forEach(outcome -> outcomes.add(jsonToDiagnosisOutcome(outcome)));

        ScrubScrubbingRequestClaimDiagnosisOutcome[] outcomesArray = new ScrubScrubbingRequestClaimDiagnosisOutcome[outcomes.size()];

        return outcomes.toArray(outcomesArray);
    }

    private ScrubScrubbingRequestClaimDiagnosisOutcome jsonToDiagnosisOutcome(JsonNode outcomeJson) {
        ScrubScrubbingRequestClaimDiagnosisOutcome outcome = new ScrubScrubbingRequestClaimDiagnosisOutcome();
        outcome.setId(outcomeJson.get("id").asInt(0));
        outcome.setRuleName(outcomeJson.get("ruleName").asText(""));
        outcome.setRuleID(outcomeJson.get("ruleID").asText(""));
        outcome.setSeverity(outcomeJson.get("severity").asText(""));
        outcome.setShortMsg(outcomeJson.get("shortMsg").asText(""));
        outcome.setLongMsg(outcomeJson.get("longMsg").asText(""));
        return outcome;
    }

    private ScrubScrubbingRequestClaimActivity[] jsonToActivityList(JsonNode activityArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimActivity> activities = new ArrayList<>();

        activityArrayNode.forEach(activity -> activities.add(jsonToActivity(activity)));

        ScrubScrubbingRequestClaimActivity[] activitiesArray = new ScrubScrubbingRequestClaimActivity[activities.size()];

        return activities.toArray(activitiesArray);
    }

    private ScrubScrubbingRequestClaimActivity jsonToActivity(JsonNode activityJson) {

        ScrubScrubbingRequestClaimActivity activity = new ScrubScrubbingRequestClaimActivity();
        activity.setID(activityJson.get("ID").asText(""));
        activity.setIdCaller(activityJson.get("idCaller").asInt(0));
        activity.setStart(activityJson.get("Start").asText(""));
        activity.setType(activityJson.get("Type").asInt(0));
        activity.setCode(activityJson.get("Code").asText(""));
        activity.setQuantity((float) activityJson.get("Quantity").asDouble(0.0));
        activity.setNet((float) activityJson.get("Net").asDouble(0.0));
        activity.setProviderNet((float) activityJson.get("ProviderNet").asDouble(0.0));
        activity.setClinician(activityJson.get("Clinician").asText("Clinician"));
        activity.setOrderingClinician(activityJson.get("OrderingClinician") != null ? activityJson.get("OrderingClinician").asText("") : "");
        activity.setPriorAuthorizationID(activityJson.get("PriorAuthorizationID").asText(""));
        activity.setList((float) activityJson.get("List").asDouble(0.0));
        activity.setGross((float) activityJson.get("Gross").asDouble(0.0));
        activity.setPatientShare((float) activityJson.get("PatientShare").asDouble(0.0));
        activity.setPaymentAmount((float) activityJson.get("PaymentAmount").asDouble(0.0));
        activity.setDenialCode(activityJson.get("DenialCode") != null ? activityJson.get("DenialCode").asText("") : "");
        activity.setCopayment((float) activityJson.get("Copayment").asDouble(0.0));
        activity.setDeductible((float) activityJson.get("deductible").asDouble(0.0));
        activity.setProviderPatientShare((float) activityJson.get("providerPatientShare").asDouble(0.0));
        activity.setSysNet((float) activityJson.get("sysNet").asDouble(0.0));
        activity.setSysList((float) activityJson.get("sysList").asDouble(0.0));
        activity.setOutcome(jsonToActivityOutcomeList(activityJson.get("Outcome")));
        activity.setObservation(activityJson.get("Observation") != null ? jsonToObservationList(activityJson.get("Observation")) : null);
        return activity;
    }

    private ScrubScrubbingRequestClaimActivityOutcome[] jsonToActivityOutcomeList(JsonNode outcomeArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimActivityOutcome> outcomes = new ArrayList<>();

        outcomeArrayNode.forEach(outcome -> outcomes.add(jsonToActivityOutcome(outcome)));

        ScrubScrubbingRequestClaimActivityOutcome[] outcomesArray = new ScrubScrubbingRequestClaimActivityOutcome[outcomes.size()];

        return outcomes.toArray(outcomesArray);
    }

    private ScrubScrubbingRequestClaimActivityOutcome jsonToActivityOutcome(JsonNode outcomeJson) {
        ScrubScrubbingRequestClaimActivityOutcome outcome = new ScrubScrubbingRequestClaimActivityOutcome();
        outcome.setId(outcomeJson.get("id") != null ? outcomeJson.get("id").asInt(0) : 0);
        outcome.setRuleName(outcomeJson.get("ruleName") != null ? outcomeJson.get("ruleName").asText("") : "");
        outcome.setRuleID(outcomeJson.get("ruleID") != null ? outcomeJson.get("ruleID").asText("") : "");
        outcome.setSeverity(outcomeJson.get("severity").asText(""));
        outcome.setShortMsg(outcomeJson.get("shortMsg").asText(""));
        outcome.setLongMsg(outcomeJson.get("longMsg").asText(""));
        outcome.setCategories(outcomeJson.get("categories") != null ? outcomeJson.get("categories").asText("") : "");
        return outcome;
    }

    private ScrubScrubbingRequestClaimActivityObservation[] jsonToObservationList(JsonNode observationArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimActivityObservation> observations = new ArrayList<>();

        observationArrayNode.forEach(observation -> observations.add(jsonToActivityObservation(observation)));

        ScrubScrubbingRequestClaimActivityObservation[] outcomesArray = new ScrubScrubbingRequestClaimActivityObservation[observations.size()];

        return observations.toArray(outcomesArray);
    }

    private ScrubScrubbingRequestClaimActivityObservation jsonToActivityObservation(JsonNode observationJson) {
        ScrubScrubbingRequestClaimActivityObservation observation = new ScrubScrubbingRequestClaimActivityObservation();
        observation.setIdCaller(observationJson.get("idCaller").asInt(0));
        observation.setType(observationJson.get("Type").asText(""));
        observation.setCode(observationJson.get("Code").asText(""));
        observation.setValue(observationJson.get("Value").asText(""));
        observation.setValueType(observationJson.get("ValueType").asText(""));
        return observation;
    }

    private ScrubScrubbingRequestClaimContract jsonToContract(JsonNode contractJson) {
        ScrubScrubbingRequestClaimContract contract = new ScrubScrubbingRequestClaimContract();
        contract.setPackageName(contractJson.get("PackageName").asText(""));
        contract.setNetwork(contractJson.get("Network") != null ? contractJson.get("Network").asText("") : "");
        contract.setSubNetworkName(contractJson.get("SubNetworkName") != null ? contractJson.get("SubNetworkName").asText("") : "");
        contract.setPolicy(contractJson.get("Policy") != null ? contractJson.get("Policy").asText("") : "");
        return contract;
    }

    private ScrubScrubbingRequestClaimPatient jsonToPatient(JsonNode patientJson) {
        ScrubScrubbingRequestClaimPatient patient = new ScrubScrubbingRequestClaimPatient();
        patient.setIdCaller(patientJson.get("idCaller").asInt(0));
        patient.setPATIENT_NAME(patientJson.get("PATIENT_NAME").asText(""));
        patient.setPATIENT_SURNAME(patientJson.get("PATIENT_SURNAME").asText(""));
        patient.setGENDER_ID(patientJson.get("GENDER_ID").asText(""));
        patient.setEMIRATES_ID(patientJson.get("EMIRATES_ID").asText(""));
        patient.setPASSPORT_ID(patientJson.get("PASSPORT_ID").asText(""));
        patient.setDRIVING_LICENSE(patientJson.get("DRIVING_LICENSE").asText(""));
        patient.setDATE_OF_BIRTH(patientJson.get("DATE_OF_BIRTH").asText(""));
        patient.setNATIONALITY(patientJson.get("NATIONALITY").asText(""));
        patient.setEMIRATE_TYPES(patientJson.get("EMIRATE_TYPES").asInt(0));
        patient.setPatientInsurance(jsonToPatientInsurance(patientJson.get("PatientInsurance")));
        return patient;
    }

    private ScrubScrubbingRequestClaimPatientPatientInsurance jsonToPatientInsurance(JsonNode patientInsuranceJson) {
        ScrubScrubbingRequestClaimPatientPatientInsurance insurance = new ScrubScrubbingRequestClaimPatientPatientInsurance();
        insurance.setIdCaller(patientInsuranceJson.get("idCaller").asInt(0));
        insurance.setPATIENT_INSURANCE_ID(patientInsuranceJson.get("PATIENT_INSURANCE_ID").asText(""));
        insurance.setRELATION_TO(patientInsuranceJson.get("RELATION_TO").asText(""));
        insurance.setPRINCIPLE_INSURANCE_ID(patientInsuranceJson.get("PRINCIPLE_INSURANCE_ID").asText(""));
        insurance.setINSURANCE_LISENCE(patientInsuranceJson.get("INSURANCE_LISENCE").asText(""));
        insurance.setPACKAGE_NAME(patientInsuranceJson.get("PACKAGE_NAME").asText(""));
        insurance.setNetworkName(patientInsuranceJson.get("NetworkName").asText(""));
        insurance.setNetworkId(patientInsuranceJson.get("NetworkId").asInt(0));
        insurance.setSubNetworkName(patientInsuranceJson.get("SubNetworkName") != null ? patientInsuranceJson.get("SubNetworkName").asText("") : "");
        insurance.setSubNetworkId(patientInsuranceJson.get("SubNetworkId") != null ? patientInsuranceJson.get("SubNetworkId").asInt(0) : null);
        insurance.setPlanName(patientInsuranceJson.get("PlanName") != null ? patientInsuranceJson.get("PlanName").asText("") : "");
        insurance.setPlanId(patientInsuranceJson.get("PlanId") != null ? patientInsuranceJson.get("PlanId").asInt(0) : 0);
        insurance.setSTART_DATE(patientInsuranceJson.get("START_DATE").asText(""));
        insurance.setEND_DATE(patientInsuranceJson.get("END_DATE").asText(""));
        insurance.setGROSS_PREMIUM((float) patientInsuranceJson.get("GROSS_PREMIUM").asDouble(0.0));
        insurance.setPOLICY_HOLDER_TYPE_ID(patientInsuranceJson.get("POLICY_HOLDER_TYPE_ID").asText(""));
        insurance.setIS_EXPIRED(patientInsuranceJson.get("IS_EXPIRED").asInt(0));
        insurance.setPolicy_Number(patientInsuranceJson.get("Policy_Number").asText(""));
        insurance.setVerified(patientInsuranceJson.get("verified").asInt(0));
        insurance.setADMISSION_TYPE(patientInsuranceJson.get("ADMISSION_TYPE").asInt(0));
        insurance.setIS_PENDING(patientInsuranceJson.get("IS_PENDING").asInt(0));
        insurance.setMATERNITY(patientInsuranceJson.get("MATERNITY").asInt(0));
        insurance.setDENTAL(patientInsuranceJson.get("DENTAL").asInt(0));
        insurance.setOPTICAL(patientInsuranceJson.get("OPTICAL").asInt(0));
        insurance.setCoPayment(jsonToCoPaymentsList(patientInsuranceJson.get("CoPayment")));
        return insurance;

    }

    private ScrubScrubbingRequestClaimPatientPatientInsuranceCoPayment[] jsonToCoPaymentsList(JsonNode coPaymentArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimPatientPatientInsuranceCoPayment> coPayments = new ArrayList<>();

        coPaymentArrayNode.forEach(coPayment -> coPayments.add(jsonToCoPayment(coPayment)));

        ScrubScrubbingRequestClaimPatientPatientInsuranceCoPayment[] coPaymentsArray = new ScrubScrubbingRequestClaimPatientPatientInsuranceCoPayment[coPayments.size()];

        return coPayments.toArray(coPaymentsArray);
    }

    private ScrubScrubbingRequestClaimPatientPatientInsuranceCoPayment jsonToCoPayment(JsonNode coPaymentJson) {
        ScrubScrubbingRequestClaimPatientPatientInsuranceCoPayment coPayment = new ScrubScrubbingRequestClaimPatientPatientInsuranceCoPayment();
        coPayment.setId(coPaymentJson.get("id").asLong());
        coPayment.setType(coPaymentJson.get("type").asText(""));
        coPayment.setOutNet(coPaymentJson.get("OutNet").asInt(0));
        coPayment.setValue(coPaymentJson.get("Value").asDouble(0.0));
        coPayment.setCeiling(coPaymentJson.get("Ceiling").asDouble(0.0));
        return coPayment;
    }

    private ScrubScrubbingRequestClaimXClaim[] jsonToXClaimList(JsonNode xClaimArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimXClaim> xClaims = new ArrayList<>();

        xClaimArrayNode.forEach(xClaim -> xClaims.add(jsonToXClaim(xClaim)));

        ScrubScrubbingRequestClaimXClaim[] coPaymentsArray = new ScrubScrubbingRequestClaimXClaim[xClaims.size()];

        return xClaims.toArray(coPaymentsArray);
    }

    private ScrubScrubbingRequestClaimXClaim jsonToXClaim(JsonNode xClaimJson) {
        ScrubScrubbingRequestClaimXClaim xClaim = new ScrubScrubbingRequestClaimXClaim();
        xClaim.setID(xClaimJson.get("ID").asInt(0));
        xClaim.setIdCaller(xClaimJson.get("idCaller").asInt(0));
        xClaim.setIDPayer(xClaimJson.get("IDPayer") != null ? xClaimJson.get("IDPayer").asText("") : "");
        xClaim.setReceiverID(xClaimJson.get("ReceiverID").asText(""));
        xClaim.setMemberID(xClaimJson.get("MemberID").asText(""));
        xClaim.setPayerID(xClaimJson.get("PayerID").asText(""));
        xClaim.setProviderID(xClaimJson.get("ProviderID").asText(""));
        xClaim.setEmiratesIDNumber(xClaimJson.get("EmiratesIDNumber").asText(""));
        xClaim.setGross((float) xClaimJson.get("Gross").asDouble(0.0));
        xClaim.setPatientShare((float) xClaimJson.get("PatientShare").asDouble(0.0));
        xClaim.setNet((float) xClaimJson.get("Net").asDouble(0.0));
        xClaim.setProviderInvoiceAmount((float) xClaimJson.get("ProviderInvoiceAmount").asDouble(0.0));
        xClaim.setDateSettlement(xClaimJson.get("DateSettlement") != null ? xClaimJson.get("DateSettlement").asText("") : "");
        xClaim.setPending(xClaimJson.get("Pending").asBoolean());
        xClaim.setImported(xClaimJson.get("Imported").asBoolean());
        xClaim.setStatus(xClaimJson.get("Status").asText(""));
        xClaim.setXDiagnosis(jsonToXDiagnosisList(xClaimJson.get("XDiagnosis")));
        if (xClaimJson.get("XActivity") != null)
            xClaim.setXActivity(jsonToXActivityList(xClaimJson.get("XActivity")));
        xClaim.setXContract(jsonToXContract(xClaimJson.get("XContract")));
        return xClaim;
    }

    private ScrubScrubbingRequestClaimXClaimXDiagnosis[] jsonToXDiagnosisList(JsonNode xDiagnosisArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimXClaimXDiagnosis> xDiagnoses = new ArrayList<>();

        xDiagnosisArrayNode.forEach(xDiagnosis -> xDiagnoses.add(jsonToXDiagnoses(xDiagnosis)));

        ScrubScrubbingRequestClaimXClaimXDiagnosis[] coPaymentsArray = new ScrubScrubbingRequestClaimXClaimXDiagnosis[xDiagnoses.size()];

        return xDiagnoses.toArray(coPaymentsArray);
    }

    private ScrubScrubbingRequestClaimXClaimXDiagnosis jsonToXDiagnoses(JsonNode xDiagnosisJson) {
        ScrubScrubbingRequestClaimXClaimXDiagnosis xDiagnosis = new ScrubScrubbingRequestClaimXClaimXDiagnosis();
        xDiagnosis.setIdCaller(xDiagnosisJson.get("idCaller").asInt(0));
        xDiagnosis.setType(xDiagnosisJson.get("Type").asText(""));
        xDiagnosis.setCode(xDiagnosisJson.get("Code").asText(""));
        xDiagnosis.setProviderType(xDiagnosisJson.get("providerType").asText(""));
        xDiagnosis.setProviderCode(xDiagnosisJson.get("providerCode").asText(""));
        return xDiagnosis;
    }

    private ScrubScrubbingRequestClaimXClaimXActivity[] jsonToXActivityList(JsonNode xActivityArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimXClaimXActivity> xActivities = new ArrayList<>();

        xActivityArrayNode.forEach(xActivity -> xActivities.add(jsonToXActivity(xActivity)));

        ScrubScrubbingRequestClaimXClaimXActivity[] coPaymentsArray = new ScrubScrubbingRequestClaimXClaimXActivity[xActivities.size()];

        return xActivities.toArray(coPaymentsArray);
    }

    private ScrubScrubbingRequestClaimXClaimXActivity jsonToXActivity(JsonNode xActivityJson) {
        ScrubScrubbingRequestClaimXClaimXActivity xActivity = new ScrubScrubbingRequestClaimXClaimXActivity();
        xActivity.setID(xActivityJson.get("ID").asText(""));
        xActivity.setIdCaller(xActivityJson.get("idCaller").asInt(0));
        xActivity.setStart(xActivityJson.get("Start").asText(""));
        xActivity.setClinician(xActivityJson.get("Clinician").asText("Clinician"));
        xActivity.setType(xActivityJson.get("Type").asInt(0));
        xActivity.setCode(xActivityJson.get("Code").asText(""));
        xActivity.setQuantity((float) xActivityJson.get("Quantity").asDouble(0.0));
        xActivity.setNet((float) xActivityJson.get("Net").asDouble(0.0));
        xActivity.setProviderNet((float) xActivityJson.get("ProviderNet").asDouble(0.0));
        xActivity.setCode(xActivityJson.get("Clinician").asText(""));
        xActivity.setCode(xActivityJson.get("PriorAuthorizationID").asText(""));
        xActivity.setList((float) xActivityJson.get("List").asDouble(0.0));
        xActivity.setGross((float) xActivityJson.get("Gross").asDouble(0.0));
        xActivity.setPatientShare((float) xActivityJson.get("PatientShare").asDouble(0.0));
        xActivity.setPaymentAmount((float) xActivityJson.get("PaymentAmount").asDouble(0.0));
        xActivity.setDenialCode(xActivityJson.get("DenialCode").asText(""));
        xActivity.setCopayment((float) xActivityJson.get("Copayment").asDouble(0.0));
        xActivity.setDeductible((float) xActivityJson.get("deductible").asDouble(0.0));
        xActivity.setProviderPatientShare((float) xActivityJson.get("providerPatientShare").asDouble(0.0));
        return xActivity;
    }

    private ScrubScrubbingRequestClaimXClaimXContract jsonToXContract(JsonNode xContractJson) {
        ScrubScrubbingRequestClaimXClaimXContract xContract = new ScrubScrubbingRequestClaimXClaimXContract();
        xContract.setPackageName(xContractJson.get("PackageName").asText(""));
        return xContract;
    }

    private ScrubScrubbingRequestClaimFClaim[] jsonToFClaimList(JsonNode fClaimArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimFClaim> fClaims = new ArrayList<>();

        fClaimArrayNode.forEach(fClaim -> fClaims.add(jsonToFClaim(fClaim)));

        ScrubScrubbingRequestClaimFClaim[] coPaymentsArray = new ScrubScrubbingRequestClaimFClaim[fClaims.size()];

        return fClaims.toArray(coPaymentsArray);
    }

    private ScrubScrubbingRequestClaimFClaim jsonToFClaim(JsonNode fClaimJson) {
        ScrubScrubbingRequestClaimFClaim fClaim = new ScrubScrubbingRequestClaimFClaim();
        fClaim.setID(fClaimJson.get("ID").asInt(0));
        fClaim.setIdCaller(fClaimJson.get("idCaller").asInt(0));
        fClaim.setIDPayer(fClaimJson.get("IDPayer").asText(""));
        fClaim.setReceiverID(fClaimJson.get("ReceiverID").asText(""));
        fClaim.setMemberID(fClaimJson.get("MemberID").asText(""));
        fClaim.setPayerID(fClaimJson.get("PayerID").asText(""));
        fClaim.setProviderID(fClaimJson.get("ProviderID").asText(""));
        fClaim.setEmiratesIDNumber(fClaimJson.get("EmiratesIDNumber").asText(""));
        fClaim.setGross((float) fClaimJson.get("Gross").asDouble(0.0));
        fClaim.setPatientShare((float) fClaimJson.get("PatientShare").asDouble(0.0));
        fClaim.setNet((float) fClaimJson.get("Net").asDouble(0.0));
        fClaim.setProviderInvoiceAmount((float) fClaimJson.get("ProviderInvoiceAmount").asDouble(0.0));
        fClaim.setDateSettlement(fClaimJson.get("DateSettlement").asText(""));
        fClaim.setPending(fClaimJson.get("Pending").asBoolean());
        fClaim.setImported(fClaimJson.get("Imported").asBoolean());
        fClaim.setStatus(fClaimJson.get("Status").asText(""));
        fClaim.setFDiagnosis(jsonToFDiagnosisList(fClaimJson.get("FDiagnosis")));
        fClaim.setFActivity(jsonToFActivityList(fClaimJson.get("FActivity")));
        fClaim.setFContract(jsonToFContract(fClaimJson.get("FContract")));
        return fClaim;
    }

    private ScrubScrubbingRequestClaimFClaimFDiagnosis[] jsonToFDiagnosisList(JsonNode fDiagnosisArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimFClaimFDiagnosis> fDiagnoses = new ArrayList<>();

        fDiagnosisArrayNode.forEach(fDiagnosis -> fDiagnoses.add(jsonToFDiagnosis(fDiagnosis)));

        ScrubScrubbingRequestClaimFClaimFDiagnosis[] coPaymentsArray = new ScrubScrubbingRequestClaimFClaimFDiagnosis[fDiagnoses.size()];

        return fDiagnoses.toArray(coPaymentsArray);
    }

    private ScrubScrubbingRequestClaimFClaimFDiagnosis jsonToFDiagnosis(JsonNode fDiagnosisJson) {
        ScrubScrubbingRequestClaimFClaimFDiagnosis fDiagnosis = new ScrubScrubbingRequestClaimFClaimFDiagnosis();
        fDiagnosis.setIdCaller(fDiagnosisJson.get("idCaller").asInt(0));
        fDiagnosis.setType(fDiagnosisJson.get("Type").asText(""));
        fDiagnosis.setCode(fDiagnosisJson.get("Code").asText(""));
        fDiagnosis.setProviderType(fDiagnosisJson.get("providerType").asText(""));
        fDiagnosis.setProviderCode(fDiagnosisJson.get("providerCode").asText(""));
        return fDiagnosis;
    }

    private ScrubScrubbingRequestClaimFClaimFActivity[] jsonToFActivityList(JsonNode fActivityArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimFClaimFActivity> fActivities = new ArrayList<>();


        fActivityArrayNode.forEach(fActivity -> {
            ScrubScrubbingRequestClaimFClaimFActivity tempXActivity = jsonToFActivity(fActivity);
            if (tempXActivity != null)
                fActivities.add(tempXActivity);
        });

        ScrubScrubbingRequestClaimFClaimFActivity[] coPaymentsArray = new ScrubScrubbingRequestClaimFClaimFActivity[fActivities.size()];

        return fActivities.toArray(coPaymentsArray);
    }

    private ScrubScrubbingRequestClaimFClaimFActivity jsonToFActivity(JsonNode fActivityJson) {
        ScrubScrubbingRequestClaimFClaimFActivity fActivity = new ScrubScrubbingRequestClaimFClaimFActivity();
        try {
            fActivity.setID(fActivityJson.get("ID").asText(""));
            fActivity.setIdCaller(fActivityJson.get("idCaller").asInt(0));
            fActivity.setStart(fActivityJson.get("Start").asText(""));
            fActivity.setType(fActivityJson.get("Type").asInt(0));
            fActivity.setCode(fActivityJson.get("Code").asText(""));
            fActivity.setClinician(fActivityJson.get("Clinician").asText("Clinician"));
            fActivity.setQuantity((float) fActivityJson.get("Quantity").asDouble(0.0));
            fActivity.setNet((float) fActivityJson.get("Net").asDouble(0.0));
            fActivity.setProviderNet((float) fActivityJson.get("ProviderNet").asDouble(0.0));
            fActivity.setCode(fActivityJson.get("Clinician").asText(""));
            fActivity.setCode(fActivityJson.get("PriorAuthorizationID").asText(""));
            fActivity.setList((float) fActivityJson.get("List").asDouble(0.0));
            fActivity.setGross((float) fActivityJson.get("Gross").asDouble(0.0));
            fActivity.setPatientShare((float) fActivityJson.get("PatientShare").asDouble(0.0));
            fActivity.setPaymentAmount((float) fActivityJson.get("PaymentAmount").asDouble(0.0));
            fActivity.setDenialCode(fActivityJson.get("DenialCode").asText(""));
            fActivity.setCopayment((float) fActivityJson.get("Copayment").asDouble(0.0));
            fActivity.setDeductible((float) fActivityJson.get("deductible").asDouble(0.0));
            fActivity.setProviderPatientShare((float) fActivityJson.get("providerPatientShare").asDouble(0.0));
            return fActivity;
        } catch (Exception e) {
            return null;
        }
    }

    private ScrubScrubbingRequestClaimFClaimFContract jsonToFContract(JsonNode fContractJson) {
        ScrubScrubbingRequestClaimFClaimFContract fContract = new ScrubScrubbingRequestClaimFClaimFContract();
        fContract.setPackageName(fContractJson.get("PackageName").asText(""));
        return fContract;
    }

    private ScrubScrubbingRequestClaimOutcome[] jsonToOutcomeList(JsonNode outcomeArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimOutcome> outcomes = new ArrayList<>();

        outcomeArrayNode.forEach(outcome -> outcomes.add(jsonToOutcome(outcome)));

        ScrubScrubbingRequestClaimOutcome[] outcomesArray = new ScrubScrubbingRequestClaimOutcome[outcomes.size()];

        return outcomes.toArray(outcomesArray);
    }

    private ScrubScrubbingRequestClaimOutcome jsonToOutcome(JsonNode outcomeJson) {
        ScrubScrubbingRequestClaimOutcome outcome = new ScrubScrubbingRequestClaimOutcome();
        outcome.setId(outcomeJson.get("id").asInt(0));
        outcome.setRuleName(outcomeJson.get("ruleName").asText(""));
        outcome.setRuleID(outcomeJson.get("ruleID").asText(""));
        outcome.setSeverity(outcomeJson.get("severity").asText(""));
        outcome.setShortMsg(outcomeJson.get("shortMsg").asText(""));
        outcome.setLongMsg(outcomeJson.get("longMsg").asText(""));
        return outcome;
    }

    private ScrubScrubbingRequestClaimClaimType[] jsonToClaimTypeList(JsonNode claimTypeArrayNode) {
        ArrayList<ScrubScrubbingRequestClaimClaimType> claimTypes = new ArrayList<>();

        claimTypeArrayNode.forEach(claimType -> claimTypes.add(jsonToClaimType(claimType)));

        ScrubScrubbingRequestClaimClaimType[] outcomesArray = new ScrubScrubbingRequestClaimClaimType[claimTypes.size()];

        return claimTypes.toArray(outcomesArray);
    }

    private ScrubScrubbingRequestClaimClaimType jsonToClaimType(JsonNode claimTypeJson) {
        ScrubScrubbingRequestClaimClaimType claimType = new ScrubScrubbingRequestClaimClaimType();
        claimType.setType(claimTypeJson.asText(""));
        return claimType;
    }
}
