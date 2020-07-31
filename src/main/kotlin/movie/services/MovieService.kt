package movie.services

import movie.repository.*
import movie.models.*
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

@Service
class MovieService @Autowired constructor (
    private val auditoriumRepository: AuditoriumRepository,
    private val seatRepository: SeatRepository,
    private val screeningRepository: ScreeningRepository,
    private val resevationRepository: ResevationRepository
) {

    fun createAuditorium(name: String, numberOfSeats: Int): Auditorium {
        val auditorium = Auditorium(name)

        auditorium.addSeats(numberOfSeats)
        auditoriumRepository.save(auditorium)
        return auditorium
    }

    fun getAuditorium(id: Int): Auditorium? {
        return auditoriumRepository.findById(id).orElse(null)
    }

    fun getSeats(auditoriumId: Int): List<Seat>? {
        val auditorium: Auditorium? = auditoriumRepository.findById(auditoriumId).orElse(null)
        return auditorium?.seats
    }

    fun createScreening(name: String, auditoriumId: Int): Screening {
        val auditorium = auditoriumRepository.getOne(auditoriumId)
        val screening: Screening = Screening(name, LocalDateTime.now(), LocalDateTime.now(), auditorium)
        screeningRepository.save(screening)
        return screening 
    }

    fun getScreening(id: Int): Screening? {
        return screeningRepository.findById(id).orElse(null)
    }

    fun getScreenings(startTime: LocalDateTime?, endTime: LocalDateTime?, upComing: Boolean?): List<Screening> {
        fun screenings(startTime: LocalDateTime?, endTime: LocalDateTime?): List<Screening> {
            endTime ?.let { endTime ->
                startTime ?. let { startTime ->
                    return screeningRepository.findByStartTimeBetween(startTime, endTime)
                } ?: run {
                    return screeningRepository.findByStartTimeLessThan(endTime)
                }
            } ?: run {
                startTime ?. let{ startTime -> 
                    return screeningRepository.findByStartTimeGreaterThan(startTime)
                } ?: run {
                    return screeningRepository.findAll()
                }
            }
        }
        
        if (upComing ?: false) {
            return screenings(LocalDateTime.now(), endTime)
        } 
        return screenings(startTime, endTime)
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

    fun getResevations(screeningId: Int): List<Resevation>? {
        val screening: Screening? = screeningRepository.findById(screeningId).orElse(null)
        return screening?.let { screening ->
            return screening.resevations
        }
        return null
    }
}