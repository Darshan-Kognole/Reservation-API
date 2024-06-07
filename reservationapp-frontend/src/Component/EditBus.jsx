import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

const EditBus = () => {

    let [name,setName]=useState("")
    let [bus_number,setBus_number]=useState("")
    let [date_of_departure,setDate_of_deparature]=useState("")
    let [from_location,setFrom_location]=useState("")
    let [to_location,setTo_location]=useState("")
    let [no_of_seat,setNo_of_seat]=useState("")

    let params=useParams();
    useEffect(()=>{
        axios.get(`http://localhost:8080/api/bus/${params.id}`)
        .then((res)=>{
            console.log(res.data);
            setName(res.data.data.name)
            setBus_number(res.data.data.bus_number)
            setDate_of_deparature(res.data.data.date_of_deparature)
            setFrom_location(res.data.data.from_location)
            setNo_of_seat(res.data.data.no_of_seat)
            setTo_location(res.data.data.to_location)
        })
    })

    let newBus={name,date_of_departure,bus_number,from_location,to_location,no_of_seat}

    function editBus(e){
        e.preventDefault()
        axios.put(`http://localhost:8080/api/bus/${params.id}`,newBus)
        .then((res)=>{
            console.log(res);
            alert("Bus Details have been updated successfully")
        })
        .catch((err)=>{
            alert("Invalid Data format");
        })
    }

  return (
    <div>
        <form onSubmit={editBus} action="">
            <label htmlFor=""><input type="text" placeholder='Enter Bus Name' required value={name} onChange={(e)=>setName(e.target.value)}/></label>
            <label htmlFor=""><input type="text" placeholder='Enter the Bus Number' required value={bus_number} onChange={(e)=>setBus_number(e.target.value)} /></label>            
            <label htmlFor=""><input type="text" placeholder='Enter the Date of Departure' required value={date_of_departure} onChange={(e)=>setDate_of_deparature(e.target.value)} /></label>
            <label htmlFor=""><input type="text" placeholder='Enter the From location' required value={from_location} onChange={(e)=>setFrom_location(e.target.value)} /></label>
            <label htmlFor=""><input type="text" placeholder='Enter the  To location' required value={to_location} onChange={(e)=>setTo_location(e.target.value)} /></label>
            <label htmlFor=""><input type="text" placeholder='Enter the No_of_seats' required value={no_of_seat} onChange={(e)=>setNo_of_seat(e.target.value)} /></label>            
            <button>Update</button>
        </form>
    </div>
  )
}

export default EditBus