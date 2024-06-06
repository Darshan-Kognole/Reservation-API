import axios from 'axios'
import React, { useEffect, useState } from 'react'
import '../Styles/ViewBus.css'
const ViewBus = () => {
    let [bus, setBus] = useState([])

    useEffect(() => {
        axios.get("http://localhost:8080/api/bus")
            .then((res) => {
                console.log(res);
                setBus(Array.isArray(res.data.data) ? res.data.data : [])
            })
            .catch((err) => {
                console.log(err);
            })
    }, [])

    function removeBus(id,bus_number){
        axios.delete(`http://localhost:8080/api/bus/${id}`)
        .then((res)=>{
            alert(`Bus Number ${bus_number} has been deleted`)
        })
        .catch((err)=>{
            alert("Cannot Remove bus")
        })
    }

    // function editBus(bus){
    //     axios.put(`http://localhost:8080/api/bus/${id}`)
    //     .then((res)=>{
    //         alert('Bus details Updated')
    //     })
    //     .catch((err)=>{
    //         alert('Cannot updated some validation error')
    //     })
    // }

    return (
        <div className='viewBus'>
            <div className="heading">
                <h4 className='col1'>Name</h4>
                <h4 className='col2'>Seats</h4>
                <h4 className='col3'>From</h4>
                <h4 className='col4'>To</h4>
                <h4 className='col5'>Date</h4>
                <h4 className='col6'>Bus Number</h4>
                <h4 className='col7'>Edit</h4>
                <h4 className='col8'>Delete</h4>
            </div>
            {Array.isArray(bus) && bus.map((items, index) => (
                <div key={index} className='busdetails'>
                    <h4>{items.name}</h4>
                    <i>{items.no_of_seat}</i>
                    <p>{items.from_location}</p>
                    <p>{items.to_location}</p>
                    <p>{items.date_of_departure}</p>
                    <span>{items.bus_number}</span>
                    <button className='btn btn-warning'>Edit</button>
                    <button className='btn btn-danger' onClick={()=>{removeBus(items.id,items.bus_number)}}>Delete</button>
                </div>
            ))}
        </div>
    )
}

export default ViewBus
