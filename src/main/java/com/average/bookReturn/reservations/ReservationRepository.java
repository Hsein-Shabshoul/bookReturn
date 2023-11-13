package com.average.bookReturn.reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByUserId(Long id);
//    Optional<List<Reservation>> findByUserIdAndBookId(Long userId, Long bookId);

//works
//    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
//    List<Reservation> findReservationsByUserId(@Param("userId") Integer userId);
//    default Double calculateAverageDurationForUser(@Param("userId") Integer userId) {
//        List<Reservation> reservations = findReservationsByUserId(userId);
//
//        if (reservations.isEmpty()) {
//            return null;
//        }
//        long totalDurationInSeconds = 0;
//
//        for (Reservation reservation : reservations) {
//            Duration duration = Duration.between(reservation.getReservationDatetime(), reservation.getReturnDatetime());
//            totalDurationInSeconds += duration.getSeconds();
//        }
//        return totalDurationInSeconds / (86400.0 * reservations.size());
//    }

    //did not work
    //    @Query("SELECT AVG(CAST(DATE_PART('day', r.returnDatetime - r.reservationDatetime)) FROM Reservation r WHERE r.user_id = :userId")
//    Double calculateAverageReturnTimeForUser(@Param("userId") Integer userId);

//    @Query("SELECT AVG(FUNCTION('EXTRACT', EPOCH FROM (r.returnDatetime - r.reservationDatetime)) / 86400) " +
//            "FROM Reservation r " +
//            "WHERE r.user.id = :userId")
//    Double calculateAverageDurationForUser(@Param("userId") Integer userId);
}

