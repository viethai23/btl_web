import {
  Button,
  DatePicker,
  Form,
  Modal,
  Select,
  notification,
} from "antd";
import { useEffect, useState } from "react";
import { getbillbydate } from "../../apis/billApi";
import { getcustomer } from "../../apis/userApi";

const { Option } = Select

const ModalBill = (props) => {
  const [user, setuser] = useState([]);
  const { bills, setbills, settotal } = props;
  const [filterModal, setFilterModal] = useState(false);

  useEffect(() => {
    getcustomer()
      .then((response) => {
        setuser(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const onCancelModal = () => {
    setFilterModal(false)
  };

  const onFinishModal = async (value) => {
    value.dayStart = value.dayStart.format("YYYY-MM-DD");
    value.dayEnd = value.dayEnd.format("YYYY-MM-DD");
    console.log(value)
    if (filterModal) {
      setFilterModal(false)
      getbillbydate(value)
        .then((response) => {
          setbills(response.data.bills)
          settotal(response.data.totalAmount)
          notification["success"]({
            message: "Lọc hóa đơn thành công",
            placement: "topRight",
          });
          setFilterModal(false)
        })
        .catch((e) => {
          notification["error"]({
            message: "Lọc hóa đơn thất bại",
            placement: "topRight",
          });
          return console.log(e)
        });
    }
  };

  return (
    <div>
      <Button
        type="primary"
        style={{ margin: "10px" }}
        onClick={() => setFilterModal(true)}
      >
        Lọc hóa đơn
      </Button>
      <Modal
        title={`Lọc hóa đơn`}
        visible={filterModal}
        onCancel={onCancelModal}
        footer={""}
        destroyOnClose={true}
      >
        <Form
          name="nest-messages"
          labelCol={{ span: 9 }}
          wrapperCol={{ span: 16 }}
          onFinish={onFinishModal}
        >
          <Form.Item
            label="Tên người dùng"
            name="userId"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn người dùng cần lọc!",
              },
            ]}
          >
            <Select defaultValue="Lọc theo người dùng" >
              {user.map((province) => (
                <Option key={province.id}>{province.full_name}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            label="Chọn ngày bất đầu"
            name="dayStart"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn ngày!",
              },
            ]}
          >
            <DatePicker style={{ width: "12vw" }} />
          </Form.Item>
          <Form.Item
            label="Chọn ngày kết thúc"
            name="dayEnd"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn ngày!",
              },
            ]}
          >
            <DatePicker style={{ width: "12vw" }} />
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 8, span: 16 }} className="form-btn">
            <Button style={{ marginRight: 10 }} onClick={onCancelModal}>
              Hủy
            </Button>
            <Button type="primary" htmlType="submit" className="btn-submit">
              Lọc
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModalBill;
