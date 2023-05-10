import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080/api";

//Lấy client
export function getuser(){
  
}

//lay khach san theo id
export function gethotelbyid(id){
  return axios.get(`/hotels/${id}`)
}

// Lấy danh sách các khách sạn
export function gethotel() {
  return axios.get("/hotels");
}

// Thêm khách sạn
export function posthotel(data) {
  console.log(data);
  return axios.post("/hotels", {
    name: data.name,
    amenities: data.amenities,
    address: data.address,
    rating: data.rating,
    opening_time: data.opening_time,
    closing_time: data.closing_time
  });
}

// Sửa khách sạn theo id khách sạn
export function puthotel(data,id) {
  return axios.put(`/hotels/${id}`, {
    name: data.name,
    amenities: data.amenities,
    address: data.address,
    rating: data.rating,
    opening_time: data.opening_time,
    closing_time: data.closing_time
  });
}

// Xóa công ty theo công ty id
export function deletehotel(id) {
  return axios.delete(`/hotels/${id}`);
}


