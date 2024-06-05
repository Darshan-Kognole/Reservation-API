import React from 'react'
import {Link} from 'react-router-dom'
import '../Styles/LandingPage.css'
const LandingPage = () => {
  return (
    <div className='landingpage'>
        <Link to="/adminlogin">
            <img src="https://i.pinimg.com/736x/6a/44/f0/6a44f0e35b10e6ed063eeebf7ed844f9.jpg" alt="" />
            <h2>Admin</h2>
        </Link>
        <Link to="/userlogin">
            <img src="https://www.spotteron.net/images/icons/user60.png" alt="" />
            <h2>User</h2>
        </Link>
    </div>
  )
}

export default LandingPage