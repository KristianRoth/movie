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

    fun createScreening(auditoriumId: Int, name: String, startTime: LocalDateTime, endTime: LocalDateTime): Screening? {
        val auditorium = auditoriumRepository.findById(auditoriumId).orElse(null)
        auditorium?.let {
            val screening: Screening = Screening(name, startTime, endTime, it)
            screeningRepository.save(screening)
            return screening
        }
        return null
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

    fun createResevation(screeningId: Int, seatId: Int): Resevation {
        val screening: Screening? = screeningRepository.findById(screeningId).orElse(null)
        val seat: Seat? = seatRepository.findById(seatId).orElse(null)
        screening?.let { screening ->
            seat?.let { seat -> 
                if (screening.auditorium != seat.auditorium) {
                    throw SeatNotInAuditoriumException("Seat with id ${seatId} is not in screening with id ${screeningId}")
                }
                val resevation = Resevation(screening, seat)
                screening.addResevation(resevation)
                resevationRepository.save(resevation)
                return resevation
            }
        }
        throw NotFoundException("Screening with id ${screeningId} or seat with id ${seatId} not found")
    }

    fun getResevations(screeningId: Int): List<Resevation>? {
        val screening: Screening? = screeningRepository.findById(screeningId).orElse(null)
        return screening?.let { screening ->
            return screening.resevations
        }
        return null
    }
}

class NotFoundException(message: String?): Exception(message)
class SeatNotInAuditoriumException(message: String?): Exception(message)