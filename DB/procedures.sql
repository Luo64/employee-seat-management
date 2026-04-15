DELIMITER $$

CREATE PROCEDURE sp_assign_seat(
    IN p_emp_id CHAR(5),
    IN p_seat_id BIGINT
)
BEGIN
    DECLARE s_exist INT;
    DECLARE s_availavle INT;

    -- check seat exist
    SELECT COUNT(*) INTO s_exist
    FROM seating_chart
    WHERE floor_seat_seq = p_seat_id;

    IF s_exist = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Seat does not exist';
    END IF;

    -- check seat available
    SELECT COUNT(*) INTO s_availavle
    FROM employees
    WHERE floor_seat_seq = p_seat_id;

    IF s_availavle > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Seat is already occupied';
    END IF;
	
    -- remove seat
    UPDATE employees
    SET floor_seat_seq = NULL
    WHERE emp_id = p_emp_id;
    
    -- assign seat
    UPDATE employees
    SET floor_seat_seq = p_seat_id
    WHERE emp_id = p_emp_id;

END $$

CREATE PROCEDURE sp_clear_seat(
    IN p_seat_id BIGINT
)
BEGIN
    DECLARE s_exist INT;

    -- check seat exist
    SELECT COUNT(*) INTO s_exist
    FROM seating_chart
    WHERE floor_seat_seq = p_seat_id;

    IF s_exist = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Seat does not exist';
    END IF;

    UPDATE employees
    SET floor_seat_seq = NULL
    WHERE floor_seat_seq = p_seat_id;

END $$

DELIMITER;