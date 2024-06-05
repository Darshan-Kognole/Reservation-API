import React from 'react'
import NavDropDown from './NavDropDown'
import '../Styles/AdminNavbar.css'
const AdminNavbar = () => {
  return (
    <div className='adminNavbar'>
        <div className='logo'>
            <h1><i>yatra</i><sup><i>.in</i></sup></h1>
        </div>
        <div className='options'>
            <NavDropDown/>
        </div>
    </div>
  )
}

export default AdminNavbar