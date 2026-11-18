
package hu.pte.hotelbooking.service;
import hu.pte.hotelbooking.exception.InvalidRequestException;
import hu.pte.hotelbooking.exception.ResourceNotFoundException;
import hu.pte.hotelbooking.model.Booking;
import hu.pte.hotelbooking.model.Customer;
import hu.pte.hotelbooking.model.Room;
import hu.pte.hotelbooking.repository.BookingRepository;
import hu.pte.hotelbooking.repository.CustomerRepository;
import hu.pte.hotelbooking.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class BookingService {
    private final BookingRepository repo;
    private final CustomerRepository customerRepo;
    private final RoomRepository roomRepo;
    @PersistenceContext
    private EntityManager em;
    public BookingService(BookingRepository repo,
                          CustomerRepository customerRepo,
                          RoomRepository roomRepo) {
        this.repo = repo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
    }
    public List<Booking> getAllBookingsJPA() {
        return repo.findAll();
    }
    public Booking getBookingByIdJPA(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking with id: " + id + " not found!"));
    }
    public Booking addBookingJPA(Booking booking) {
        Customer customer = findCustomer(booking);
        Room room = findRoom(booking);
        checkDates(booking.getCheckInDate(),
                   booking.getCheckOutDate());
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setBookingDate(LocalDate.now());
        if (booking.getStatus() == null) {
            booking.setStatus("CONFIRMED");
        }
        booking.setTotalPrice(calculatePrice(
                room,
                booking.getCheckInDate(),
                booking.getCheckOutDate()));
        return repo.save(booking);
    }
    public Booking updateBookingJPA(Long id, Booking booking) {
        Booking existing = getBookingByIdJPA(id);
        Customer customer = findCustomer(booking);
        Room room = findRoom(booking);
        checkDates(booking.getCheckInDate(),
                   booking.getCheckOutDate());
        existing.setCustomer(customer);
        existing.setRoom(room);
        existing.setCheckInDate(booking.getCheckInDate());
        existing.setCheckOutDate(booking.getCheckOutDate());
        if (booking.getStatus() != null) {
            existing.setStatus(booking.getStatus());
        }
        existing.setTotalPrice(calculatePrice(
                room,
                booking.getCheckInDate(),
                booking.getCheckOutDate()));
        return repo.save(existing);
    }
    public void deleteBookingJPA(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Booking with id: " + id + " not found!");
        }
        repo.deleteById(id);
        repo.flush();
    }
    public BigDecimal calculateBookingTotalSPQ(Long id) {
        getBookingByIdJPA(id);
        StoredProcedureQuery spq =
                em.createStoredProcedureQuery("calculateBookingTotal");
        spq.registerStoredProcedureParameter(
                "bookingIdIN", Long.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter(
                "totalOUT", BigDecimal.class, ParameterMode.OUT);
        spq.setParameter("bookingIdIN", id);
        spq.execute();
        return (BigDecimal) spq.getOutputParameterValue("totalOUT");
    }
    public List<Booking> findBookingsByCustomerId(Long customerId) {

    throw new UnsupportedOperationException(

            "Waiting for Yuka's repository method");

}
    private Customer findCustomer(Booking booking) {
        if (booking.getCustomer() == null
                || booking.getCustomer().getId() == null) {
            throw new InvalidRequestException(
                    "customer.id is required");
        }
        Long customerId = booking.getCustomer().getId();
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id: " + customerId + " not found!"));
    }
    private Room findRoom(Booking booking) {
        if (booking.getRoom() == null
                || booking.getRoom().getId() == null) {
            throw new InvalidRequestException(
                    "room.id is required");
        }
        Long roomId = booking.getRoom().getId();
        return roomRepo.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room with id: " + roomId + " not found!"));
    }
    private void checkDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null
                || checkOut == null
                || !checkOut.isAfter(checkIn)) {
            throw new InvalidRequestException(
                    "Check-out date must be after check-in date");
        }
    }
    private BigDecimal calculatePrice(
            Room room, LocalDate checkIn, LocalDate checkOut) {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return room.getPricePerNight()
                .multiply(BigDecimal.valueOf(nights));
    }
}