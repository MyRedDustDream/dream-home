//package com.dream.home.common.entity;
//
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//
////
////	RootClass.java
////	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport
//
//
//public class RootClass {
//
//    @SerializedName("adjustFlag")
//    private String adjustFlag;
//    @SerializedName("adjustTypeId")
//    private int adjustTypeId;
//    @SerializedName("companyId")
//    private int companyId;
//    @SerializedName("direction")
//    private String direction;
//    @SerializedName("hotelId")
//    private int hotelId;
//    @SerializedName("manualEntryAccountFlag")
//    private String manualEntryAccountFlag;
//    @SerializedName("operId")
//    private int operId;
//    @SerializedName("operMobile")
//    private String operMobile;
//    @SerializedName("operType")
//    private String operType;
//    @SerializedName("operUser")
//    private String operUser;
//    @SerializedName("orderBy")
//    private String orderBy;
//    @SerializedName("pageNo")
//    private Object pageNo;
//    @SerializedName("pageSize")
//    private Object pageSize;
//    @SerializedName("refundItemFlag")
//    private String refundItemFlag;
//    @SerializedName("status")
//    private String status;
//    @SerializedName("summary")
//    private String summary;
//    @SerializedName("transCode")
//    private String transCode;
//    @SerializedName("typeId")
//    private int typeId;
//    @SerializedName("voucherType")
//    private String voucherType;
//
//    /**
//     * Instantiate the instance using the passed jsonObject to set the properties values
//     */
//    public RootClass(JSONObject jsonObject) {
//        if (jsonObject == null) {
//            return;
//        }
//        adjustFlag = jsonObject.optString("adjustFlag");
//        adjustTypeId = jsonObject.optInt("adjustTypeId");
//        companyId = jsonObject.optInt("companyId");
//        direction = jsonObject.optString("direction");
//        hotelId = jsonObject.optInt("hotelId");
//        manualEntryAccountFlag = jsonObject.optString("manualEntryAccountFlag");
//        operId = jsonObject.optInt("operId");
//        operMobile = jsonObject.optString("operMobile");
//        operType = jsonObject.optString("operType");
//        operUser = jsonObject.optString("operUser");
//        orderBy = jsonObject.optString("orderBy");
//        pageNo = jsonObject.opt("pageNo");
//        pageSize = jsonObject.opt("pageSize");
//        refundItemFlag = jsonObject.optString("refundItemFlag");
//        status = jsonObject.optString("status");
//        summary = jsonObject.optString("summary");
//        transCode = jsonObject.optString("transCode");
//        typeId = jsonObject.optInt("typeId");
//        voucherType = jsonObject.optString("voucherType");
//    }
//
//    public String getAdjustFlag() {
//        return adjustFlag;
//    }
//
//    public void setAdjustFlag(String adjustFlag) {
//        this.adjustFlag = adjustFlag;
//    }
//
//    public int getAdjustTypeId() {
//        return adjustTypeId;
//    }
//
//    public void setAdjustTypeId(int adjustTypeId) {
//        this.adjustTypeId = adjustTypeId;
//    }
//
//    public int getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(int companyId) {
//        this.companyId = companyId;
//    }
//
//    public String getDirection() {
//        return direction;
//    }
//
//    public void setDirection(String direction) {
//        this.direction = direction;
//    }
//
//    public int getHotelId() {
//        return hotelId;
//    }
//
//    public void setHotelId(int hotelId) {
//        this.hotelId = hotelId;
//    }
//
//    public String getManualEntryAccountFlag() {
//        return manualEntryAccountFlag;
//    }
//
//    public void setManualEntryAccountFlag(String manualEntryAccountFlag) {
//        this.manualEntryAccountFlag = manualEntryAccountFlag;
//    }
//
//    public int getOperId() {
//        return operId;
//    }
//
//    public void setOperId(int operId) {
//        this.operId = operId;
//    }
//
//    public String getOperMobile() {
//        return operMobile;
//    }
//
//    public void setOperMobile(String operMobile) {
//        this.operMobile = operMobile;
//    }
//
//    public String getOperType() {
//        return operType;
//    }
//
//    public void setOperType(String operType) {
//        this.operType = operType;
//    }
//
//    public String getOperUser() {
//        return operUser;
//    }
//
//    public void setOperUser(String operUser) {
//        this.operUser = operUser;
//    }
//
//    public String getOrderBy() {
//        return orderBy;
//    }
//
//    public void setOrderBy(String orderBy) {
//        this.orderBy = orderBy;
//    }
//
//    public Object getPageNo() {
//        return pageNo;
//    }
//
//    public void setPageNo(Object pageNo) {
//        this.pageNo = pageNo;
//    }
//
//    public Object getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(Object pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public String getRefundItemFlag() {
//        return refundItemFlag;
//    }
//
//    public void setRefundItemFlag(String refundItemFlag) {
//        this.refundItemFlag = refundItemFlag;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getSummary() {
//        return summary;
//    }
//
//    public void setSummary(String summary) {
//        this.summary = summary;
//    }
//
//    public String getTransCode() {
//        return transCode;
//    }
//
//    public void setTransCode(String transCode) {
//        this.transCode = transCode;
//    }
//
//    public int getTypeId() {
//        return typeId;
//    }
//
//    public void setTypeId(int typeId) {
//        this.typeId = typeId;
//    }
//
//    public String getVoucherType() {
//        return voucherType;
//    }
//
//    public void setVoucherType(String voucherType) {
//        this.voucherType = voucherType;
//    }
//
//    /**
//     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
//     */
//    public JSONObject toJsonObject() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("adjustFlag", adjustFlag);
//            jsonObject.put("adjustTypeId", adjustTypeId);
//            jsonObject.put("companyId", companyId);
//            jsonObject.put("direction", direction);
//            jsonObject.put("hotelId", hotelId);
//            jsonObject.put("manualEntryAccountFlag", manualEntryAccountFlag);
//            jsonObject.put("operId", operId);
//            jsonObject.put("operMobile", operMobile);
//            jsonObject.put("operType", operType);
//            jsonObject.put("operUser", operUser);
//            jsonObject.put("orderBy", orderBy);
//            jsonObject.put("pageNo", pageNo);
//            jsonObject.put("pageSize", pageSize);
//            jsonObject.put("refundItemFlag", refundItemFlag);
//            jsonObject.put("status", status);
//            jsonObject.put("summary", summary);
//            jsonObject.put("transCode", transCode);
//            jsonObject.put("typeId", typeId);
//            jsonObject.put("voucherType", voucherType);
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }
//
//}