import { Menu } from "antd";
import "antd/dist/antd.css";
import { Link } from "react-router-dom";
import { PATH } from "../../constants/path";

const SideBar = ({ user }) => {
  const renderMenuWithoutIdentifer = () => {
    return (
      <>
        <Menu.Item key="2">
          <Link to={PATH.ROOM}>PHÒNG</Link>
        </Menu.Item>
        <Menu.Item key="3">
          <Link to={PATH.CUSTOMER}>KHÁCH HÀNG</Link>
        </Menu.Item>
        <Menu.Item key="4">
          <Link to={PATH.BOOKING}>ĐẶT PHÒNG</Link>
        </Menu.Item>
        <Menu.Item key="5">
          <Link to={PATH.BILL}>HÓA ĐƠN</Link>
        </Menu.Item>
      </>
    );
  };

  const renderMenuWithIdentifer = () => {
    return (
      <>
        <Menu.Item key="2">
          <Link to={PATH.BOOKING}>ĐẶT PHÒNG</Link>
        </Menu.Item>
        <Menu.Item key="3">
          <Link to={PATH.BILL}>HÓA ĐƠN</Link>
        </Menu.Item>
      </>
    );
  };

  const renderMenu = () => {
    if (user && user.identifier !== null) {
      return renderMenuWithIdentifer();
    } else {
      return renderMenuWithoutIdentifer();
    }
  };

  return (
    <div>
      <Menu
        mode="inline"
        defaultSelectedKeys={["1"]}
        style={{ height: "95vh", borderRight: 0, width: "10vw" }}
      >
        <Menu.Item key="1">
          <Link to={PATH.HOTEL}>KHÁCH SẠN</Link>
        </Menu.Item>
        {renderMenu()}
      </Menu>
    </div>
  );
};

export default SideBar;
