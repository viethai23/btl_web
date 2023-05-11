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
import { Link } from "react-router-dom";
import styled from "styled-components";
import { deletehotel, gethotel } from "../../apis/hotelApi";
import ModalHotel from "./ModalHotel";

const Container = styled.div`
  margin: 20px;
`;

const TitleAndSearch = styled.div`
  display: flex;
  justify-content: space-between;
  width: 86.5vw;
`;

const Content = styled.div``;

const HotelTable = styled.div`
  margin: 10px;
`;

const Action = styled.div`
  width: 7vw;
`;

const Hotel = (props) => {
  const { user } = props;
  const [hotels, setHotels] = useState([]);
  const [editModal, setEditModal] = useState(null);
  const [wantDelete, setWantDelete] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [searchedColumn, setSearchedColumn] = useState("");
  const searchInput = useRef(null);

  useEffect(() => {
    gethotel()
      .then((response) => {
        setHotels(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const handleSearch = (selectedKeys, confirm, dataIndex) => {
    confirm();
    setSearchText(selectedKeys[0]);
    setSearchedColumn(dataIndex);
  };

  const companies = "";
  const handleReset = (clearFilters) => {
    clearFilters();
    setSearchText("");
  };

  const onConfirmDelete = () => {
    deletehotel(wantDelete)
      .then(() => {
        displayhotel();
      })
      .catch(() => {
        notification["error"]({
          message: "Xóa khách sạn thất bại",
          placement: "topRight",
        });
      });
  };

  const displayhotel = () => {
    gethotel()
      .then((response) => {
        setHotels(response.data);
        notification["success"]({
          message: "Xóa khách sạn thành công",
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
      title: "Mã khách sạn",
      dataIndex: "id",
      key: "id",
      width: "8vw",
      ...getColumnSearchProps("id"),
    },
    {
      title: "Tên khách sạn",
      dataIndex: "name",
      key: "name",
      width: "12vw",
      ...getColumnSearchProps("name"),
    },
    {
      title: "Tiện ích",
      dataIndex: "amenities",
      key: "amenities",
    },
    {
      title: "Địa chỉ",
      dataIndex: "address",
      key: "address",
    },
    {
      title: "Giờ mở",
      dataIndex: "opening_time",
      key: "opening_time",
      width: "6vw",
    },
    {
      title: "Giờ đóng",
      dataIndex: "closing_time",
      key: "openingTime",
      width: "6vw",
    },
    {
      title: "Đánh giá",
      dataIndex: "rating",
      key: "rating",
      width: "8vw",
      sorter: (a, b) => a.rating - b.rating,
      sortDirections: ["descend", "ascend"],
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

  const actionMenuAdmin = (record) => (
    <Menu>
      <Menu.Item key="1">
        <a onClick={() => setEditModal(record)}>Sửa</a>
      </Menu.Item>

      <Menu.Item key="2">
        <Popconfirm
          title="Bạn có muốn xóa khách sạn này không?"
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
          <Link to={`/hotel/${record.id}`}>Danh sách phòng</Link>
        </Menu.Item>
    </Menu>
  )
  const actionMenu = (record) => {
    if (user && user.identifier !== null) {
      return actionMenuClient(record);
    } else {
      return actionMenuAdmin(record); 
    }
  }
  return (
    <div style={{ backgroundColor: "#F3F2F2", width: "90vw" }}>
      <Container>
        <TitleAndSearch>
          <div>
            {!user.identifier ? (
              <h1>QUẢN LÝ KHÁCH SẠN</h1>
            ) : (
              <h1>THUÊ PHÒNG KHÁCH SẠN</h1>
            )}
          </div>
        </TitleAndSearch>
        <Content>
          <div>
            <h2>DANH SÁCH KHÁCH SẠN</h2>
          </div>
          {!user.identifier && (
            <ModalHotel
              editModal={editModal}
              setEditModal={setEditModal}
              hotels={hotels}
              setHotels={setHotels}
            />
          )}
          <HotelTable>
            <Table //dataIndex se duoc su dung nhu la ten cua 1 thuoc tinh cua doi tuong nam trong 1 ban ghi tren bang
              dataSource={hotels}
              columns={columns}
              scroll={{ y: 500 }}
              pagination={{
                defaultPageSize: 10,
                showSizeChanger: true,
                pageSizeOptions: ["10", "20", "30"],
              }}
            ></Table>
          </HotelTable>
        </Content>
      </Container>
    </div>
  );
};

export default Hotel;
