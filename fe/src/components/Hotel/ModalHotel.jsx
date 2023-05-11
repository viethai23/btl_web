import { Button, Form, Input, InputNumber, Modal, notification } from "antd";
import { useState } from "react";
import { gethotel, posthotel, puthotel } from "../../apis/hotelApi";

const ModalHotel = (props) => {
  const [addModal, setAddModal] = useState(false);

  const onCancelModal = () => {
    setAddModal(false);
    props.setEditModal(null);
  };

  const onFinishModal = (hotel) => {
    if (addModal) {
      setAddModal(false);
      posthotel(hotel)
        .then(() => displayData())
        .catch(() => {
          notification["error"]({
            message: "Thêm khách sạn thất bại",
            placement: "topRight",
          });
        });
    }
    if (props.editModal) {
      props.setEditModal(null);
      puthotel(hotel, props.editModal.id) // hotel la thong tin cua cong ty nguoi dung muon sua o form ben duoi, props.editModal.id la id cua cong ty muon edit
        .then(() => displayData())
        .catch(() => {
          notification["error"]({
            message: "Sửa khách sạn thất bại",
            placement: "topRight",
          });
        });
    }
  };

  const displayData = () => {
    gethotel()
      .then((response) => {
        props.setHotels(response.data);
        notification["success"]({
          message: addModal
            ? "Thêm khách sạn thành công"
            : "Sửa khách sạn thành công",
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
        onClick={() => setAddModal(true)}
      >
        Thêm Khách sạn
      </Button>
      <Modal
        title={addModal ? "Thêm khách sạn" : "Sửa khách sạn"}
        visible={addModal || props.editModal}
        onCancel={onCancelModal} // Ham onCancelModal se duoc goi khi nguoi dung bam nut tat hoac cancel
        destroyOnClose={true}
        footer={null}
      >
        <Form //Khi hoan tat form va submit, tat ca du lieu se duoc goi vao 1 doi tuong va chui vao function onFinishModal(). Moi mot doi tuong se co thuoc tinh duoc dat sau prop 'name' cua form item
          name="nest-messages"
          labelCol={{ span: 6 }}
          wrapperCol={{ span: 16 }}
          onFinish={onFinishModal}
          initialValues={props.editModal}
        >
          <Form.Item
            label="Tên khách sạn"
            name="name"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập tên khách sạn!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Tiên ích"
            name="amenities"
            rules={[
              { required: true, message: "Vui lòng nhập lĩnh vực hoạt động!" },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Địa chỉ"
            name="address"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập địa chỉ!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Giờ mở cửa"
            name="opening_time"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập giờ mở cửa",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Giờ đóng cửa"
            name="closing_time"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập giờ đóng cửa",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Đánh giá"
            name="rating"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập đánh giá !",
              },
            ]}
          >
            <InputNumber
              min={0}
              max={5}
              initialValue={1}
              style={{ width: "3vw" }}
            />
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
            <Button style={{ marginRight: 10 }} onClick={onCancelModal}>
              Close
            </Button>
            <Button type="primary" htmlType="submit">
              {addModal ? "Thêm" : "Lưu"}
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModalHotel;
