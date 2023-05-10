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