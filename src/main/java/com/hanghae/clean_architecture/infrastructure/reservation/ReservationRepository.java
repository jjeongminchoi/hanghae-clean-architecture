package com.hanghae.clean_architecture.infrastructure.reservation;

import com.hanghae.clean_architecture.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.userId = :userId")
    List<Reservation> searchReservations(@Param("userId") Long userId);
}
