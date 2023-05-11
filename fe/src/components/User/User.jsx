import { DownOutlined, SearchOutlined } from "@ant-design/icons";
import {
  Button,
  Dropdown,
  Input,
  Menu,
  notification,
  Popconfirm,
  Space,
  Table,
} from "antd";
import { useEffect, useRef, useState } from "react";
import Highlighter from "react-highlight-words";
import styled from "styled-components";
import { deletecustomer, getcustomer } from "../../apis/userApi";
import ModalCustomer from "./ModalUser";
import ModalUser from "./ModalUser";

const Container = styled.div`
  margin: 20px;
`;

const TitleAndSearch = styled.div`
  display: flex;
  justify-content: space-between;
  width: 86.5vw;
`;

const Content = styled.div``;

const UserTable = styled.div`
  margin: 10px;
`;

const Action = styled.div`
  width: 7vw;
`;

const Customer = (props) => {
  const { user } = props;
  const [users, setusers] = useState([]);
  const [editModal, setEditModal] = useState(null);
  const [wantDelete, setWantDelete] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [searchedColumn, setSearchedColumn] = useState("");
  const searchInput = useRef(null);

  useEffect(() => {
    getcustomer()
      .then((response) => {
        setusers(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const handleSearch = (selectedKeys, confirm, dataIndex) => {
    confirm();
    setSearchText(selectedKeys[0]);
    setSearchedColumn(dataIndex);
  };

  const handleReset = (clearFilters) => {
    clearFilters();
    setSearchText("");
  };

  const onConfirmDelete = () => {
    deletecustomer(wantDelete)
      .then(() => {
        displayhotel();
      })
      .catch(() => {
        notification["error"]({
          message: "Xóa khách hàng thất bại",
          placement: "topRight",
        });
      });
  };

  const displayhotel = () => {
    getcustomer()
      .then((response) => {
        setusers(response.data);
        notification["success"]({
          message: "Xóa khách hàng thành công",
          placement: "topRight",
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const getColumnSearchProps = (dataIndex) => ({
    filterDropdown: ({
      setSelectedKeys,
      selectedKeys,
      confirm,
      clearFilters,
    }) => (
      <div
        style={{
          padding: 8,
        }}
      >
        <Input
          ref={searchInput}
          placeholder={`Search ${dataIndex}`}
          value={selectedKeys[0]}
          onChange={(e) =>
            setSelectedKeys(e.target.value ? [e.target.value] : [])
          }
          onPressEnter={() => handleSearch(selectedKeys, confirm, dataIndex)}
          style={{
            marginBottom: 8,
            display: "block",
          }}
        />
        <Space>
          <Button
            type="primary"
            onClick={() => handleSearch(selectedKeys, confirm, dataIndex)}
            icon={<SearchOutlined />}
            size="small"
            style={{
              width: 90,
            }}
          >
            Search
          </Button>
          <Button
            onClick={() => clearFilters && handleReset(clearFilters)}
            size="small"
            style={{
              width: 90,
            }}
          >
            Reset
          </Button>
          <Button
            type="link"
            size="small"
            onClick={() => {
              confirm({
                closeDropdown: false,
              });
              setSearchText(selectedKeys[0]);
              setSearchedColumn(dataIndex);
            }}
          >
            Filter
          </Button>
        </Space>
      </div>
    ),
    filterIcon: (filtered) => (
      <SearchOutlined
        style={{
          color: filtered ? "#1890ff" : undefined,
        }}
      />
    ),
    onFilter: (value, record) =>
      record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => searchInput.current?.select(), 100);
      }
    },
    render: (text) =>
      searchedColumn === dataIndex ? (
        <Highlighter
          highlightStyle={{
            backgroundColor: "#ffc069",
            padding: 0,
          }}
          searchWords={[searchText]}
          autoEscape
          textToHighlight={text ? text.toString() : ""}
        />
      ) : (
        text
      ),
  });
  const columns = [
    {
      title: "Mã",
      dataIndex: "id",
      key: "id",
      ...getColumnSearchProps("id"),
    },
    {
      title: "Identifier",
      dataIndex: "identifier",
      key: "identifier",
    },
    {
      title: "Username",
      dataIndex: "username",
      key: "username",
      ...getColumnSearchProps("username"),
    },
    {
      title: "Password",
      dataIndex: "password",
      key: "password",
    },
    {
      title: "Tên khách hàng",
      dataIndex: "full_name",
      key: "full_name",
      ...getColumnSearchProps("full_name"),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      ...getColumnSearchProps("email"),
    },
    {
      title: "Địa chỉ",
      dataIndex: "address",
      key: "address",
      ...getColumnSearchProps("address"),
    },
    {
      title: "Số điện thoại",
      dataIndex: "phone_number",
      key: "phone_number",
      ...getColumnSearchProps("phone_number"),
    },
    {
      title: "Tùy chọn",
      key: "action",
      width: "10vw",
      render: (record) => (
        <Action>
          <Dropdown overlay={actionMenu(record)}>
            <Button>
              <Space>
                Tùy chọn
                <DownOutlined />
              </Space>
            </Button>
          </Dropdown>
        </Action>
      ),
    },
  ];

  const actionMenu = (record) => {
    return (
      <Menu>
        <Menu.Item key="1">
          <a onClick={() => setEditModal(record)}>Sửa</a>
        </Menu.Item>
        <Menu.Item key="2">
          <Popconfirm
            title="Bạn có muốn xóa khách hàng này không?"
            onConfirm={onConfirmDelete}
            okText="Có"
            cancelText="Không"
          >
            <a onClick={() => setWantDelete(record.id)}>Xóa</a>
          </Popconfirm>
        </Menu.Item>
      </Menu>
    );
  };
  return (
    <div style={{ backgroundColor: "#F3F2F2", width: "90vw" }}>
      <Container>
        <TitleAndSearch>
          <div>
            <h1>QUẢN LÝ KHÁCH SẠN</h1>
          </div>
        </TitleAndSearch>
        <Content>
          <div>
            <h2>DANH SÁCH KHÁCH HÀNG</h2>
          </div>
          <ModalUser
            editModal={editModal}
            setEditModal={setEditModal}
            users={users}
            setusers={setusers}
          />
          <UserTable>
            <Table //dataIndex se duoc su dung nhu la ten cua 1 thuoc tinh cua doi tuong nam trong 1 ban ghi tren bang
              dataSource={users}
              columns={columns}
              scroll={{ y: 500 }}
              pagination={{
                defaultPageSize: 10,
                showSizeChanger: true,
                pageSizeOptions: ["10", "20", "30"],
              }}
            ></Table>
          </UserTable>
        </Content>
      </Container>
    </div>
  );
};

export default Customer;
