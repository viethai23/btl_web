import { Button, Input, Form, Modal, notification } from "antd";
import {
  getbooking,
  getbookingbyuser,
  putbooking,
} from "../../apis/bookingApi";

const ModalBooking = (props) => {
  const { user } = props;

  const onCancelModal = () => {
    props.setEditModal(null);
  };

  const onFinishModal = (values) => { 
      props.setEditModal(null);
      putbooking(values, props.editModal.id)
        .then(() => displayData())
        .catch(() => {
          notification["error"]({
            message: "Sửa đặt phòng thất bại",
            placement: "topRight",
          });
        });
    }
  

  const displayData = () => {
    if (!user.identifier) {
      getbooking()
        .then((response) => {
          props.setbookings(response.data);
          notification["success"]({
            message: "Sửa đặt phòng thành công",
            placement: "topRight",
          });
          props.setEditModal(null);
        })
        .catch((error) => console.log(error));
    } else {
      getbookingbyuser(user.id)
        .then((response) => {
          props.setbookings(response.data);
          notification["success"]({
            message: "Sửa đặt phòng thành công",
            placement: "topRight",
          });
          props.setEditModal(null);
        })
        .catch((error) => console.log(error));
    }
  };

  return (
    <div>
      <Modal
        title="Sửa đặt phòng"
        visible={props.editModal}
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
            label="Ngày checkin"
            name="checkin_date"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn ngày!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Ngày checkout"
            name="checkout_date"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn ngày!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
            <Button style={{ marginRight: 10 }} onClick={onCancelModal}>
              Close
            </Button>
            <Button type="primary" htmlType="submit">
              Lưu
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModalBooking;
