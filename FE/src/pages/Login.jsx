import { Button, Form, Input, notification } from "antd";
import { checklogin } from "../apis/loginApi";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const LoginFormContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const LoginHeader = styled.h1`
  margin-bottom: 24px;
  font-weight: bold;
  font-size: 32px;
  line-height: 1.5;
  text-align: center;
`;

const LoginForm = ({ onFinish, openSignup }) => {
  return (
    <LoginFormContainer>
      <LoginHeader>Welcome to My Hotel</LoginHeader>
      <Form
        name="login-form"
        initialValues={{ remember: true }}
        onFinish={onFinish}
      >
        <Form.Item
          label="Username"
          name="username"
          rules={[{ required: true, message: "Please input your username!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[{ required: true, message: "Please input your password!" }]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            Log in
          </Button>
        </Form.Item>
      </Form>
      <div style={{ marginTop: "24px", textAlign: "center" }}>
        <span>Don't have an account?</span>{" "}
        <Button type="link" onClick={openSignup}>
          Sign up now
        </Button>
      </div>
    </LoginFormContainer>
  );
};

const LoginPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
`;

const LoginPageWrapper = styled.div`
  width: 400px;
  padding: 24px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
`;

const LoginPage = ({ setuser }) => {
  const navigate = useNavigate();
  const openSignup = () => {
    navigate("/signup");
  };
  const handleFinish = (values) => {
    checklogin(values)
      .then((response) => {
        setuser(response.data);
        notification["success"]({
          message: "Log in successful",
          placement: "topRight",
        });
        navigate("/hotel");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <LoginPageContainer>
      <LoginPageWrapper>
        <LoginForm onFinish={handleFinish} openSignup={openSignup} />
      </LoginPageWrapper>
    </LoginPageContainer>
  );
};

export default LoginPage;
