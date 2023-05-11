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
import { deletebill, getbill, getbillbyuser } from "../../apis/billApi";
import { useParams } from "react-router-dom";

const Container = styled.div`
  margin: 20px;
`;

const TitleAndSearch = styled.div`
  display: flex;
  justify-content: space-between;
  width: 86.5vw;
`;

const Content = styled.div``;

const BillTable = styled.div`
  margin: 10px;
`;

const Action = styled.div`
  width: 7vw;
`;

const Bill = (props) => {
  const { user } = props;
  const [bills, setbills] = useState([]);
  const [editModal, setEditModal] = useState(null);
  const [wantDelete, setWantDelete] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [searchedColumn, setSearchedColumn] = useState("");
  const searchInput = useRef(null);

  useEffect(() => {
    if (!user.identifier) {
      getbill()
        .then((response) => {
          setbills(response.data);
        })
        .catch((error) => console.log(error));
    } else {
      getbillbyuser(user.id)
        .then((response) => {
          setbills(response.data);
        })
        .catch((error) => console.log(error));
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
    deletebill(wantDelete)
      .then(() => {
        displaydata();
      })
      .catch(() => {
        notification["error"]({
          message: "Xóa hoa don thất bại",
          placement: "topRight",
        });
      });
  };

  const displaydata = () => {
    getbill()
      .then((response) => {
        setbills(response.data);
        notification["success"]({
          message: "Xóa hoa don thành công",
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
      title: "Mã hóa đơn",
      dataIndex: "id",
      key: "id",
      ...getColumnSearchProps("id"),
    },
    {
      title: "Ngày thanh toán",
      dataIndex: "payment_date",
      key: "payment_date",
      ...getColumnSearchProps("payment_date"),
    },
    {
      title: "Hình thức thanh toán",
      dataIndex: "payment_method",
      key: "payment_method",
    },
    {
      title: "Giá phòng",
      dataIndex: "amount_total",
      key: "amount_total",
      sorter: (a, b) => a.amount_total - b.amount_total,
      sortDirections: ["descend", "ascend"],
    },
    {
      title: "Phòng",
      dataIndex: ["booking","room","room_name"],
      key: "room_name",
      ...getColumnSearchProps("id"),
    },
    {
      title: "Khách sạn",
      dataIndex: ["booking","room","hotel","name"],
      key: "name",
      ...getColumnSearchProps("id"),
    },
    {
      title: "Tên người dùng",
      dataIndex: ["booking","user", "full_name"],
      key: "full_name"
    }
  ];

  return (
    <div style={{ backgroundColor: "#F3F2F2", width: "90vw" }}>
      <Container>
        <TitleAndSearch>
          <div>
            {user.identifier ? (
              <h1>QUẢN LÝ KHÁCH SẠN</h1>
            ) : (
              <h1>THUÊ PHÒNG KHÁCH SẠN</h1>
            )}
          </div>
        </TitleAndSearch>
        <Content>
          <div>
            <h2>DANH SÁCH HÓA ĐƠN</h2>
          </div>
          <BillTable>
            <Table //dataIndex se duoc su dung nhu la ten cua 1 thuoc tinh cua doi tuong nam trong 1 ban ghi tren bang
              dataSource={bills}
              columns={columns}
              scroll={{ y: 500 }}
              pagination={{
                defaultPageSize: 10,
                showSizeChanger: true,
                pageSizeOptions: ["10", "20", "30"],
              }}
            ></Table>
          </BillTable>
        </Content>
      </Container>
    </div>
  );
};

export default Bill;
