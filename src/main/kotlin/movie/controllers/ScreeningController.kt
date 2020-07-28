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
     fun getAuditorium(
        @RequestParam(value = "auditoriumId", required = true) id: Int
     ): Screening? {
          return movieService.createScreening("HP22 Salaisuuksien Kamari", id)
     }

}