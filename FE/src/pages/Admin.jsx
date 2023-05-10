import 'antd/dist/antd.css';
import { Route} from "react-router-dom";
import Header from '../components/Header/Header'
import { PATH } from "../constants/path";
import SideBar from '../components/SideBar/SideBar'
import styled from "styled-components";
import Hotel from '../components/Hotel/Hotel';
import Room from '../components/Room/Room'
import Customer from '../components/Customer/Customer'

const Component = styled.div`
  display: flex;
`;

function Admin() {
  return (
    <Component>
      <Header />
      <Component>
        <SideBar width={200} style={{ background: "#fff" }} />
          <Route path={PATH.HOTEL} element={<Hotel />} />
          <Route path={PATH.ROOM} element={<Room />} />
          <Route path={PATH.CUSTOMER} element={<Customer />} />
      </Component>
    </Component>
  );
}

export default Admin;
