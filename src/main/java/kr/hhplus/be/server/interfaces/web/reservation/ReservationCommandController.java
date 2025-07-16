package kr.hhplus.be.server.interfaces.web.reservation;

import jakarta.validation.Valid;
import kr.hhplus.be.server.application.reservation.dto.CancelReservationResult;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;
import kr.hhplus.be.server.application.reservation.ReservationCommandService;
import kr.hhplus.be.server.config.resolver.QueueAuth;
import kr.hhplus.be.server.config.resolver.ValidQueueToken;
import kr.hhplus.be.server.interfaces.web.reservation.request.CancelReservationRequest;
import kr.hhplus.be.server.interfaces.web.reservation.request.PlaceReservationRequest;
import kr.hhplus.be.server.interfaces.web.reservation.response.ReservationResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationCommandController {
    private final ReservationCommandService reservationCommandService;

    @PostMapping
    public ResponseEntity<ReservationResultResponse> reserve(@QueueAuth ValidQueueToken queueToken,
                                                            @RequestBody @Valid PlaceReservationRequest request
    ) {
        PlaceReservationResult result = reservationCommandService.placeWithLock(queueToken.userId(), request.toCommand());
        ReservationResultResponse response = ReservationResultResponse.of(
                result.reservationId(),
                result.reservationStatus()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<ReservationResultResponse> cancel(@QueueAuth ValidQueueToken queueToken,
                                                            @RequestBody @Valid CancelReservationRequest request) {

        CancelReservationResult result = reservationCommandService.cancel(queueToken.userId(), request.reservationId());
        ReservationResultResponse response = ReservationResultResponse.of(
                result.reservationId(),
                result.reservationStatus()
        );
        return ResponseEntity.ok(response);
    }
}
