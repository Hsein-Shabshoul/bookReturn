package com.average.bookReturn.rabbitMq;

import com.average.bookReturn.exception.BadRequestException;
import com.average.bookReturn.reservations.Reservation;
import com.average.bookReturn.reservations.ReservationRepository;
import com.average.bookReturn.users.user.User;
import com.average.bookReturn.users.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
@Service
@Log4j2
@RequiredArgsConstructor
public class BookReturnListener {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public Double calculateAverageDurationForUser(User user, Reservation thisReservation) {
        Optional<List<Reservation>> reservationsOptional = reservationRepository.findByUserId(user.getId());
        if (reservationsOptional.isEmpty()) {
            return null;
        }
        List<Reservation> reservations = reservationsOptional.get();
        long totalDurationInSeconds = 0;
        //getting reservations where return date is not null
        List<Reservation> reservationsWithReturnDatetime = reservations
                .stream()
                .filter(reservation -> reservation.getReturnDatetime() != null)
                .toList();
        //if an average already existed, skip recalculating and just adjust it according to the new reservation
        if (user.getAverageReturn() != null) {
            Duration duration = Duration.ofDays(Duration.between(thisReservation.getReservationDatetime(), thisReservation.getReturnDatetime()).toDays());
            double totalDays = (user.getAverageReturn() * (reservationsWithReturnDatetime.size() - 1)) + duration.toDays();
            double newAverage = totalDays / reservationsWithReturnDatetime.size();
            return newAverage;
        }
        else {
            for (Reservation reservation : reservationsWithReturnDatetime) {
                Duration duration = Duration.between(reservation.getReservationDatetime(), reservation.getReturnDatetime());
                totalDurationInSeconds += duration.getSeconds();
            }
            return totalDurationInSeconds / (86400.0 * reservationsWithReturnDatetime.size());
        }
    }
    @RabbitListener(queues = "q.average.bookReturn")
    public void onBookReturn(Long reservation_id) {
        log.info("Reservation id returned: {}", reservation_id);
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation_id);
        if (reservationOptional.isEmpty()){
            log.info("Reservation not found");
            throw new BadRequestException("Reservation not found.");
        }
        Reservation reservation = reservationOptional.get();
        User user = reservation.getUser();
        Double averageDays = calculateAverageDurationForUser(user, reservation);
        Double roundedResult = Double.parseDouble(new DecimalFormat("#.###").format(averageDays));
        user.setAverageReturn(roundedResult);
        userRepository.save(user);
        log.info("Average time for book return for user {} is: {} days", reservation.getUser_email(), roundedResult);
    }
}
