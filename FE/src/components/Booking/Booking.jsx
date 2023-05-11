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
import {
  deletebooking,
  getbooking,
  getbookingbyuser,
} from "../../apis/bookingApi";
import ModalAddingBill from "./ModalAddingBill";

const Container = styled.div`
  margin: 20px;
`;

const TitleAndSearch = styled.div`
  display: flex;
  justify-content: space-between;
  width: 86.5vw;
`;

const Content = styled.div``;

const BookingTable = styled.div`
  margin: 10px;
`;

const Action = styled.div`
  width: 7vw;
`;

const Booking = (props) => {
  const { user } = props;
  const [bookings, setbookings] = useState([]);
  const [addbill, setaddbill] = useState(null);
  const [wantDelete, setWantDelete] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [searchedColumn, setSearchedColumn] = useState("");
  const searchInput = useRef(null);

  useEffect(() => {
    if (!user.identifier) {
      getbooking()
        .then((reponse) => {
          setbookings(reponse.data);
        })
        .catch((error) => console.log(error));
    } else {
      getbookingbyuser(user.id)
        .then((response) => {
          setbookings(response.data);
        })
        .catch((e) => console.log(e));
    }
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
    deletebooking(wantDelete)
      .then(() => {
        displaydata();
      })
      .catch(() => {
        notification["error"]({
          message: "Xóa đặt phòng thất bại",
          placement: "topRight",
        });
      });
  };

  const displaydata = () => {
    getbookingbyuser(user.id)
      .then((response) => {
        setbookings(response.data);
        notification["success"]({
          message: "Xóa đặt phòng thành công",
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
      title: "Mã đặt phòng",
      dataIndex: "id",
      key: "id",
      width: "8vw",
      ...getColumnSearchProps("id"),
    },
    {
      title: "Ngày đặt",
      dataIndex: "booking_date",
      key: "booking_date",
      width: "12vw",
      ...getColumnSearchProps("booking_date"),
    },
    {
      title: "Ngày check in",
      dataIndex: "checkin_date",
      key: "checkin_date",
    },
    {
      title: "Ngày check out",
      dataIndex: "checkout_date",
      key: "checkout_date",
    },
    {
      title: "Số người",
      dataIndex: "num_of_guests",
      key: "num_of_guests",
      width: "6vw",
    },
    {
      title: "Phòng",
      dataIndex: ["room", "room_name"],
      key: "room_name",
      width: "6vw",
    },
    {
      title: "Khách sạn",
      dataIndex: ["room", "hotel", "name"],
      key: "hotelName",
      width: "8vw",
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

  const actionMenuClient = (record) => (
    <Menu>
      {/*<Menu.Item key="1">
        <a onClick={() => setEditModal(record)}>Sửa</a>
  </Menu.Item>*/}
      <Menu.Item key="1">
        <Popconfirm
          title="Bạn có muốn xóa đặt phòng này không?"
          onConfirm={onConfirmDelete}
          okText="Có"
          cancelText="Không"
        >
          <a onClick={() => setWantDelete(record.id)}>Xóa</a>
        </Popconfirm>
      </Menu.Item>
      <Menu.Item key="2">
        <a onClick={() => setaddbill(record)}>Thanh toán</a>
      </Menu.Item>
    </Menu>
  );
  const actionMenuAdmin = (record) => (
    <Menu>
      <Menu.Item key="1">
        <Popconfirm
          title="Bạn có muốn xóa đặt phòng này không?"
          onConfirm={onConfirmDelete}
          okText="Có"
          cancelText="Không"
        >
          <a onClick={() => setWantDelete(record.id)}>Xóa</a>
        </Popconfirm>
      </Menu.Item>
    </Menu>
  );
  const actionMenu = (record) => {
    if (user.identifier) {
      return actionMenuClient(record);
    } else return actionMenuAdmin(record);
  };
  return (
    <div style={{ backgroundColor: "#F3F2F2", width: "90vw" }}>
      <Container>
        <TitleAndSearch>
          <div>
            {!user.identifier ? (
              <h1>QUẢN LÝ ĐẶT PHÒNG</h1>
            ) : (
              <h1>THUÊ PHÒNG KHÁCH SẠN</h1>
            )}
          </div>
        </TitleAndSearch>
        <Content>
          <div>
            <h2>DANH SÁCH ĐẶT PHÒNG</h2>
          </div>
          <divThanhToan />
          {/*<ModalBooking
            editModal={editModal}
            setEditModal={setEditModal}
            setbookings={setbookings}
            user={user}
          />*/}
          {user.identifier && (
            <ModalAddingBill
              addbill={addbill}
              setaddbill={setaddbill}
            />
          )}
          <BookingTable>
            <Table //dataIndex se duoc su dung nhu la ten cua 1 thuoc tinh cua doi tuong nam trong 1 ban ghi tren bang
              dataSource={bookings}
              columns={columns}
              scroll={{ y: 500 }}
              pagination={{
                defaultPageSize: 10,
                showSizeChanger: true,
                pageSizeOptions: ["10", "20", "30"],
              }}
            ></Table>
          </BookingTable>
        </Content>
      </Container>
    </div>
  );
};

export default Booking;
