import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080/api";

export function checklogin(data){
    return axios.get("/users/login",{
        params:{
            username: data.username,
            password: data.password
        }
    });
}
export function postuser(data){
    return axios.post("/users",{
        username: data.username,
        password: data.password,
        identifier: "client",
        full_name: data.full_name,
        address: data.address,
        phone_number: data.phone_number,
        email: data.email
    });
}