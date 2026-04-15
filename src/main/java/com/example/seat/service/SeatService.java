package com.example.seat.service;

import com.example.seat.dto.SeatViewResponse;
import com.example.seat.dto.EmployeeResponse;
import com.example.seat.repository.SeatRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class SeatService {
    
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository){
        this.seatRepository=seatRepository;
    }
    public List<SeatViewResponse> getAllSeats() {
        return seatRepository.getAllSeats();
    }

    public List<EmployeeResponse> getAllEmployees() {
        return seatRepository.getAllEmployees();
    }


    public void AssignSeat(String empId, Long seatId){
        //System.out.println("=== Service assignSeat entered ===");
        try {
            seatRepository.AssignSeat(empId, seatId);
           // System.out.println("=== Service assignSeat success ===");
        } catch (Exception e) {
            //System.out.println("=== Service caught exception ===");
            //e.printStackTrace();
            String msg = e.getMessage();
            if (msg != null && msg.contains("Seat is already occupied")) {
                throw new RuntimeException("Seat is already occupied");
            }

            if (msg != null && msg.contains("Seat does not exist")) {
                throw new RuntimeException("Seat does not exist");
            }

            throw new RuntimeException("Assign seat failed");
        
        }
        
    }
    public void ClearSeat(Long seatId){
        try {
            seatRepository.ClearSeat(seatId);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Seat does not exist")) {
                throw new RuntimeException("Seat does not exist");
            }
            throw new RuntimeException("Clear seat failed");

        }
        
    }
}
