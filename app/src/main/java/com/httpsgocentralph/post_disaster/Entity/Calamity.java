package com.httpsgocentralph.post_disaster.Entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.httpsgocentralph.post_disaster.R;

public class Calamity {
    String id;
    String accountId;
    String name;
    String date;
    String damageStatus;
    String dataAmount;
    String status;
    String createdAt;
    String updatedAt;
    String deletedAt;

    public Calamity(String id, String accountId, String name, String date, String damageStatus, String dataAmount, String status, String createdAt, String updatedAt, String deletedAt) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.date = date;
        this.damageStatus = damageStatus;
        this.dataAmount = dataAmount;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDamageStatus() {
        return damageStatus;
    }

    public void setDamageStatus(String damageStatus) {
        this.damageStatus = damageStatus;
    }

    public String getDataAmount() {
        return dataAmount;
    }

    public void setDataAmount(String dataAmount) {
        this.dataAmount = dataAmount;
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
