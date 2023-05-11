import { Button, Form, Input, InputNumber, Modal, notification } from "antd";
import { useState } from "react";
import { getcustomer, postcustomer, putcustomer } from "../../apis/userApi";

const ModalUser = (props) => {
  const [addModal, setAddModal] = useState(false);

  const onCancelModal = () => {
    setAddModal(false);
    props.setEditModal(null);
  };

  const onFinishModal = (customer) => {
    if (addModal) {
      setAddModal(false);
      postcustomer(customer)
        .then(() => displayData())
        .catch(() => {
          notification["error"]({
            message: "Thêm khách hàng thất bại",
            placement: "topRight",
          });
        });
    }
    if (props.editModal) {
      props.setEditModal(null);
      putcustomer(customer, props.editModal.id) // customer la thong tin cua cong ty nguoi dung muon sua o form ben duoi, props.editModal.id la id cua cong ty muon edit
        .then(() => displayData())
        .catch(() => {
          notification["error"]({
            message: "Sửa khách hàng thất bại",
            placement: "topRight",
          });
        });
    }
  };

  const displayData = () => {
    getcustomer()
      .then((response) => {
        props.setusers(response.data);
        notification["success"]({
          message: addModal
            ? "Thêm khách hàng thành công"
            : "Sửa khách hàng thành công",
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
        Thêm khách hàng
      </Button>
      <Modal
        title={addModal ? "Thêm khách hàng" : "Sửa khách hàng"}
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
            label="Tên khách hàng"
            name="full_name"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập tên khách hàng!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Username"
            name="username"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập username!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Password"
            name="password"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập tên password!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Email"
            name="email"
            rules={[{ required: true, message: "Vui lòng nhập email!" }]}
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
            label="Số điện thoại"
            name="phone_number"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập số điện thoại!",
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
              {addModal ? "Thêm" : "Lưu"}
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModalUser;
