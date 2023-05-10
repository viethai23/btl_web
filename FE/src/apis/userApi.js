import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080/api";

export function getcustomer(){
    return axios.get("/users")
}
export function deletecustomer(id){
    return axios.delete(`/users/${id}`)
}
export function postcustomer(data){
    return axios.post("/users",{
        username: data.username,
        password: data.password,
        identifier: "client",
        full_name: data.full_name,
        address: data.address,
        email: data.email,
        phone_number: data.phone_number
    })
}
export function putcustomer(data,id){
    return axios.put(`/users/${id}`,{
        username: data.username,
        password: data.password,
        full_name: data.full_name,
        address: data.address,
        email: data.email,
        phone_number: data.phone_number
    })
}