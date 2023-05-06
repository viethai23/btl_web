package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.Entity.Room;

import java.util.List;
public interface RoomService {
    public List<Room> getAllRooms();
    public List<Room> getRoomsByType(String type);
    public List<Room> getRoomsByPriceLessThanEqual(Float maxPrice);
    public Room getRoomById(Long id);
    public Room createRoom(Room room);
    public Room updateRoom(Long id, Room roomDetails);
    public void deleteRoom(Long id);

}
