package com.example.spectra;

public class UserInformation {
    public String Name;
    public String SID;
    public String Branch;

    public UserInformation(){

    }

    public UserInformation(String name, String SID, String branch) {
        Name = name;
        this.SID = SID;
        Branch = branch;
    }
}
