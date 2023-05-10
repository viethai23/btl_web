import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080/api";
export function getbill(){
    return axios.get("/bills")
}
export function getbillbyid(id){
    return axios.get(`/bills/${id}`)
}
export function getbillbyuser(id){
    return axios.get(`/bills/${id}/bills`)
}
export function deletebill(id){
    return axios.delete(`/bills/${id}`)
}
export function postbill(data,booking_id){
    return axios.post("/bills",{
        payment_method: data.payment_method,
        booking_id: booking_id
    })
}