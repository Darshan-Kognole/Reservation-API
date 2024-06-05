import React, { useState } from 'react'
import '../Styles/UserLogin.css'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios'
const UserLogin = () => {

  let [email,setUsername]=useState("")
  let [password,setPassword]=useState("")
  let navigate =useNavigate()

  function verifyUser(e){
    e.preventDefault();
    axios.post(`http://localhost:8080/api/users/verify-by-email?email=${email}&password=${password}`)
    .then((res)=>{
      navigate('/userhomepage');
      alert("Login Successfull")
    })
    .catch((err)=>{
      alert("Login failed")
    })
  }
  return (
    <div className='userlogin'>
        <img src="https://img.freepik.com/free-vector/flat-public-transport-background-with-two-human-characters-bus-city-map-vector-illustration_1284-75456.jpg?t=st=1717007418~exp=1717011018~hmac=6c01d1451f11102db715fabfd852826e88e335a6adf0e7960c6b80aeb34175dc&w=826" alt="" />
        <form onSubmit={verifyUser} action="">
        <h2>User Login</h2>
            <label htmlFor="">
                <input type="text" placeholder='Enter Username' value={email} onChange={(e)=>{setUsername(e.target.value)}}/>
            </label>
            <label htmlFor="">
                <input type="text" placeholder='Enter Password' value={password} onChange={(e)=>{setPassword(e.target.value)}}/>
            </label>
            <button>Submit</button>
            <p>Are you the new user ? <Link to="/usersignup">Register here....</Link></p>
        </form>
    </div>
  )
}

export default UserLogin