package movie.services

import movie.repository.*
import movie.models.*
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@Service
class MovieService @Autowired constructor (
    private val auditoriumRepository: AuditoriumRepository,
    private val seatRepository: SeatRepository,
    private val screeningRepository: ScreeningRepository,
    private val resevationRepository: ResevationRepository
) {
    fun getAuditoriums(): List<Auditorium> {
        return auditoriumRepository.findAll()
    }

    fun createAuditorium(name: String, numberOfSeats: Int): Auditorium {
        val auditorium = Auditorium(name)
        auditorium.addSeats(numberOfSeats)
        auditoriumRepository.save(auditorium)
        return auditorium
    }

    fun getAuditorium(auditoriumId: Int): Auditorium {
        return auditoriumRepository
                        .findById(auditoriumId)
                        .orElseThrow{ NotFoundException("Auditorium with id ${auditoriumId} not found") }
    }

    fun getSeats(auditoriumId: Int): List<Seat> {
        val auditorium: Auditorium = auditoriumRepository
                        .findById(auditoriumId)
                        .orElseThrow{ NotFoundException("Auditorium with id ${auditoriumId} not found") }
        return auditorium.seats
    }

    fun createScreening(auditoriumId: Int, name: String, startTime: LocalDateTime, endTime: LocalDateTime): Screening {
        val auditorium: Auditorium = auditoriumRepository
                        .findById(auditoriumId)
                        .orElseThrow{ NotFoundException("Auditorium with id ${auditoriumId} not found") }

        if (endTime.isBefore(startTime)) {
            throw EndTimeIsEarlierThanStartTimeException("Starting time cant be earlier than ending time")
        }

        val overlapping: List<Screening> = screeningRepository.findOverlappingScreenings(auditorium, startTime, endTime)
        if (!overlapping.isEmpty()) {
            var err: String = "There is overlap in auditorium with id ${auditoriumId}"
            overlapping.forEach {
                err += " with screening with id ${it.id}"
            }
            throw OverLappingScreeningsException(err)
        }

        val screening: Screening = Screening(name, startTime, endTime, auditorium)
        screeningRepository.save(screening)
        return screening
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

    fun getScreening(screeningId: Int): Screening {
        return screeningRepository
                        .findById(screeningId)
                        .orElseThrow{ NotFoundException("Screening with id ${screeningId} not found") }
    }

    fun getScreeningSeats(screeningId: Int, getReserved: Boolean): List<Seat> {
        val screening: Screening = screeningRepository
                        .findById(screeningId)
                        .orElseThrow{ NotFoundException("Screening with id ${screeningId} not found") }

        val seats: List<Seat> = screening.auditorium.seats
        val resevations: List<Seat> = screening.resevations.map{it.seat}

        return seats.filter { (getReserved == resevations.contains(it)) }
    }

    fun createResevation(screeningId: Int, seatIds: List<Int>): List<Resevation> {
        val screening: Screening = screeningRepository
                        .findById(screeningId)
                        .orElseThrow{ NotFoundException("Screening with id ${screeningId} not found") }
        val seats: List<Seat> = seatIds.map { seatId -> 
                    seatRepository
                        .findById(seatId)
                        .orElseThrow{ NotFoundException("Seat with id ${seatId} not found") }
        }

        seats.forEach { seat -> 
            if (screening.auditorium != seat.auditorium) {
                throw SeatNotInAuditoriumException("Seat with id ${seat.id} is not in screening with id ${screeningId}")
            }
        }

        val resevations = seats.map { seat -> Resevation(screening, seat) }
        screening.addResevations(resevations)
        resevationRepository.saveAll(resevations)

        return resevations
    }

    fun getResevations(screeningId: Int): List<Resevation> {
        val screening: Screening = screeningRepository
                                    .findById(screeningId)
                                    .orElseThrow{ NotFoundException("Screening with id ${screeningId} not found") }
        return screening.resevations
    }
}

class NotFoundException(message: String?): ResponseStatusException(HttpStatus.NOT_FOUND, message)
class SeatNotInAuditoriumException(message: String?): ResponseStatusException(HttpStatus.BAD_REQUEST, message)
class EndTimeIsEarlierThanStartTimeException(message: String?): ResponseStatusException(HttpStatus.BAD_REQUEST, message)
class OverLappingScreeningsException(message: String?): ResponseStatusException(HttpStatus.BAD_REQUEST, message)
