## Employee Seat Management System
## Technologies Used
    -Fronted:Vue.js
    -Backed End:Spring Boot
    -Database:MySQL
    -Database Access:Stored Procedures
    -Build Tool:Maven
## Features
    -呈現各樓層座位
    -不同顏色來呈現各樓層空位或己佔用座位（選擇中：綠、已選：紅、可選：灰）
    -員工只能選擇一個座位（若是已選可以點按座位取消）
    -座位取消後，會變回可選
    -先選員工編號，再選位，之後送出到資料庫
## Project Structure
    SEAT/
    ├── DB/
    │   ├── schema.sql
    │   ├── data.sql
    │   └── procedures.sql
    ├── seat-frontend/
    ├── src/
    ├── pom.xml
    └── README.md
### A. Database Setup
    1. 建立資料庫在MySQL
    2. 執行DB資料夾：schema.sql, data.sql, procedures.sql (依序進行)
### B. Backend Setup
    -bash ./mvnw spring-boot:run
##  C. Frontend Setup
    -bash cd seat-fronted
    -bash npm install
    -bash npm run dev
### API Endpoints
	-GET /seat/all
	-GET /seat/employees
	-POST /seat/assign
	-POST /seat/clear