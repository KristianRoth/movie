package movie.controllers

import movie.services.*
import movie.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
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
        movieService.createScreening(auditoriumId, name, startTime, endTime)?.let {
            return ResponseEntity.ok(it)
        }?:run {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Auditorium with id ${auditoriumId} not found"
            )
        }
    }

    @GetMapping("/{screeningId}")
    fun getScreening(
        @PathVariable(value = "screeningId") screeningId: Int
    ): ResponseEntity<Screening> {
        movieService.getScreening(screeningId)?.let {
            return ResponseEntity.ok(it)
        }?:run {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Screening with id ${screeningId} not found"
            )
        }
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

    @GetMapping("/{screeningId}/resevations/make")
    fun createResevation(
        @PathVariable("screeningId") screeningId: Int,
        @RequestParam(value = "seatId", required = true) seatId: Int
    ): ResponseEntity<Resevation> {
        try {
            return ResponseEntity.ok(movieService.createResevation(screeningId, seatId))
        } catch (e: Exception) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                e.message,
                e
            );
        }
    }


    @GetMapping("/{screeningId}/resevations", produces = ["application/json"])
    fun getResevations(
        @PathVariable(value = "screeningId") screeningId: Int
    ): ResponseEntity<List<Resevation>> {
        movieService.getResevations(screeningId)?.let {
            return ResponseEntity.ok(it)
        }?:run {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Screening with id ${screeningId} not found"
            );
        }
    }
}