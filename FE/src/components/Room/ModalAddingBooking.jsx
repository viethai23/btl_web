import {
  Button,
  DatePicker,
  Form,
  InputNumber,
  Modal,
  notification,
} from "antd";
import { useEffect, useState } from "react";
import { getbookingbyid, postbooking } from "../../apis/bookingApi";

const ModalAddingBooking = (props) => {
  const [bill,setbill] = useState(null);
  const [userId, setUserId] = useState(null);
  const [roomName, setRoomName] = useState("");
  const [max, setMax] = useState(2);

  useEffect(() => {
    if (props.editModal) {
      setRoomName(props.editModal.room_name);
      setMax(props.editModal.max_occupancy);
      setUserId(props.userId);
    }
  }, [props.editModal]);

  const onCancelModal = () => {
    props.setEditModal(null);
  };

  const onFinishModal = async (value) => {
    value.checkin_date = value.checkin_date.format("YYYY-MM-DD");
    value.checkout_date = value.checkout_date.format("YYYY-MM-DD");
    console.log(value)
    if (props.editModal) {
      console.log(value, props.editModal.id, userId)
      postbooking(value, props.editModal.id, userId)
        .then((response) => {
          displayData(response.data)
        })
        .catch((e) => {
          notification["error"]({
            message: "Đặt phòng thất bại",
            placement: "topRight",
          });
          return console.log(e)
        });
    }
  };
  const displayData = (value) => {
    getbookingbyid(value.id)
      .then((response) => {
        setbill(response.data);
        notification["success"]({
          message: "Đặt phòng khách sạn thành công",
          placement: "topRight",
        });
        console.log(value.id)
        props.setEditModal(null);
      })
      .catch((error) => console.log(error));
  };

  return (
    <div>
      <Modal
        title={`Đặt phòng ${roomName}`}
        visible={props.editModal}
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
            label="Số người"
            name="num_of_guests"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn số người!",
              },
            ]}
          >
            <InputNumber
              min={1}
              max={max}
              initialValue={1}
              style={{ width: "12vw" }}
            />
          </Form.Item>
          <Form.Item
            label="Chọn ngày check in"
            name="checkin_date"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn ngày check in!",
              },
            ]}
          >
            <DatePicker style={{ width: "12vw" }} />
          </Form.Item>
          <Form.Item
            label="Chọn ngày check out"
            name="checkout_date"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn ngày check out!",
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
              Đặt phòng
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModalAddingBooking;
