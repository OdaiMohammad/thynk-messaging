/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.accumed.thynkrequestsexecutor.engine;

import com.accumed.ws.wsinterface.rulesengine.service.*;

import java.util.Calendar;

/**
 *
 * @author Waltasseh
 */
public class RuleResult extends Result {
    private String objectType;
    private String objectID;
    private boolean alreadyPassed;
    private String byPassedNote;
    private Float claimAmount;
    private Float activityamount;
    
    public String getByPassedStage() {
        return byPassedStage;
    }

    public void setByPassedStage(String byPassedStage) {
        this.byPassedStage = byPassedStage;
    }
     private String byPassedStage;

    public String getByPassedNote() {
        return byPassedNote;
    }

    public void setByPassedNote(String byPassedNote) {
        this.byPassedNote = byPassedNote;
    }

    public boolean isAlreadyPassed() {
        return alreadyPassed;
    }

    public void setAlreadyPassed(boolean alreadyPassed) {
        this.alreadyPassed = alreadyPassed;
    }

    public RuleResult() {
    }

    public RuleResult(String objectType, String objectID) {
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, String longMsgDescription, Long ruleID, String ruleName, Calendar runDateTime, String severity, String shortMsgDescription) {
        super(longMsgDescription, ruleID, ruleName, runDateTime, severity, shortMsgDescription);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnOutcome requestOutcome) {
        super(requestOutcome);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnHeaderOutcome outcome) {
        super(outcome);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnHeaderWorkflowOutcome outcome) {
        super(outcome);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnHeaderExtendedValidationTypeOutcome outcome) {
        super(outcome);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimOutcome outcome, String claimId,Float claimAmount) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
         this.claimAmount=claimAmount;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimEncounterOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimEncounterAuthorisationOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimDiagnosisOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimActivityOutcome outcome, String claimId,Float activityAmount) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
        this.activityamount=activityAmount;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimActivityObservationOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimResubmissionOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimContractOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimPatientOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public RuleResult(String objectType, String objectID, ScrubResponseReturnClaimPatientPatientInsuranceOutcome outcome, String claimId) {
        super(outcome, claimId);
        this.objectType = objectType;
        this.objectID = objectID;
    }


    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public Float getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Float claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Float getActivityamount() {
        return activityamount;
    }

    public void setActivityamount(Float activityamount) {
        this.activityamount = activityamount;
    }

  

  

     

    
    
}
