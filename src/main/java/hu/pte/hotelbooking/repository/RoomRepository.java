package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Room;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/// this communicates with the database and the custom query tells spring boot how to search for rooms using their type and maximum price
public interface RoomRepository extends JpaRepository<Room, Long> {

    // find rooms by type and maximum price
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.pricePerNight <= :maxPrice ORDER BY r.pricePerNight")
    List<Room> findRoomsByTypeAndMaxPrice(
            @Param("roomType") String roomType,
            @Param("maxPrice") BigDecimal maxPrice
    );
}
