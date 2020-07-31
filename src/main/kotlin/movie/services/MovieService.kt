package movie.services

import movie.repository.*
import movie.models.*
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager
import java.util.Date

@Service
class MovieService @Autowired constructor (
    private val auditoriumRepository: AuditoriumRepository,
    private val seatRepository: SeatRepository,
    private val screeningRepository: ScreeningRepository,
    private val resevationRepository: ResevationRepository
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
        val screening: Screening = Screening(name, Date(), Date(), auditorium)
        screeningRepository.save(screening)
        return screening 
    }

    fun getScreening(id: Int): Screening? {
        val s: Screening? = screeningRepository.findById(id).orElse(null)
        println(s!!.id)
        return s
    }

    fun createResevation(screeningId: Int, seatId: Int): Resevation? {
        val screening: Screening? = screeningRepository.getOne(screeningId)
        val seat: Seat? = seatRepository.getOne(seatId)
        //TODO: make sure that screening and seat exist and seat is in the correct screening
        val resevation = Resevation(screening!!, seat!!)
        screening!!.addResevation(resevation)
        screeningRepository.save(screening)
        return resevation
    }

    fun getResevations(screeningId: Int): List<Resevation> {
        val screening: Screening = screeningRepository.getOne(screeningId)!!
        return screening.resevations
    }
}