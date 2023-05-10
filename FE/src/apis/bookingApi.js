import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080/api";

export function getbookingbyid(id){
    return axios.get(`/bookings/${id}`)
}

export function putbooking(id){
    return axios.put(`/bookings/${id}`)
}

export function getbooking(){
    return axios.get(`/bookings`)
}

export function getbookingbyuser(id){
    return axios.get(`/bookings/user/${id}`)
}

export function deletebooking(id){
    return axios.delete(`/bookings/${id}`)
}

export function postbooking(data,roomid,userid){
    return axios.post("/bookings",{
        checkin_date: data.checkin_date,
        checkout_date: data.checkout_date,
        num_of_guests: data.num_of_guests,
        user_id: userid,
        room_id: roomid
    })
}