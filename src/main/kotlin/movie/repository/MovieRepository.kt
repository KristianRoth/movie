package movie.repository

import movie.models.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("""SELECT s FROM Screening s 
            WHERE s.auditorium = :auditorium
            AND (:startTime < s.endTime) AND (:endTime > s.startTime)""")
    fun findOverlappingScreenings(
        @Param("auditorium") auditorium: Auditorium,
        @Param("startTime") startTime: LocalDateTime,
        @Param("endTime") endTime: LocalDateTime
    ): List<Screening> 
}



@Repository
interface ResevationRepository: JpaRepository<Resevation, Int> {

}
