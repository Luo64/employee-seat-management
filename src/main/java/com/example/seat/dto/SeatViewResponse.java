package com.example.seat.dto;

import java.security.PublicKey;

public class SeatViewResponse {
    private Integer floorNo;
    private Integer seatNo;
    private String empId;
    private Long floorSeatSeq;

    public SeatViewResponse(){}

    public SeatViewResponse(Long floorSeatSeq,Integer floorNo,Integer seatNo,String empId){
        this.floorSeatSeq=floorSeatSeq;
        this.floorNo=floorNo;
        this.seatNo=seatNo;
        this.empId=empId;
    }

    public Long getFloorSeatSeq() {
        return floorSeatSeq;
    }

    public void setFloorSeatSeq(Long floorSeatSeq) {
        this.floorSeatSeq = floorSeatSeq;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    
}
