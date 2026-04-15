CREATE DATABASE IF NOT EXISTS seat_management_db;
USE seat_management_db;

CREATE TABLE seating_chart (
    floor_seat_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    floor_no INT NOT NULL,
    seat_no INT NOT NULL UNIQUE
);

CREATE TABLE employees (
    emp_id CHAR(5) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    floor_seat_seq BIGINT UNIQUE,
    CONSTRAINT fk_employee_seat
        FOREIGN KEY (floor_seat_seq)
        REFERENCES seating_chart(floor_seat_seq)
);