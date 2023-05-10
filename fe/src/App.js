import "antd/dist/antd.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header/Header";
import { PATH } from "./constants/path";
import SideBar from "./components/SideBar/SideBar";
import Hotel from "./components/Hotel/Hotel";
import styled from "styled-components";
import Room from "./components/Room/Room";
import Customer from "./components/User/User";
import Bill from "./components/Bill/Bill"
import { useState } from "react";
import LoginPage from "./pages/Login";
import Booking from "./components/Booking/Booking";

const Component = styled.div`
  display: flex;
`;

function App() {
  const [user, setuser] = useState(null);
  const Applogin = () => (
    <BrowserRouter>
        <Routes>
          <Route path={PATH.LOGIN} element={<LoginPage setuser={setuser} />} />
        </Routes>
    </BrowserRouter>
  );
  const Appusing = () => (
    <BrowserRouter>
      <Header setuser={setuser} user={user} />
      <Component>
        <SideBar user={user} width={200} style={{ background: "#fff" }} />
        <Routes>
          <Route path={PATH.ROOM_HOTEL} element={<Room user={user} />} />
          <Route path={PATH.LOGIN} element={<LoginPage setuser={setuser} />} />
          <Route path={PATH.HOTEL} element={<Hotel user={user} />} />
          <Route path={PATH.ROOM} element={<Room user={user} />} />
          <Route path={PATH.CUSTOMER} element={<Customer user={user} />} />
          <Route path={PATH.BILL} element={<Bill user={user}/>} />
          <Route path={PATH.BILL_USER} element={<Bill user={user}/>} />
          <Route path={PATH.BOOKING} element={<Booking user={user}/>}/>
        </Routes>
      </Component>
    </BrowserRouter>
  );
  if (user !== null) {
    return Appusing(user, setuser);
  } else return Applogin(user, setuser);
}

export default App;
