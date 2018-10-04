package com.httpsgocentralph.post_disaster.Entity;

public class CalamityName {
    String id;
    String calamityId;
    String householdNameId;
    String status;
    String createdAt;
    String updatedAt;
    String deletedAt;

    public CalamityName(String id, String calamityId, String householdNameId, String status, String createdAt, String updatedAt, String deletedAt) {
        this.id = id;
        this.calamityId = calamityId;
        this.householdNameId = householdNameId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalamityId() {
        return calamityId;
    }

    public void setCalamityId(String calamityId) {
        this.calamityId = calamityId;
    }

    public String getHouseholdNameId() {
        return householdNameId;
    }

    public void setHouseholdNameId(String householdNameId) {
        this.householdNameId = householdNameId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
