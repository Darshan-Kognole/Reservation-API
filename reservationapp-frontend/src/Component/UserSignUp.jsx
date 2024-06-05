import React, { useState } from 'react'
import '../Styles/UserSignUp.css'
import axios from 'axios';
const UserSignUp = () => {
    let [name,setName]=useState("");
    let [phone,setPhone]=useState("");
    let [email,setEmail]=useState("");
    let [age,setAge]=useState("");
    let [gender,setGender]=useState("");
    let [password,setPassword]=useState("");

    let data={
        name,phone,email,age,gender,password
    }

    function createUser(e){
        e.preventDefault();
        axios.post("http://localhost:8080/api/users",data)
        .then((res)=>{
            alert("Data Added successfully");
            console.log(res);
        })
        .catch((err)=>{
            alert("Invalid data")
            console.log(err);
        })
    }



  return (
    <div className='usersignup'>
        <form onSubmit={createUser} action="">
            <h2>User SignUp</h2>
            <label htmlFor="">
                <input type="text" placeholder='Enter the Name' value={name} onChange={(e)=>setName(e.target.value)} />
            </label>
            <label htmlFor="">
                <input type="text" placeholder='Enter the Phone number' value={phone} onChange={(e)=>setPhone(e.target.value)} />
            </label>
            <label htmlFor="">
                <input type="text" placeholder='Enter the Email' value={email} onChange={(e)=>setEmail(e.target.value)} />
            </label>
            <label htmlFor="">
                <input type="text" placeholder='Enter the Age' value={age} onChange={(e)=>setAge(e.target.value)} />
            </label>
            <label htmlFor="">
                <input type="text" placeholder='Enter the Gender' value={gender} onChange={(e)=>setGender(e.target.value)} />
            </label>
            <label htmlFor="">
                <input type="text" placeholder='Enter the Password' value={password} onChange={(e)=>setPassword(e.target.value)} />
            </label>
            <button>Submit</button>
        </form>
        <img src="https://img.freepik.com/free-vector/autonomous-public-transport-abstract-concept-vector-illustration-self-driving-bus-urban-transport-services-smart-taxi-automatic-road-service-public-bus-city-train-traffic-abstract-metaphor_335657-1771.jpg?t=st=1716965747~exp=1716969347~hmac=4689116ed1b8a658d1a895acc4b88aab1f065eb6299849ca4b2e1da7be2425dd&w=740" alt="" />
    </div>
  )
}

export default UserSignUp