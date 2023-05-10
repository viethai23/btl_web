import { DownOutlined, SearchOutlined } from "@ant-design/icons";
import { useEffect, useRef, useState } from "react";

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
import Highlighter from "react-highlight-words";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import {
  deleteroom,
  getroom,
  getroombyhotel,
  getroombyid,
} from "../../apis/roomApi";
import ModalRoom from "./ModalRoom";
import ModalAddingBooking from "./ModalAddingBooking";

const Container = styled.div`
  margin: 20px;
`;

const TitleAndSearch = styled.div`
  display: flex;
  justify-content: space-between;
  width: 88.5vw;
`;

const Content = styled.div``;

const RoomTable = styled.div`
  margin: 10px;
`;

const Action = styled.div`
  width: 2vw;
`;
const Room = (props) => {
  const { user } = props;
  const [rooms, setrooms] = useState([]);
  const [editModal, setEditModal] = useState(null);
  const [wantDelete, setWantDelete] = useState(null);
  const [id, setId] = useState(null);
  const [userId, setUserId] = useState(null);
  let idHotel = useParams();
  const [searchText, setSearchText] = useState("");
  const [searchedColumn, setSearchedColumn] = useState("");
  const searchInput = useRef(null);

  useEffect(() => {
    setId(idHotel.id);
    setUserId(user.id);
    console.log("setting id to",idHotel.id)
  },[id]);
  useEffect(() => {
    if (!user.identifier) {
      getroom()
        .then((response) => {
          setrooms(response.data);
        })
        .catch((error) => console.log(error));
      console.log("not yet, "+id);
    } else {
      getroombyhotel(id)
        .then((response) => {
          setrooms(response.data);
        })
        .catch((error) => console.log(error));
      console.log("by hotel works with "+id)
    }
  },[rooms,id]); //thêm [room] sẽ tạo vòng lặp vô hạn nhưng chính xác, chỉ dùng trong tg ngắn

  const onConfirmDelete = () => {
    deleteroom(wantDelete)
      .then(() => displayroom())
      .catch(() => {
        notification["error"]({
          message: "Xóa phòng thất bại",
          placement: "topRight",
        });
      });
  };

  const displayroom = () => {
    getroombyid(id)
      .then((response) => {
        setrooms(response.data);
        notification["success"]({
          message: "Xóa phòng thành công",
          placement: "topRight",
        });
      })
      .catch((error) => console.log(error));
  };
  const actionMenuAdmin = (record) => (
    <Menu>
      <Menu.Item key="1">
        <a onClick={() => setEditModal(record)}>Sửa</a>
      </Menu.Item>
      <Menu.Item key="2">
        <Popconfirm
          title="Bạn có muốn xóa phòng này không?"
          onConfirm={onConfirmDelete}
          okText="Có"
          cancelText="Không"
        >
          <a onClick={() => setWantDelete(record.id)}>Xóa</a>
        </Popconfirm>
      </Menu.Item>
    </Menu>
  );
  const actionMenuClient = (record) => (
    <Menu>
      <Menu.Item key="1">
        <a onClick={() => setEditModal(record)}>Đặt phòng</a>
      </Menu.Item>
    </Menu>
  );
  const actionMenu = (record) => {
    if (user && user.identifier !== null) {
      return actionMenuClient(record);
    } else {
      return actionMenuAdmin(record);
    }
  };

  const handleSearch = (selectedKeys, confirm, dataIndex) => {
    confirm();
    setSearchText(selectedKeys[0]);
    setSearchedColumn(dataIndex);
  };

  const handleReset = (clearFilters) => {
    clearFilters();
    setSearchText("");
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
      title: "Mã phòng",
      dataIndex: "id",
      key: "id",
      ...getColumnSearchProps("id"),
      sorter: (a, b) => a.id - b.id,
      sortDirections: ["descend", "ascend"],
    },
    {
      title: "Tên phòng",
      dataIndex: "room_name",
      key: "room_name",
      ...getColumnSearchProps("room_name"),
    },
    {
      title: "Loại phòng",
      dataIndex: "room_type",
      key: "room_type",
    },
    {
      title: "Kích thước phòng",
      dataIndex: "room_size",
      key: "room_size",
      sorter: (a, b) => a.room_size - b.room_size,
      sortDirections: ["descend", "ascend"],
    },
    {
      title: "Số người",
      dataIndex: "max_occupancy",
      key: "max_occupancy",
      sorter: (a, b) => a.maxOccupancy - b.max_occupancy,
      sortDirections: ["descend", "ascend"],
    },
    {
      title: "Đơn giá",
      dataIndex: "price",
      key: "price",
      sorter: (a, b) => a.price - b.price,
      sortDirections: ["descend", "ascend"],
    },

    {
      title: "Mã khách sạn",
      dataIndex: ["hotel", "id"],
      key: "hotel_id",
      ...getColumnSearchProps(["hotel", "id"]),
      sorter: (a, b) => a.hotel.id - b.hotel.id,
      sortDirections: ["descend", "ascend"],
    },

    {
      title: "Tùy chọn",
      key: "action",
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

  const roomClient = () => (
    <div style={{ backgroundColor: "#F3F2F2", width: "89.8vw" }}>
      <Container>
        <TitleAndSearch>
          <div>
            <h1>CHỌN PHÒNG KHÁCH SẠN</h1>
          </div>
        </TitleAndSearch>
        <Content>
          <div>
            <h2>Danh sách phòng</h2>
          </div>
          <ModalAddingBooking
            userId={userId}
            editModal={editModal}
            setEditModal={setEditModal}
          />
          <RoomTable>
            <Table //dataIndex se duoc su dung nhu la ten cua 1 thuoc tinh cua doi tuong nam trong 1 ban ghi tren bang
              dataSource={rooms}
              columns={columns}
              scroll={{ y: 500 }}
              pagination={{
                defaultPageSize: 10,
                showSizeChanger: true,
                pageSizeOptions: ["10", "20", "30"],
              }}
            ></Table>
          </RoomTable>
        </Content>
      </Container>
    </div>
  );

  const roomAdmin = () => (
    <div style={{ backgroundColor: "#F3F2F2", width: "89.8vw" }}>
      <Container>
        <TitleAndSearch>
          <div>
            <h1>QUẢN LÝ CÁC PHÒNG CỦA KHÁCH SẠN</h1>
          </div>
        </TitleAndSearch>
        <Content>
          <div>
            <h2>Danh sách phòng</h2>
          </div>
          <ModalRoom
            id={idHotel}
            editModal={editModal}
            setEditModal={setEditModal}
            rooms={rooms}
            setrooms={setrooms}
          />
          <RoomTable>
            <Table //dataIndex se duoc su dung nhu la ten cua 1 thuoc tinh cua doi tuong nam trong 1 ban ghi tren bang
              dataSource={rooms}
              columns={columns}
              scroll={{ y: 500 }}
              pagination={{
                defaultPageSize: 10,
                showSizeChanger: true,
                pageSizeOptions: ["10", "20", "30"],
              }}
            ></Table>
          </RoomTable>
        </Content>
      </Container>
    </div>
  );

  if (user && user.identifier !== null) {
    return roomClient();
  }else{
    return roomAdmin();
  }
};

export default Room;
