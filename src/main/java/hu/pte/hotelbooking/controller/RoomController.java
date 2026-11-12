package hu.pte.hotelbooking.controller;

import hu.pte.hotelbooking.model.Room;
import hu.pte.hotelbooking.service.RoomService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author somalinban
 */
@RestController
public class RoomController {

    @Autowired
    private RoomService service;

    @GetMapping(value = "/room/jpa")
    public List<Room> getAllRoomsJPA() {
        return service.getAllRoomsJPA();
    }

    @GetMapping(value = "/room/jpa/{id}")
    public Room getRoomByIdJPA(@PathVariable Long id) {
        return service.getRoomByIdJPA(id);
    }

    @PostMapping(value = "/room/jpa/add")
    public Room addRoomJPA(@RequestBody Room room) {
        return service.addRoomJPA(room);
    }

    @PutMapping(value = "/room/jpa/edit/{id}")
    public Room updateRoomJPA(@PathVariable Long id, @RequestBody Room room) {
        return service.updateRoomJPA(id, room);
    }

    @DeleteMapping(value = "/room/jpa/delete/{id}")
    public String deleteRoomJPA(@PathVariable Long id) {
        service.deleteRoomJPA(id);
        return "Room with id: " + id + " deleted";
    }

    @GetMapping(value = "/room/spq/available")
    public List<Room> getAvailableRoomsSPQ(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        return service.getAvailableRoomsSPQ(checkIn, checkOut);
    }

    @GetMapping(value = "/room/query/byTypeAndMaxPrice")
    public List<Room> findRoomsByTypeAndMaxPrice(@RequestParam String roomType, @RequestParam BigDecimal maxPrice) {
        return service.findRoomsByTypeAndMaxPrice(roomType, maxPrice);
    }

}
