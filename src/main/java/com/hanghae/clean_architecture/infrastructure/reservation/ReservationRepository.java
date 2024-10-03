package com.hanghae.clean_architecture.infrastructure.reservation;

import com.hanghae.clean_architecture.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
