import React, { useState } from 'react'
import '../Styles/AddBus.css'
import axios from 'axios'
const AddBus = () => {
    let [name,setName]=useState("")
    let [bus_number,setBus_number]=useState("")
    let [date_of_deparature,setDate_of_deparature]=useState("")
    let [from_location,setFrom_location]=useState("")
    let [to_location,setTo_location]=useState("")
    let [no_of_seat,setNo_of_seat]=useState("")

    let busData={
        name,date_of_deparature,bus_number,from_location,to_location,no_of_seat
    }

    let admin=JSON.parse(localStorage.getItem("Admin"))
    // console.log(admin);
    // console.log(typeof(admin));
    function addbusData(e){
        e.preventDefault()
        axios.post(`http://localhost:8080/api/bus/${admin.id}`,busData)
        .then((res)=>{
            console.log(res);
            alert("Bus details have been added successfully")
        })
        .catch((err)=>{
            console.log(err);
            alert("Invalid data")
        })
    }

  return (
    <div className='addbus'>
        <img src="https://img.freepik.com/free-vector/taxi-app-concept_23-2148490652.jpg?t=st=1717404756~exp=1717408356~hmac=65dfbc6125fbf7d3a83c9e2030ab2786c5d4be4cb086c21668ce2601c378caf4&w=740" alt="" />
        <form onSubmit={addbusData} action="">
            <label htmlFor=""><input type="text" placeholder='Enter Bus Name' required value={name} onChange={(e)=>setName(e.target.value)}/></label>
            <label htmlFor=""><input type="text" placeholder='Enter the Bus Number' required value={bus_number} onChange={(e)=>setBus_number(e.target.value)} /></label>            
            <label htmlFor=""><input type="text" placeholder='Enter the Date of Departure' required value={date_of_deparature} onChange={(e)=>setDate_of_deparature(e.target.value)} /></label>
            <label htmlFor=""><input type="text" placeholder='Enter the From location' required value={from_location} onChange={(e)=>setFrom_location(e.target.value)} /></label>
            <label htmlFor=""><input type="text" placeholder='Enter the  To location' required value={to_location} onChange={(e)=>setTo_location(e.target.value)} /></label>
            <label htmlFor=""><input type="text" placeholder='Enter the No_of_seats' required value={no_of_seat} onChange={(e)=>setNo_of_seat(e.target.value)} /></label>            
            <button>Submit</button>
        </form>
    </div>
  )
}

export default AddBus