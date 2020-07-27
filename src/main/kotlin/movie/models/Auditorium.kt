package movie.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column


@Entity
data class Auditorium(
        @Column(nullable = false)
        var name: String,
        @Id
        @GeneratedValue(stretegy = GenerationType.IDENTITY)
        var id: Int = 0
) {

}