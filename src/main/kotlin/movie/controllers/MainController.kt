package movie.controllers

import org.springframework.web.bind.annotation.*


@RestController
class MainController() {
     @GetMapping("/")
     fun hello() = "Hello World!"
}