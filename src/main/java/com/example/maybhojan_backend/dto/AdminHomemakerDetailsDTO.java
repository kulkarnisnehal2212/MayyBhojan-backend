package com.example.maybhojan_backend.dto;

public class AdminHomemakerDetailsDTO {

	private Long id;
	private String name;
	private String email;
	private String phone;
	private String accountStatus;

	private String address;
	private String dob;

    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;

    private String govtIdUrl;
    private String fssaiUrl;
    private String kitchenPhotoUrl;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getGovtIdUrl() {
        return govtIdUrl;
    }

    public void setGovtIdUrl(String govtIdUrl) {
        this.govtIdUrl = govtIdUrl;
    }

    public String getFssaiUrl() {
        return fssaiUrl;
    }

    public void setFssaiUrl(String fssaiUrl) {
        this.fssaiUrl = fssaiUrl;
    }

    public String getKitchenPhotoUrl() {
        return kitchenPhotoUrl;
    }

    public void setKitchenPhotoUrl(String kitchenPhotoUrl) {
        this.kitchenPhotoUrl = kitchenPhotoUrl;
    }
}