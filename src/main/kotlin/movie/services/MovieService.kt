package movie.services

import movie.repository.MovieRepository
import movie.models.Auditorium
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

@Service
class MovieService @Autowired constructor (
    private val movieRepository: MovieRepository
) {
    

    fun createAuditorium(name: String): String {
        val auditorium = Auditorium(name)
        movieRepository.save(auditorium)
        println("testiä")
        return "OK"
    }
}