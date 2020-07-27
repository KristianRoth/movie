package movie.repository

import movie.models.Auditorium
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface MovieRepository: CrudRepository<Auditorium, Int> {
    // override fun findById(id: Int): Auditorium?
}