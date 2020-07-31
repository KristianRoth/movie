package movie.controllers

import movie.services.MovieService
import movie.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


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
          movieService.getAuditorium(auditoriumId)?.let {
               return ResponseEntity.ok(it)
          }?: run {
               throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Auditorium with id ${auditoriumId} not found"
               )
          }
     }

     @GetMapping("/{auditoriumId}/seats", produces = ["application/json"])
     fun getSeats(
          @PathVariable(value = "auditoriumId") auditoriumId: Int
     ): ResponseEntity<List<Seat>> {
          movieService.getSeats(auditoriumId)?.let {
               return ResponseEntity.ok(it)
          }?: run {
               throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Auditorium with id ${auditoriumId} not found"
               )
          }
     }

}