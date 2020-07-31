package movie.controllers

import movie.services.MovieService
import movie.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/api/auditoriums")
class AuditoriumController @Autowired constructor(
     private val movieService: MovieService
) {
     @GetMapping("/make")
     fun make(
          @RequestParam(value = "auditoriumName", required = true) auditoriumName: String,
          @RequestParam(value = "numberOfSeats", required = true) numberOfSeats: Int
     ): ResponseEntity<Auditorium> {
          return ResponseEntity.ok(movieService.createAuditorium(auditoriumName, numberOfSeats))
          
     }

     @GetMapping("/{auditoriumId}", produces = ["application/json"])
     fun getAuditorium(
          @PathVariable(value = "auditoriumId") auditoriumId: Int
     ): ResponseEntity<Auditorium> {
          return ResponseEntity.ok(movieService.getAuditorium(auditoriumId))
     }

     @GetMapping("/{auditoriumId}/seats", produces = ["application/json"])
     fun getSeats(
          @PathVariable(value = "auditoriumId") auditoriumId: Int
     ): ResponseEntity<List<Seat>> {
          return ResponseEntity.ok(movieService.getSeats(auditoriumId))   
     }

}