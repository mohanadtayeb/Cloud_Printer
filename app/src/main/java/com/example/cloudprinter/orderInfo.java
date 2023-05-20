package com.example.cloudprinter;

class orderInfo {

    public String getBind_paper() {
        return bind_paper;
    }

    public void setBind_paper(String bind_paper) {
        this.bind_paper = bind_paper;
    }

    public String getColored() {
        return colored;
    }

    public void setColored(String colored) {
        this.colored = colored;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlastic_cover() {
        return plastic_cover;
    }

    public void setPlastic_cover(String plastic_cover) {
        this.plastic_cover = plastic_cover;
    }

public orderInfo(){}
    private String bind_paper;
private String colored;
private String copies;
private String cover;
private String email;
private String location;
private String plastic_cover;

    public String getOrder_file_ref() {
        return order_file_ref;
    }

    public void setOrder_file_ref(String order_file_ref) {
        this.order_file_ref = order_file_ref;
    }

    private String order_file_ref;

    public String getLib_location() {
        return lib_location;
    }

    public void setLib_location(String lib_location) {
        this.lib_location = lib_location;
    }

    private String lib_location;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;



}
