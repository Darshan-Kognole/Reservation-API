import React, { useState } from 'react'
import '../Styles/AdminLogin.css'
import {Link, json, useNavigate} from 'react-router-dom'
import axios from 'axios'
const AdminLogin = () => {

  let [email,setUsername]=useState("")
  let [password,setPassword]=useState("")
  let navigate = useNavigate()
  function verifyAdmin(e){
    e.preventDefault();
    axios.post(`http://localhost:8080/api/admins/verify-by-email?email=${email}&password=${password}`)
    .then((res)=>{
      navigate('/adminhomepage');
      alert("Login Successfull")
      console.log(res.data.data);
      localStorage.setItem("Admin",JSON.stringify(res.data.data))

    })
    .catch((err)=>{
      alert("Login failed")
    })
  }
  
  return (
    <div className='adminlogin'>
        <form onSubmit={verifyAdmin}>
            <h2>Admin Login</h2>
            <label htmlFor="">
                <input type="text" placeholder='Enter Your username' value={email} onChange={(e)=>{setUsername(e.target.value)}} />
            </label>
            <label htmlFor="">
                <input type="text" placeholder='Enter Your Password' value={password} onChange={(e)=>{setPassword(e.target.value)}} />
            </label>
            <button>Submit</button>
            <p>Are you the new user ? <Link to="/adminsignup">Register here....</Link></p>
        </form>
        <img src="https://img.freepik.com/free-vector/sentiment-analysis-concept-illustration_114360-5182.jpg?t=st=1716964983~exp=1716968583~hmac=b6756a41d65403b420b0d907b3ba0e0ce176d0b34586c030febf33f31af3de82&w=740" alt="" />
    </div>
  )
}

export default AdminLogin