package movie.controllers

import movie.services.MovieService
import movie.models.Auditorium
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api")
class MainController @Autowired constructor(
     private val movieService: MovieService
) {
     @GetMapping("/")
     fun hello() = "Hello world!"

     @GetMapping("/make")
     fun make(): String {
          movieService.createAuditorium("testi auditorio")
          return "palautus make"
     }

     @GetMapping("/auditorium/find", produces = ["application/json"])
     fun getAuditorium(
          @RequestParam(value = "id", required = true) id: Int
     ): Auditorium? {
          return movieService.getAuditorium(id)
     }

}