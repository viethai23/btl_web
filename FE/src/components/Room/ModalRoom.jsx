import {
  Button,
  Form,
  Input,
  InputNumber,
  Modal,
  notification,
  Select,
} from "antd";
import { useEffect, useState } from "react";
import { getroombyid, postroom, putroom } from "../../apis/roomApi";
import { gethotel, gethotelbyid } from "../../apis/hotelApi";
const { Option } = Select;

const ModalRoom = (props) => {
  const id = props.id;
  const [hotel, sethotel] = useState([]);
  const [addModal, setAddModal] = useState(false);

  useEffect(() => {
    gethotel()
      .then((response) => {
        sethotel(response.data);
        console.log(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const onCancelModal = () => {
    setAddModal(false);
    props.setEditModal(null);
  };

  const onFinishModal = async (rooms) => {
    if (addModal) {
      setAddModal(false);
      postroom(rooms)
        .then(() => displayData())
        .catch(() => {
          notification["error"]({
            message: "Thêm phòng cho khách sạn thất bại",
            placement: "topRight",
          });
        });
    }
    if (props.editModal) {
      props.setEditModal(null);
      putroom(rooms, props.editModal.id) // rooms la thong tin cua phong nguoi dung muon sua o form ben duoi, props.editModal.id la id cua phong muon edit
        .then(() => displayData())
        .catch(() => {
          notification["error"]({
            message: "Sửa phòng cho khách sạn thất bại",
            placement: "topRight",
          });
        });
    }
  };

  const displayData = () => {
    getroombyid(id.id)
      .then((response) => {
        props.setrooms(response.data);
        notification["success"]({
          message: addModal
            ? "Thêm phòng cho khách sạn thành công"
            : "Sửa phòng cho khách sạn thành công",
          placement: "topRight",
        });
        addModal ? setAddModal(false) : props.setEditModal(null);
      })
      .catch((error) => console.log(error));
  };

  return (
    <div>
      <Button
        type="primary"
        style={{ margin: "10px" }}
        onClick={() => {
          setAddModal(true);
        }}
      >
        Thêm phòng cho khách sạn
      </Button>
      <Modal
        title={addModal ? "Thêm phòng" : "Sửa phòng"}
        visible={addModal || props.editModal}
        onCancel={onCancelModal}
        footer={""}
        destroyOnClose={true}
      >
        <Form
          name="nest-messages"
          labelCol={{ span: 5 }}
          wrapperCol={{ span: 16 }}
          onFinish={onFinishModal}
          initialValues={props.editModal}
        >
          <Form.Item
            label="Tên phòng"
            name="room_name"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập tên phòng!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Loại phòng"
            name="room_type"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn loại phòng!",
              },
            ]}
          >
            <Select defaultValue="single" style={{ width: "18vw" }}>
              <Option value="single">Single</Option>
              <Option value="double">Double</Option>
              <Option value="presidential">Presidential Suite</Option>
            </Select>
          </Form.Item>
          <Form.Item
            label="Kích cỡ"
            name="room_size"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn kích cỡ!",
              },
            ]}
          >
            <InputNumber
              min={20}
              max={50}
              defaultValue={20}
              style={{ width: "18vw" }}
            />
          </Form.Item>
          <Form.Item
            label="Số người"
            name="max_occupancy"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn số người!",
              },
            ]}
          >
            <InputNumber
              min={1}
              max={102}
              defaultValue={1}
              style={{ width: "18vw" }}
            />
          </Form.Item>
          <Form.Item
            label="Giá tiền"
            name="price"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập giá phòng!",
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Khách sạn"
            name="hotel_id"
            rules={[{ required: true, message: "Vui lòng chọn khách sạn!" }]}
          >
            <Select defaultValue="Lựa chọn khách sạn" style={{ width: "20vw" }}>
              {hotel.map((province) => (
                <Option key={province.id}>{province.name}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 8, span: 16 }} className="form-btn">
            <Button style={{ marginRight: 10 }} onClick={onCancelModal}>
              Hủy
            </Button>
            <Button type="primary" htmlType="submit" className="btn-submit">
              {addModal ? "Thêm" : "Sửa"}
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModalRoom;
