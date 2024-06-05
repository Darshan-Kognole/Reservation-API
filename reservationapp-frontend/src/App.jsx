import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import LandingPage from './Component/LandingPage';
import AdminLogin from './Component/AdminLogin';
import UserLogin from './Component/UserLogin';
import AdminSignUp from './Component/AdminSignUp';
import UserSignUp from './Component/UserSignUp';
import AdminHomePage from './Component/AdminHomePage';
import UserHomePage from './Component/UserHomePage';
import PageNotFound from './Component/PageNotFound';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
        <Route element={<PageNotFound/>} path='/*'></Route>
        <Route element={<LandingPage/>} path='/'/>
        <Route element={<AdminLogin/>} path='/adminlogin'/>
        <Route element={<UserLogin/>} path='/userlogin'/>
        <Route element={<AdminSignUp/>} path='/adminsignup'/>
        <Route element={<UserSignUp/>} path='/usersignup'/>
        <Route element={<AdminHomePage/>} path='/adminhomepage/*'/>
        <Route element={<UserHomePage/>} path='/userhomepage'></Route>
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
