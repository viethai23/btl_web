import { Button, Form, Input, notification } from "antd";
import { postuser } from "../apis/loginApi";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const SignupPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
`;

const SignupFormContainer = styled.div`
  width: 400px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  padding: 24px;
`;

const SignupHeader = styled.h1`
  margin-bottom: 24px;
  font-weight: bold;
  font-size: 32px;
  line-height: 1.5;
  text-align: center;
`;

const SignupForm = ({ onFinish }) => {
  return (
    <SignupFormContainer>
      <SignupHeader>Sign up to start Booking now!</SignupHeader>
      <Form
        name="nest-messages"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        onFinish={onFinish}
      >
        <Form.Item
          label="Full Name"
          name="full_name"
          rules={[
            {
              required: true,
              message: "Please enter your full name",
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
              message: "Please enter your username",
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
              message: "Please enter your password",
            },
          ]}
        >
          <Input.Password />
        </Form.Item>
        <Form.Item
          label="Email"
          name="email"
          rules={[
            {
              type: "email",
              required: true,
              message: "Please enter a valid email address",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Address"
          name="address"
          rules={[
            {
              required: true,
              message: "Please enter your address",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Phone Number"
          name="phone_number"
          rules={[
            {
              required: true,
              message: "Please enter your phone number",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
          <Button type="primary" htmlType="submit">
            Sign up
          </Button>
        </Form.Item>
      </Form>
    </SignupFormContainer>
  );
};

const Signup = ({ setuser }) => {
  const navigate = useNavigate();
  const handleFinish = (values) => {
    postuser(values)
      .then((response) => {
        setuser(response.data);
        notification["success"]({
          message: "Sign up successful",
          placement: "topRight",
        });
        navigate("/hotel");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <SignupPageContainer>
      <SignupForm onFinish={handleFinish} />
    </SignupPageContainer>
  );
};

export default Signup;