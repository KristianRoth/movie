package movie.controllers

import movie.services.MovieService
import movie.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/screening")
class ScreeningController @Autowired constructor(
     private val movieService: MovieService
) {
    @GetMapping("/")
    fun hello() = "Hello World!!"

    @GetMapping("/make", produces = ["application/json"])
    fun createScreening(
    @RequestParam(value = "auditoriumId", required = true) id: Int
    ): Screening? {
        return movieService.createScreening("HP22 Salaisuuksien Kamari", id)
    }

    @GetMapping("/{screeningId}")
    fun getScreening(
        @PathVariable(value = "screeningId") screeningId: Int
    ): Screening? {
        return movieService.getScreening(screeningId)
    }

    @GetMapping("/{screeningId}/resevations")
    fun getResevations(
        @PathVariable(value = "screeningId") screeningId: Int
    ): List<Resevation> {
        return movieService.getResevations(screeningId)
    }

    @GetMapping("/{screeningId}/resevations/make")
    fun createResevation(
        @PathVariable("screeningId") screeningId: Int,
        @RequestParam(value = "seatId", required = true) seatId: Int
    ): Resevation? {
        return movieService.createResevation(screeningId, seatId)
    }

}