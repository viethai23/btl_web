import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080/api";

// Lấy phòng khách sạn
export function getroom() {
  return axios.get(`/rooms`);
}

// Lấy phòng khách sạn theo id phong
export function getroombyid(id) {
  return axios.get(`/rooms/${id}`);
}

// Lấy phòng khách sạn theo id khach san
export function getroombyhotel(id) {
  return axios.get(`/rooms`,{
    params:{
      hotelId: id
    }
  });
}

// Thêm phòng khach san
export function postroom(data) {
  return axios.post("/rooms", {
    room_name: data.room_name,
    room_type: data.room_type,
    room_size: data.room_size,
    max_occupancy: data.max_occupancy,
    price: data.price,
    hotel_id: data.hotel_id,
  });
}

// Sửa phòng khách sạn
export function putroom(data,id) {
  console.log(data);
  return axios.put(`/rooms/${id}`, {
    room_name: data.room_name,
    room_type: data.room_type,
    room_size: data.room_size,
    max_occupancy: data.max_occupancy,
    price: data.price,
    hotel_id: data.hotel_id,
  });
}

// Xóa phòng khách sạn
export function deleteroom(id) {
  return axios.delete(`/rooms/${id}`);
}
