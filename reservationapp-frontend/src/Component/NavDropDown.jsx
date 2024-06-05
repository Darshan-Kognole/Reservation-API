import Dropdown from 'react-bootstrap/Dropdown';
import '../Styles/NavBar.css'
function NavDropDown() {
  return (
    <Dropdown className='main'>
      <Dropdown.Toggle variant="success" id="dropdown-basic">
        Account
      </Dropdown.Toggle>

      <Dropdown.Menu id='menu'>
        <Dropdown.Item href="/adminhomepage/addbus">AddBus</Dropdown.Item>
        <Dropdown.Item href="/adminhomepage/viewbus">Bus List</Dropdown.Item>
        <Dropdown.Item href="">Edit Profile</Dropdown.Item>

        <Dropdown.Divider />
      <Dropdown.Item eventKey="4">Logout</Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  );
}

export default NavDropDown;