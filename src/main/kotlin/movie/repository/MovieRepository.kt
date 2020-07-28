package movie.repository

import movie.models.Auditorium
import movie.models.Seat
import movie.models.Screening
import movie.models.Resevation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
 

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

}

@Repository
interface ResevationRepository: JpaRepository<Resevation, Int> {

}