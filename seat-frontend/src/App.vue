<script setup>
import { ref, onMounted, computed } from 'vue'
import { watch } from 'vue'

const seats = ref([])
const employees = ref([])
const selectedEmpId= ref('')
const selectedSeatNo = ref(null)
const showAssignModal = ref(false)
const displaySeatNumber = (seatNo) => {
  return seatNo % 100
}

const fetchSeats = async () => {
  const res = await fetch('http://localhost:8080/seat/all')
  seats.value = await res.json()
}

const fetchEmployees = async () => {
  const res = await fetch('http://localhost:8080/seat/employees')
  employees.value = await res.json()
}

onMounted(() => {
  fetchSeats()
  fetchEmployees()
})
const groupedSeats = computed (()=>{
  const groups = {}
  for (const seat of seats.value){
    if (!groups[seat.floorNo]){
      groups[seat.floorNo]=[]
    }
    groups[seat.floorNo].push(seat)
  }
  return groups
})

const getSeatClass = (seat) => {
  if (selectedSeatNo.value === seat.seatNo){
    return 'seat-card selected'
  }
  if (seat.empId){
    return 'seat-card occupied'
  }
  return 'seat-card empty'
}

const handleSeatClick = async(seat) => {
  if (seat.empId){
    const confirmClear=confirm(
      `Seat already occupied by ${seat.empId}. Clear this seat?`
    )
    if (!confirmClear) return
    const res = await fetch(
      `http://localhost:8080/seat/clear?seatId=${seat.floorSeatSeq}`,
      {
        method: 'POST'
      }
    )
    const result= await res.json()
    if (!res.ok){
      alert(result.message)
      return
    }
    alert(result.message)
    await fetchSeats()
    return
  }
  if (!selectedEmpId.value){
    alert('Please selecte an employee first.')
    return
  }
  selectedSeatNo.value=seat.seatNo
  setTimeout(() => {
    showAssignModal.value = true
  },250)
}

const assignSeat =async()=>{
  if (!selectedEmpId.value){
    alert('Please select an employee first. ')
  }
  if (!selectedSeatNo.value){
    alert('Please select an empty seat first. ')
  }
  const selectedSeat = seats.value.find(
    (seat)=>seat.seatNo===selectedSeatNo.value
  )
  if(!selectedSeat){
    alert('Selected seat no found. ')
    return
  }
  const res=await fetch(
     `http://localhost:8080/seat/assign?empId=${selectedEmpId.value}&seatId=${selectedSeat.floorSeatSeq}`,
    {
      method: 'POST'
    }
  )
  const result =await res.json()
   if (!res.ok) {
    alert(result.message)
    return
  }

  alert(result.message)

  selectedSeatNo.value = null
  selectedEmpId.value = ''
  showAssignModal.value=false
  await fetchSeats()
}
const cancelAssign = () => {
  showAssignModal.value=false
  //selectedSeatNo.value=null
}
watch(selectedEmpId, () => {
  selectedSeatNo.value = null
  showAssignModal.value = false
})

</script>

<template>
  <div class="container">
    <h1>Seat Management</h1>
    <div class="control-section">
      <h2>Employees</h2>
      <select v-model="selectedEmpId">
        <option value="">Please select</option>
        <option
          v-for="emp in employees"
          :key="emp.empId"
          :value="emp.empId"
          >
          {{ emp.empId }}
        </option>
      </select>
    </div>
    <div class="seat-section">
      <h2>Seats</h2>
      <div
        v-for="(floorSeats, floorNo) in groupedSeats"
        :key="floorNo"
        class="floor-block"
        >
          <h3>{{ floorNo }} Floor</h3>
          <div class="seat-grid">
            <div
            v-for="seat in floorSeats"
            :key="seat.seatNo"
            :class="getSeatClass(seat)"
            @click="handleSeatClick(seat)"
            >
            <div>{{ seat.floorNo }} Floor</div>
            <div>Seat {{displaySeatNumber(seat.seatNo)}}</div>
            <div v-if="seat.empId">[Emp ID:{{ seat.empId }}]</div>
          </div>
        </div>
      </div>
    </div>
    <div class="legend">
      <div class="legend-item">
        <span class="legend-box empty"></span>
        <span>Empty</span>
      </div>
       <div class="legend-item">
        <span class="legend-box occupied"></span>
        <span>Occupied</span>
      </div>
       <div class="legend-item">
        <span class="legend-box selected"></span>
        <span>Selected</span>
      </div>
    </div>
  </div>
  <div v-if="showAssignModal" class="modal-overlay">
  <div class="modal-box">
    <h3>Confirm Assignment</h3>
    <p>Employee: {{ selectedEmpId }}</p>
    <p>Seat: {{ selectedSeatNo }}</p>

    <div class="modal-actions">
      <button class="submit-btn" @click="assignSeat">Submit</button>
      <button class="cancel-btn" @click="cancelAssign">Cancel</button>
    </div>
  </div>
</div>
</template>
<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 32px;
}

h2 {
  text-align: center;
  margin-bottom: 16px;
}

h3 {
  margin-bottom: 16px;
}

.control-section {
  margin-bottom: 32px;
}

select {
  width: 100%;
  padding: 10px;
  font-size: 16px;
}

.floor-block {
  margin-bottom: 32px;
}

.seat-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.seat-card {
  border-radius: 12px;
  padding: 24px;
  text-align: center;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border: 1px solid #ddd;
}

.empty {
  background-color: #DCDCDC;
  color: #222;
}

.occupied {
  background-color: red;
  color: white;
  cursor: pointer;
}

.selected {
  background-color: lightgreen;
  color: #111;
}

.legend {
  margin-top: 32px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.legend-box {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  display: inline-block;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.modal-box {
  background: white;
  padding: 24px;
  border-radius: 12px;
  width: 320px;
  text-align: center;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
  transform: translateY(-40px);
}

.modal-box h3 {
  margin-bottom: 16px;
}

.modal-box p {
  margin: 8px 0;
  font-size: 16px;
}

.modal-actions {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 16px;
}

.submit-btn {
  background-color: #0b4ea2;
  color: white;
  border: none;
  padding: 10px 18px;
  border-radius: 6px;
  cursor: pointer;
}

.cancel-btn {
  background-color: #cccccc;
  color: black;
  border: none;
  padding: 10px 18px;
  border-radius: 6px;
  cursor: pointer;
}
</style>