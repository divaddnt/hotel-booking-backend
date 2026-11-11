package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // Member 2 adds the custom query here
}