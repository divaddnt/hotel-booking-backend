# Room — Somalin Ban

## What I built
- Files: repository/RoomRepository.java, service/RoomService.java, controller/RoomController.java,
  database/procedures/room_procedures.sql

## Endpoints
| HTTP | URL | What it does | Type (CRUD / Stored procedure / Custom query) |
|------|-----|--------------|-----------------------------------------------|
| GET | /room/jpa | Get all rooms | CRUD |
| GET | /room/jpa/{id} | Get one room | CRUD |
| POST | /room/jpa/add | Add a room | CRUD |
| PUT | /room/jpa/edit/{id} | Edit a room | CRUD |
| DELETE | /room/jpa/delete/{id} | Delete a room | CRUD |
| GET | /room/spq/available | Rooms free between two dates | Stored procedure |
| GET | /room/query/byTypeAndMaxPrice | Rooms by type under a max price | Custom query |

## Stored procedure
- Name: `getAvailableRooms`
- Parameters: `checkInIN` (IN, DATE), `checkOutIN` (IN, DATE)
- Returns: the rooms that are AVAILABLE and have no non-cancelled booking overlapping the dates.
- Example call: `GET /room/spq/available?checkIn=2026-12-01&checkOut=2026-12-04`
- Result: rooms 101, 201, 202, 302 (102 is booked, 301 is in maintenance).

## Custom query
```java
@Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.pricePerNight <= :maxPrice ORDER BY r.pricePerNight")
```
- Returns: rooms of the given type with a price per night up to the maximum, cheapest first.
- Example call: `GET /room/query/byTypeAndMaxPrice?roomType=DOUBLE&maxPrice=80`
- Result: room 102 (70.00) and room 201 (75.00).

## Tests


## Problems I solved
- I often get errors by placing a new method in another. Meaning i started a new project without exiting which often flags my code as an error that took me a few minutes to figure out the small problem. I solved it by checking where the previous method ended and closing it with `}` before starting the new one.

- I had to deal with multiple error messages on the side bar because somehow NetBeans on Mac doesn’t automatically suggest the correct import upon typing the methods/keywords. Therefore, I have to constantly and manually import one by one. I solved it by reading each error message and adding the missing import myself via the side bar.