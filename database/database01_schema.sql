-- 01_schema.sql | owner: lead | run first
DROP DATABASE IF EXISTS hotel_booking;
CREATE DATABASE hotel_booking CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE hotel_booking;

CREATE TABLE customer (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    full_name         VARCHAR(100) NOT NULL,
    email             VARCHAR(100) NOT NULL,
    phone             VARCHAR(30),
    address           VARCHAR(255),
    registration_date DATE         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_customer_email UNIQUE (email)
) ENGINE = InnoDB;

CREATE TABLE room (
    id              BIGINT        NOT NULL AUTO_INCREMENT,
    room_number     VARCHAR(10)   NOT NULL,
    room_type       VARCHAR(20)   NOT NULL,  -- SINGLE, DOUBLE, SUITE
    price_per_night DECIMAL(10,2) NOT NULL,
    capacity        INT           NOT NULL,
    floor           INT           NOT NULL,
    status          VARCHAR(20)   NOT NULL,  -- AVAILABLE, MAINTENANCE
    PRIMARY KEY (id),
    CONSTRAINT uk_room_room_number UNIQUE (room_number)
) ENGINE = InnoDB;

CREATE TABLE booking (
    id             BIGINT        NOT NULL AUTO_INCREMENT,
    customer_id    BIGINT        NOT NULL,
    room_id        BIGINT        NOT NULL,
    check_in_date  DATE          NOT NULL,
    check_out_date DATE          NOT NULL,
    total_price    DECIMAL(10,2),
    status         VARCHAR(20)   NOT NULL,  -- CONFIRMED, CANCELLED, COMPLETED
    booking_date   DATE          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_booking_customer FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_booking_room     FOREIGN KEY (room_id)     REFERENCES room (id)
) ENGINE = InnoDB;

CREATE TABLE payment (
    id             BIGINT        NOT NULL AUTO_INCREMENT,
    booking_id     BIGINT        NOT NULL,
    amount         DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(20)   NOT NULL,  -- CARD, CASH, TRANSFER
    status         VARCHAR(20)   NOT NULL,  -- PENDING, PAID, REFUNDED
    payment_date   DATETIME      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES booking (id)
) ENGINE = InnoDB;
