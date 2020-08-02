package movie.models

import javax.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference

@Entity
@Table(name = "screenings")
@JsonRootName("screening")
class Screening(
    val movieName: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    @OneToOne(
        name = "auditorium_id",
        cascade = [(CascadeType.ALL)],
        fetch = FetchType.Lazy    
    )
    val auditorium: Auditorium
) {
    @JsonBackReference
    @OneToMany(
        name = "resevation_id",
        cascade = [(CascadeType.ALL)],
        fetch = FetchType.Lazy
    )
    var resevations: List<Resevation> = ArrayList()

    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0

    fun addResevation(resevation: Resevation) {
        resevations += resevation
    }

    fun addResevations(resevations: List<Resevation>) {
        resevations.forEach { addResevation(it) }
    }
}

@Entity
@JsonRootName("resevation")
@Table(
    name = "resevations",
    uniqueConstraints = [UniqueConstraint(columnNames = ["screening_id", "seat_id"], name = "unique seat in screening constraint")]
)
class Resevation(
    @JsonManagedReference
    @ManyToOne(
        name = "screening_id",
        cascade = [(CascadeType.NONE)],
        fetch = FetchType.Lazy
    )
    val screening: Screening,
    @OneToOne(
        name = "seat_id",
        cascade = [(CascadeType.ALL)]
    )
    val seat: Seat
) { 
    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0
    val serial: String = this.screening.startTime.format(DateTimeFormatter.ofPattern("ddMMyyyy"))+this.screening.id+this.seat.seatNumber
}

