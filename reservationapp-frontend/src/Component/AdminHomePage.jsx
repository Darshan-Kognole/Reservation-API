import React from 'react'
import AdminNavbar from './AdminNavbar'
import AdminDashBoard from './AdminDashBoard'
import { Route, Routes } from 'react-router-dom'
import AddBus from './AddBus'
import ViewBus from './ViewBus.jsx'

const AdminHomePage = () => {
  return (
    <div>
        <AdminNavbar/>
        <Routes>
            <Route path='/' element={<AdminDashBoard/>}></Route>
            <Route path='/addbus' element={<AddBus></AddBus>}></Route>
            <Route element={<ViewBus></ViewBus>} path='/viewbus'></Route>
        </Routes>
    </div>
  )
}

export default AdminHomePage