package hu.pte.hotelbooking.service;

import hu.pte.hotelbooking.exception.InvalidRequestException;
import hu.pte.hotelbooking.exception.ResourceNotFoundException;
import hu.pte.hotelbooking.model.Room;
import hu.pte.hotelbooking.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository repo;

    @PersistenceContext
    private EntityManager em;

    //// get all the rooms from the database
    public List<Room> getAllRoomsJPA() {
        return repo.findAll();
    }

    /// following zhara's backendshop orderservice 
    public Room getRoomByIdJPA(Long id) {
        Optional<Room> optionalRoom = repo.findById(id);
        /// search in the database for the room by its id and store it in optional room

    /// if it is there, just store it and return it, if not return an error message
    if (optionalRoom.isPresent()) {
            return optionalRoom.get();
        } else {
            throw new ResourceNotFoundException("Room with id: " + id + " not found!");
        }
    }

    public Room addRoomJPA(Room room) {
        /// if room status return to nothing(null) we set it to available
        if (room.getStatus() == null) {
            room.setStatus("AVAILABLE");
        }
        //// if the room's price is missing (nonexistence) or it is 0 or smaller, we straw an exception 
        if (room.getPricePerNight() == null || room.getPricePerNight().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Price per night must be greater than 0!");
        }
        return repo.save(room);
    }

    /*
    public void addRoom (Room room) {
        /// if room status return to nothing(null) we set it to available
        if (room.getStatus() == null) {
            room.setStatus("AVAILABLE");
        }
        
        //// if the room's price is missing (nonexistence) or it is 0 or smaller, we straw an exception 
        if (room.getPricePerNight() == null || room.getPricePerNight().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Price per night must be greater than 0");
        }
    
    /// store a procedure called addroom to add our room
    StoredProcedureQuery spq = em.createStoredProcedureQuery("addRoom");
    
    /// telling java what it needs as input
    spq.registerStoredProcedureParameter("roomNumberIN", String.class, ParameterMode.IN);
    spq.registerStoredProcedureParameter("roomTypeIN", String.class, ParameterMode.IN);
    spq.registerStoredProcedureParameter("pricePerNightIN", BigDecimal.class, ParameterMode.IN);
    spq.registerStoredProcedureParameter("capacityIN", Integer.class, ParameterMode.IN);
    spq.registerStoredProcedureParameter("floorIN", Integer.class, ParameterMode.IN);
    spq.registerStoredProcedureParameter("statusIN", String.class, ParameterMode.IN);
    
    
    /// set the value to the database 
    spq.setParameter("roomNumberIN", room.getRoomNumber());
    spq.setParameter("roomTypeIN", room.getRoomType());
    spq.setParameter("pricePerNightIN", room.getPricePerNight());
    spq.setParameter("capacityIN", room.getCapacity());
    spq.setParameter("floorIN", room.getFloor());
    spq.setParameter("statusIN", room.getStatus());

    spq.execute();
        
    } */

    public Room updateRoomJPA(Long id, Room room) {
        Room existing = getRoomByIdJPA(id); // find the existed room using id 

        /// update the room number if it need changes
    existing.setRoomNumber(room.getRoomNumber());
        //// let say that the room 201 is under maintenence so 202 is now 201...
    /// change the room type ex: single to double bed
    existing.setRoomType(room.getRoomType());
        /// change room price 
    existing.setPricePerNight(room.getPricePerNight());
        /// set how many people can be in the room ex: 2 -> 3 if room change from single to double bed...
    existing.setCapacity(room.getCapacity());
        /// updating the floor if room is being changed to another floor
    existing.setFloor(room.getFloor());
        //// updating the status ex: from available to maintenence 
    existing.setStatus(room.getStatus());
        /// save the update to the database
    return repo.save(existing);
    }

    public void deleteRoomJPA(Long id) {
        /// checking if the room exist, if not throw an exception
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Room with id: " + id + " not found!");
        }
        /// if it does, delete it 
        repo.deleteById(id);
        repo.flush();
    

    /// use flush to check for errors and send the command to the database 
    }
    //// if the check in dates are equal to the check out date or the check-out is earlier than the check in , throw an error.
      public List<Room> getAvailableRoomsSPQ(LocalDate checkIn, LocalDate checkOut) {
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new InvalidRequestException("Check-out date must be after check-in date!");
        }
        /// store a procedure called getAvailableRooms to find available rooms
        StoredProcedureQuery spq = em.createStoredProcedureQuery("getAvailableRooms", Room.class);
        //// telling java what it needs as input (check in and check out dates)
        spq.registerStoredProcedureParameter("checkInIN", LocalDate.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("checkOutIN", LocalDate.class, ParameterMode.IN);

        /// set the dates to the database
        spq.setParameter("checkInIN", checkIn);
        spq.setParameter("checkOutIN", checkOut);

        // return as a list
        return spq.getResultList();
    }

    // find rooms by their type and maximum price
    public List<Room> findRoomsByTypeAndMaxPrice(String roomType, BigDecimal maxPrice) {
        /// get the rooms from the repository that match the room type and max price
            List<Room> result = repo.findRoomsByTypeAndMaxPrice(roomType.toUpperCase(), maxPrice);

        /// check if there are no matching rooms
            if (result.isEmpty()) {
            throw new ResourceNotFoundException("No " + roomType + " rooms up to " + maxPrice+".");
        }

        /// return the list of matching rooms
        return result;
    }
}
