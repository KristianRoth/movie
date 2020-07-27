package movie.controllers

import movie.services.MovieService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class MainController @Autowired constructor(
     private val movieService: MovieService
) {
     @GetMapping("/")
     fun hello() = "Hello World!!"

     @GetMapping("/make")
     fun make(): String {
          movieService.createAuditorium("testi auditorio")
          return "palautus make"
     }

}