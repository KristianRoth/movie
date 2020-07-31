package movie.repository

import movie.models.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
 

@Repository
interface AuditoriumRepository: JpaRepository<Auditorium, Int> {
    fun findByName(name: String): Auditorium?
    fun existsByName(name: String): Boolean
}

@Repository
interface SeatRepository: JpaRepository<Seat, Int> {
    
}

@Repository
interface ScreeningRepository: JpaRepository<Screening, Int> {
    fun findByStartTimeBetween(startTime: LocalDateTime, endTime: LocalDateTime): List<Screening>
    fun findByStartTimeGreaterThan(startTime: LocalDateTime): List<Screening>
    fun findByStartTimeLessThan(startTime: LocalDateTime): List<Screening>
}

@Repository
interface ResevationRepository: JpaRepository<Resevation, Int> {

}