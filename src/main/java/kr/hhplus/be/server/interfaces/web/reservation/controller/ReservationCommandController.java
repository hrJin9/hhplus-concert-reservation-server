package kr.hhplus.be.server.interfaces.web.reservation.controller;

import jakarta.validation.Valid;
import kr.hhplus.be.server.application.reservation.dto.ReservationResult;
import kr.hhplus.be.server.application.reservation.service.ReservationCommandService;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.ValidQueueToken;
import kr.hhplus.be.server.interfaces.web.reservation.request.CreateReservationRequest;
import kr.hhplus.be.server.interfaces.web.reservation.response.ReservationResponse;
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
    public ResponseEntity<ReservationResponse> placeReservation(@QueueAuth ValidQueueToken queueToken,
                                                                @RequestBody @Valid CreateReservationRequest request) {
        ReservationResult reservationResult = reservationCommandService.placeReservation(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(ReservationResponse.from(reservationResult));
    }
}
