package com.example.goodhouse;

public class Complaint {
    //System.currenttime형식으로 받기때문에 long
    String time;
    String complaintKind;
    int target;
    int result;

    Complaint(String time, String complaintKind, int target, int result){
        this.time = time;
        this.complaintKind = complaintKind;
        this.target = target;
        this.result = result;
    }
}
