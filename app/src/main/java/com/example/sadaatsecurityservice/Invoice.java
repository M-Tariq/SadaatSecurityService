package com.example.sadaatsecurityservice;

import androidx.annotation.NonNull;

public class Invoice {
    private String guardName;
    private int quantity;
    private double totalAmount;
    private int rate;
    private String issueDate;
    private String sendTo;
    private String billOfMonth;
    private String startPeriodDate;
    private String endPeriodDate;

    public Invoice() {
    }
    public String getGuardName() {
        return guardName;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getBillOfMonth() {
        return billOfMonth;
    }

    public void setBillOfMonth(String billOfMonth) {
        this.billOfMonth = billOfMonth;
    }

    public String getStartPeriodDate() {
        return startPeriodDate;
    }

    public void setStartPeriodDate(String startPeriodDate) {
        this.startPeriodDate = startPeriodDate;
    }

    public String getEndPeriodDate() {
        return endPeriodDate;
    }

    public void setEndPeriodDate(String endPeriodDate) {
        this.endPeriodDate = endPeriodDate;
    }
}
