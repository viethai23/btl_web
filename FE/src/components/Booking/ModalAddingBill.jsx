import { Button, Form, Input, InputNumber, Modal, notification, Select } from "antd";
import { useState } from "react";
import { getbillbyid, postbill } from "../../apis/billApi";

const { Option } = Select;

const ModalAddingBill = (props) => {
  const onCancelModal = () => {
    props.setaddbill(null);
  };

  const onFinishModal = (value) => {
    if (props.addbill) {
      props.setaddbill(null);
      postbill(value, props.addbill.id) 
        .then((response) => displayData(response))
        .catch(() => {
          notification["error"]({
            message: "Thanh toán thất bại",
            placement: "topRight",
          });
        });
    }
  };

  const displayData = (booking) => {
      getbillbyid(booking.data.id)
        .then((response) => {
          notification["success"]({
            message:"Thanh toán thành công",
            placement: "topRight",
          });
          props.setaddbill(null);
          props.setPaidBookings([...paidBookings, booking.data.id]);
        })
        .catch((error) => console.log(error));
    
  };

  return (
    <div>
      <Modal
        title={"Thanh toán thành công"}
        visible={props.addbill}
        onCancel={onCancelModal} // Ham onCancelModal se duoc goi khi nguoi dung bam nut tat hoac cancel
        destroyOnClose={true}
        footer={null}
      >
        <Form //Khi hoan tat form va submit, tat ca du lieu se duoc goi vao 1 doi tuong va chui vao function onFinishModal(). Moi mot doi tuong se co thuoc tinh duoc dat sau prop 'name' cua form item
          name="nest-messages"
          labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          onFinish={onFinishModal}
          initialValues={props.addbill}
        >
          <Form.Item
            label="Hình thức thanh toán"
            name="payment_method"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn hình thức thanh toán!",
              },
            ]}
          >
            <Select defaultValue="Cash" style={{ width: "18vw" }}>
              <Option value="Debit Card">Debit card</Option>
              <Option value="Cash">Cash</Option>
              <Option value="Credit Card">Credit card</Option>
            </Select>
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
            <Button style={{ marginRight: 10 }} onClick={onCancelModal}>
              Close
            </Button>
            <Button type="primary" htmlType="submit">
              Thanh toán
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModalAddingBill;
