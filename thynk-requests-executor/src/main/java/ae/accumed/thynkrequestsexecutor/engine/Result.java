/**
 * Result.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006
 * (06:55:48 PDT) WSDL2Java emitter.
 */
package ae.accumed.thynkrequestsexecutor.engine;

import com.accumed.ws.wsinterface.rulesengine.service.*;

import java.util.Calendar;

public class Result implements java.io.Serializable, Comparable<Result> {

    private String longMsgDescription;

    private Long ruleID;

    private String ruleName;

    private Calendar runDateTime;

    private String severity;

    private String shortMsgDescription;

    private String style;

    private String claimId;

    public int getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }
    
    private int severityLevel;

    public Result() {
    }

//    public Result(com.accumed.validation.Result res) {
//        this.setLongMsgDescription(res.getLongMsgDescription());
//        this.setRuleID(res.getRuleID());
//        this.setRuleName(res.getRuleName());
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(res.getRunDateTime());
//        this.setRunDateTime(cal);
//        this.setSeverity(res.getSeverity());
//        this.setShortMsgDescription(res.getShortMsgDescription());
//    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public Result(
            String longMsgDescription,
            Long ruleID,
            String ruleName,
            Calendar runDateTime,
            String severity,
            String shortMsgDescription) {
        this.longMsgDescription = longMsgDescription;
        this.ruleID = ruleID;
        this.ruleName = ruleName;
        this.runDateTime = runDateTime;
        this.severity = severity;
        this.shortMsgDescription = shortMsgDescription;
    }

    /**
     * Gets the longMsgDescription value for this Result.
     *
     * @return longMsgDescription
     */
    public String getLongMsgDescription() {
        return longMsgDescription;
    }

    /**
     * Sets the longMsgDescription value for this Result.
     *
     * @param longMsgDescription
     */
    public void setLongMsgDescription(String longMsgDescription) {
        this.longMsgDescription = longMsgDescription;
    }

    /**
     * Gets the ruleID value for this Result.
     *
     * @return ruleID
     */
    public Long getRuleID() {
        return ruleID;
    }

    /**
     * Sets the ruleID value for this Result.
     *
     * @param ruleID
     */
    public void setRuleID(Long ruleID) {
        this.ruleID = ruleID;
    }

    /**
     * Gets the ruleName value for this Result.
     *
     * @return ruleName
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Sets the ruleName value for this Result.
     *
     * @param ruleName
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * Gets the runDateTime value for this Result.
     *
     * @return runDateTime
     */
    public Calendar getRunDateTime() {
        return runDateTime;
    }

    /**
     * Sets the runDateTime value for this Result.
     *
     * @param runDateTime
     */
    public void setRunDateTime(Calendar runDateTime) {
        this.runDateTime = runDateTime;
    }

    /**
     * Gets the severity value for this Result.
     *
     * @return severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Sets the severity value for this Result.
     *
     * @param severity
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * Gets the shortMsgDescription value for this Result.
     *
     * @return shortMsgDescription
     */
    public String getShortMsgDescription() {
        return shortMsgDescription;
    }

    /**
     * Sets the shortMsgDescription value for this Result.
     *
     * @param shortMsgDescription
     */
    public void setShortMsgDescription(String shortMsgDescription) {
        this.shortMsgDescription = shortMsgDescription;
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Result)) {
            return false;
        }
        Result other = (Result) obj;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals =  ((this.longMsgDescription == null && other.getLongMsgDescription() == null)
                || (this.longMsgDescription != null
                && this.longMsgDescription.equals(other.getLongMsgDescription())))
                && ((this.ruleID == null && other.getRuleID() == null)
                || (this.ruleID != null
                && this.ruleID.equals(other.getRuleID())))
                && ((this.ruleName == null && other.getRuleName() == null)
                || (this.ruleName != null
                && this.ruleName.equals(other.getRuleName())))
                && ((this.runDateTime == null && other.getRunDateTime() == null)
                || (this.runDateTime != null
                && this.runDateTime.equals(other.getRunDateTime())))
                && ((this.severity == null && other.getSeverity() == null)
                || (this.severity != null
                && this.severity.equals(other.getSeverity())))
                && ((this.shortMsgDescription == null && other.getShortMsgDescription() == null)
                || (this.shortMsgDescription != null
                && this.shortMsgDescription.equals(other.getShortMsgDescription())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLongMsgDescription() != null) {
            _hashCode += getLongMsgDescription().hashCode();
        }
        if (getRuleID() != null) {
            _hashCode += getRuleID().hashCode();
        }
        if (getRuleName() != null) {
            _hashCode += getRuleName().hashCode();
        }
        if (getRunDateTime() != null) {
            _hashCode += getRunDateTime().hashCode();
        }
        if (getSeverity() != null) {
            _hashCode += getSeverity().hashCode();
        }
        if (getShortMsgDescription() != null) {
            _hashCode += getShortMsgDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(Result.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.accumed.com/", "result"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longMsgDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "longMsgDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ruleID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ruleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("runDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "runDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("severity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "severity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortMsgDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shortMsgDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(
                _javaType, _xmlType, typeDesc);
    }

    public int compareTo(Result t) {
        return this.getSeverityLevel()==(t.getSeverityLevel())?0:this.getSeverityLevel()>(t.getSeverityLevel())?1:-1;
    }

    public Result(ScrubResponseReturnOutcome requestOutcome) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = requestOutcome.getRuleName();
        this.severity = requestOutcome.getSeverity();
        this.shortMsgDescription = requestOutcome.getShortMsg();
        if (requestOutcome.getLongMsg() == null || requestOutcome.getLongMsg().equals("") || requestOutcome.getLongMsg().equals("E.")) {
            this.longMsgDescription = this.shortMsgDescription;
        } else {
            this.longMsgDescription = requestOutcome.getLongMsg();
        }
    }

    public Result(ScrubResponseReturnHeaderOutcome outcome) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        if (outcome.getLongMsg() == null || outcome.getLongMsg().equals("") || outcome.getLongMsg().equals("E.")) {
            this.longMsgDescription = this.shortMsgDescription;
        } else {
            this.longMsgDescription = outcome.getLongMsg();
        }
    }

    public Result(ScrubResponseReturnHeaderWorkflowOutcome outcome) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        this.longMsgDescription = outcome.getLongMsg();
    }

    
    public Result(ScrubResponseReturnHeaderExtendedValidationTypeOutcome outcome) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        this.longMsgDescription = outcome.getLongMsg();
    }
    
    public Result(ScrubResponseReturnClaimOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        if (outcome.getLongMsg() == null || outcome.getLongMsg().equals("")|| outcome.getLongMsg().equals("E") || outcome.getLongMsg().equals("E.")) {
            this.longMsgDescription = this.shortMsgDescription;
        } else {
            this.longMsgDescription = outcome.getLongMsg();
        }
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimEncounterOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        if (outcome.getLongMsg() == null || outcome.getLongMsg().equals("")|| outcome.getLongMsg().equals("E") || outcome.getLongMsg().equals("E.")) {
            this.longMsgDescription = this.shortMsgDescription;
        } else {
            this.longMsgDescription = outcome.getLongMsg();
        }
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimEncounterAuthorisationOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        this.longMsgDescription = outcome.getLongMsg();
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimDiagnosisOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        if (outcome.getLongMsg() == null || outcome.getLongMsg().equals("")|| outcome.getLongMsg().equals("E") || outcome.getLongMsg().equals("E.")) {
            this.longMsgDescription = this.shortMsgDescription;
        } else {
            this.longMsgDescription = outcome.getLongMsg();
        }
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimActivityOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        if (outcome.getLongMsg() == null || outcome.getLongMsg().equals("")|| outcome.getLongMsg().equals("E") || outcome.getLongMsg().equals("E.")) {
            this.longMsgDescription = this.shortMsgDescription;
        } else {
            this.longMsgDescription = outcome.getLongMsg();
        }
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimActivityObservationOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        if (outcome.getLongMsg() == null || outcome.getLongMsg().equals("")|| outcome.getLongMsg().equals("E") || outcome.getLongMsg().equals("E.")) {
            this.longMsgDescription = this.shortMsgDescription;
        } else {
            this.longMsgDescription = outcome.getLongMsg();
        }
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimResubmissionOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        this.longMsgDescription = outcome.getLongMsg();
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimContractOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        this.longMsgDescription = outcome.getLongMsg();
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimPatientOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        this.longMsgDescription = outcome.getLongMsg();
        this.claimId = claimId;
    }
    
    public Result(ScrubResponseReturnClaimPatientPatientInsuranceOutcome outcome, String claimId) {
        //this.ruleID = requestOutcome.getRuleID();
        this.ruleName = outcome.getRuleName();
        this.severity = outcome.getSeverity();
        this.shortMsgDescription = outcome.getShortMsg();
        this.longMsgDescription = outcome.getLongMsg();
        this.claimId = claimId;
    }
}
