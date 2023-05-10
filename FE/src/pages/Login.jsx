import { Button, Form, Input, notification } from "antd";
import { checklogin } from "../apis/loginApi";
import "./login.css";
import { useNavigate } from "react-router-dom";

const LoginForm = ({ onFinish }) => {
  return (
    <Form
      className="login-form"
      name="login-form"
      initialValues={{ remember: true }}
      onFinish={onFinish}
    >
      <h1 className="login-form-title">Hotel reservation system</h1>
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
        <Button
          className="login-form-button"
          type="primary"
          htmlType="submit"
        >
          Log in
        </Button>
      </Form.Item>

      <Form.Item>
        <Button
          className="login-form-button"
          type="primary"
          htmlType="submit"
        >
          Sign up for free
        </Button>
      </Form.Item>
    </Form>
  );
};

const SignUpForm = ({ onFinish }) => {
  return (
    <Form>
      <Form.Item>
        <Button
          className="login-form-button"
          type="primary"
          htmlType="submit"
        >
          Sign up for free
        </Button>
      </Form.Item>
    </Form>
  );
};

const LoginPage = ({ setuser }) => {
  const navigate = useNavigate();
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
  const handleSignUp = (values) => {
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
    <div
      className="login-container"
    >
      <LoginForm onFinish={handleFinish} />
      {/* <SignUpForm onFinish={handleSignUp} /> */}
    </div>
  );
};

export default LoginPage;
