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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Log4j2
@RequiredArgsConstructor
public class BookReturnListener {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public long calculateReservationDurationInHours(Reservation reservation) {
        LocalDateTime reservationDatetime = reservation.getReservationDatetime();
        LocalDateTime returnDatetime = reservation.getReturnDatetime();

        if (reservationDatetime != null && returnDatetime != null) {
            Duration duration = Duration.between(reservationDatetime, returnDatetime);
            return duration.toHours();
        }
        return 0;
    }
    public Double calculateAverageDurationForUser(Long userId) {
        Optional<List<Reservation>> reservationsOptional = reservationRepository.findByUserId(userId);

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

        for (Reservation reservation : reservationsWithReturnDatetime) {
            Duration duration = Duration.between(reservation.getReservationDatetime(), reservation.getReturnDatetime());
            totalDurationInSeconds += duration.getSeconds();
        }
        return totalDurationInSeconds / (86400.0 * reservationsWithReturnDatetime.size());
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

        Double averageDays = calculateAverageDurationForUser(user.getId());
        Double roundedResult = Double.parseDouble(new DecimalFormat("#.###").format(averageDays));
        user.setAverageReturn(roundedResult);
        userRepository.save(user);
        log.info("Average time for book return for user {} is: {} days" ,reservation.getUser_email(),roundedResult);

    }
}
