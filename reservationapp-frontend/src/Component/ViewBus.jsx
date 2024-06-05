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

    return (
        <div className='viewBus'>
            <div className="heading">
                <h4>Name</h4>
                <h4>Seats</h4>
                <h4>From</h4>
                <h4>To</h4>
                <h4>Date</h4>
                <h4>Bus Number</h4>
                <h4>Booking</h4>
            </div>
            {Array.isArray(bus) && bus.map((items, index) => (
                <div key={index} className='busdetails'>
                    <h4>{items.name}</h4>
                    <i>{items.no_of_seat}</i>
                    <p>{items.from_location}</p>
                    <p>{items.to_location}</p>
                    <p>{items.date_of_departure}</p>
                    <span>{items.bus_number}</span>
                    <button>Book Seats</button>
                </div>
            ))}
        </div>
    )
}

export default ViewBus
