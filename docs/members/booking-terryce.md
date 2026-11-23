# Booking — Terryce

## What I built

I wrote `BookingController.java` to connect the booking endpoints to MahNoor’s `BookingService`. I tested the endpoints in Swagger and took screenshots of the results.

## Endpoints

| HTTP | URL | What it does | Type |
| --- | --- | --- | --- |
| GET | `/booking/jpa` | List all bookings | CRUD |
| GET | `/booking/jpa/{id}` | Get a booking by ID | CRUD |
| POST | `/booking/jpa/add` | Add a booking | CRUD |
| PUT | `/booking/jpa/edit/{id}` | Edit a booking | CRUD |
| DELETE | `/booking/jpa/delete/{id}` | Delete a booking | CRUD |
| GET | `/booking/spq/total/{id}` | Get the cost of a booking | Stored procedure |
| GET | `/booking/query/findByCustomerId/{customerId}` | List bookings for one customer | Custom query |

## Stored procedure

I tested `calculateBookingTotal` with booking `7`. The procedure takes the booking ID in `bookingIdIN` and returns the total in `totalOUT`. Swagger showed `135`, which matched three nights at `45` per night.

## Custom query

The service uses `BookingRepository.findBookingsByCustomerId` to look up a customer’s bookings. I entered customer ID `1` in Swagger and found my test booking, `7`, in the list.

## Tests

I tested the booking routes in Swagger on 19–20 November 2026. All seven requests below returned HTTP `200`.

- **List:** Swagger showed a JSON list of bookings. [Screenshot](../screenshots/all-bookings.png)
- **Add:** I created booking `7` for two nights. Its status was `CONFIRMED` and the total was `90`. [Screenshot](../screenshots/add-booking.png)
- **Get by ID:** I entered `7` and got that booking back. [Screenshot](../screenshots/booking-by-id.png)
- **Edit:** I changed checkout to `2026-12-23`. The total went from `90` to `135`. [Screenshot](../screenshots/edit-booking.png)
- **Find by customer:** The results for customer `1` included booking `7`. [Screenshot](../screenshots/customer-bookings.png)
- **Get total:** The stored procedure returned `135`. [Screenshot](../screenshots/booking-total.png)
- **Delete:** I used another test booking, `8`. The response confirmed it was deleted. [Screenshot](../screenshots/delete-booking.png)

I also saw how the API handles a missing booking. A delete request for booking `7` returned HTTP `404` with `Booking with id: 7 not found!`.

## Problems I solved

- NetBeans gave me a `${start-class}` error when I tried to run the project. I opened **Run Maven** and used `spring-boot:run` with `HotelbookingApplication` as the main class.
- Spring was trying to connect on port `3306`, but my test database was running on `3307`. I used `3307` in my local run settings and opened Swagger on port `8081`.
