import React, { useState } from 'react'
import '../Styles/AdminSignUp.css'
import axios from 'axios';
const AdminSignUp = () => {
    let [name,setName]=useState("");
    let [phone,setPhone]=useState("");
    let [email,setEmail]=useState("");
    let [gst_number,setGst_number]=useState("");
    let [travels_name,setTravels_name]=useState("");
    let [password,setPassword]=useState("");

    let data={
      name,phone,email,gst_number,travels_name,password
    }

    function createAdmin(e){
      e.preventDefault();
      axios.post("http://localhost:8080/api/admins",data)
      .then((res)=>{
        alert("data added successfully");
        console.log(res);
      })
      .catch((err)=>{
        alert("Invalid data");
        console.log(err);
      })
    }

  return (
    <div className='adminsignup'>
      <img src="https://img.freepik.com/free-vector/usability-testing-concept-illustration_114360-1878.jpg?t=st=1717006894~exp=1717010494~hmac=1c556a5bcb097a0e988b45ea6cfd4028fdb0e71f435d8a1109e7dff577049343&w=740" alt="" />
        <form onSubmit={createAdmin} action="">
            <h2>Admin SignUp</h2>
            <label htmlFor=""></label><input type="text" placeholder='Enter the name' value={name} onChange={(e)=>setName(e.target.value)} />
            <label htmlFor=""></label><input type="tel" placeholder='Enter the Phone Number' value={phone} onChange={(e)=>setPhone(e.target.value)} />
            <label htmlFor=""></label><input type="email" placeholder='Enter the Email' value={email} onChange={(e)=>setEmail(e.target.value)} />
            <label htmlFor=""></label><input type="text" placeholder='Enter the Gst Number' value={gst_number} onChange={(e)=>setGst_number(e.target.value)} />
            <label htmlFor=""></label><input type="text" placeholder='Enter the TravelsName' value={travels_name} onChange={(e)=>setTravels_name(e.target.value)} />
            <label htmlFor=""></label><input type="text" placeholder='Enter the Password' value={password} onChange={(e)=>setPassword(e.target.value)} />
            <button>SignUp</button>
        </form>
    </div>
  )
}

export default AdminSignUp