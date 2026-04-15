package com.example.seat.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.seat.dto.SeatViewResponse;
import com.example.seat.dto.EmployeeResponse;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

import java.security.DrbgParameters.Reseed;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SeatRepository {

    private final JdbcTemplate jdbcTemplate;
     
    public SeatRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public void AssignSeat(String empId, Long seatId){
        jdbcTemplate.update("CALL sp_assign_seat(?,?)", empId,seatId);
    }
    public void ClearSeat(Long seatId){
        jdbcTemplate.update("CALL sp_clear_seat(?)", seatId);
    }

    public List<SeatViewResponse> getAllSeats(){
        String sql="""
                SELECT
                    s.floor_seat_seq,
                    s.floor_no,
                    s.seat_no,
                    e.emp_id
                FROM seating_chart s
                LEFT JOIN employees e
                    ON s.floor_seat_seq = e.floor_seat_seq
                ORDER BY s.floor_no, s.seat_no;
                """;

        return jdbcTemplate.query(sql, new RowMapper<SeatViewResponse>() {
            @Override
            public SeatViewResponse mapRow(ResultSet rs, int rowNum) throws SQLException{
                return new SeatViewResponse(
                    rs.getLong("floor_seat_seq"),
                    rs.getInt("floor_no"),
                    rs.getInt("seat_no"),
                    rs.getString("emp_id")
                );
            }
            
        });

    }

    public List<EmployeeResponse> getAllEmployees(){
        String sql="""
                SELECT emp_id
                FROM employees
                ORDER BY emp_id
                """;
        
        return jdbcTemplate.query(sql, new RowMapper<EmployeeResponse>(){
            @Override
            public EmployeeResponse mapRow(ResultSet rs, int rowNum) throws SQLException{
                return new EmployeeResponse(
                    rs.getString("emp_id")
                );
            }
        });
    }
    
}
