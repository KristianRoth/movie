package movie.controllers

import movie.services.*
import movie.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/screenings")
class ScreeningController @Autowired constructor(
     private val movieService: MovieService
) {
    @GetMapping("/make", produces = ["application/json"])
    fun createScreening(
        @RequestParam(value = "auditoriumId", required = true) auditoriumId: Int,
        @RequestParam(value = "name", required = true) name: String,
        @RequestParam(value = "startTime", required = true) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        startTime: LocalDateTime,
        @RequestParam(value = "endTime", required = true) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        endTime: LocalDateTime
    ): ResponseEntity<Screening> {
        return ResponseEntity.ok(movieService.createScreening(auditoriumId, name, startTime, endTime))
    }

    @GetMapping("", produces = ["application/json"])
    fun getScreenings(
        @RequestParam(value = "startTime", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        startTime: LocalDateTime?,
        @RequestParam(value = "endTime", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        endTime: LocalDateTime?,
        @RequestParam(value = "upComing", required = false) upComing: Boolean?
    ): ResponseEntity<List<Screening>> {
        return ResponseEntity.ok(movieService.getScreenings(startTime, endTime, upComing))
    }

    @GetMapping("/{screeningId}")
    fun getScreening(
        @PathVariable(value = "screeningId") screeningId: Int
    ): ResponseEntity<Screening> {
        return ResponseEntity.ok(movieService.getScreening(screeningId))
    }

    @GetMapping("/{screeningId}/resevations/make")
    fun createResevation(
        @PathVariable("screeningId") screeningId: Int,
        @RequestParam(value = "seatId", required = true) seatId: Int
    ): ResponseEntity<Resevation> {
        return ResponseEntity.ok(movieService.createResevation(screeningId, seatId))
    }


    @GetMapping("/{screeningId}/resevations", produces = ["application/json"])
    fun getResevations(
        @PathVariable(value = "screeningId") screeningId: Int
    ): ResponseEntity<List<Resevation>> {
        return ResponseEntity.ok(movieService.getResevations(screeningId))
    }
}