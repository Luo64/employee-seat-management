package com.example.seat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.seat.dto.SeatViewResponse;
import com.example.seat.common.ApiResponse;
import com.example.seat.dto.EmployeeResponse;
import com.example.seat.service.SeatService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/seat")
public class SeatController {
    private final SeatService seatService;
    
    public SeatController(SeatService seatService){
        this.seatService=seatService;
    }

    @GetMapping("/all")
        public List<SeatViewResponse> getAllSeats(){
            return seatService.getAllSeats();
        }
    
     @GetMapping("/employees")
        public List<EmployeeResponse> getAllEmployees(){
            return seatService.getAllEmployees();
        }

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<Object>> assignSeat(@RequestParam String empId, @RequestParam Long seatId){
        //System.out.println("=== Controller assign ===");
        //System.out.println("empId = " + empId + ", seatId = " + seatId);
        if (!empId.matches("\\d{5}")){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid employee ID",null));
        }
        seatService.AssignSeat(empId, seatId);
        return ResponseEntity.ok(new ApiResponse<>("Assigned Successfully",null));
        
            //System.out.println("=== Controller caught exception ===");
            //e.printStackTrace();
            //return ResponseEntity.badRequest().body("Failed" + e.getMessage());
        
    }
    @PostMapping("/clear")
    public ResponseEntity<ApiResponse<Object>> clearSeat(@RequestParam Long seatId){
        
            seatService.ClearSeat(seatId);
            return ResponseEntity.ok(new ApiResponse<>("Clear Successfully",null));
        
            //return ResponseEntity.badRequest().body("Failed,"+e.getMessage()) ;
        
    }   
}
