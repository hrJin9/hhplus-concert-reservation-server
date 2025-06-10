package kr.hhplus.be.server.interfaces.web.reservation;

import jakarta.validation.Valid;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;
import kr.hhplus.be.server.application.reservation.ReservationCommandService;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.ValidQueueToken;
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
    public ResponseEntity<ReservationResultResponse> create(@QueueAuth ValidQueueToken queueToken,
                                                            @RequestBody @Valid PlaceReservationRequest request
    ) {
        PlaceReservationResult reservationResult = reservationCommandService.place(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(ReservationResultResponse.from(reservationResult));
    }
}
