package movie.services

import movie.repository.*
import movie.models.*
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

@Service
class MovieService @Autowired constructor (
    private val auditoriumRepository: AuditoriumRepository,
    private val seatRepository: SeatRepository,
    private val screeningRepository: ScreeningRepository
) {

    fun createAuditorium(name: String): String {
        val auditorium = Auditorium(name)
        
        auditorium.addSeats(10)
        auditoriumRepository.save(auditorium)
    
        return "OK"
    }


    fun getAuditorium(id: Int): Auditorium? {
        return auditoriumRepository.findById(id).orElse(null)
    }

    fun createScreening(name: String, auditoriumId: Int): Screening {
        val auditorium = auditoriumRepository.getOne(auditoriumId)
        val screening: Screening = Screening(name, 1, 1, Auditorium("testi"))
        screeningRepository.save(screening)
        return screening 
    }

}