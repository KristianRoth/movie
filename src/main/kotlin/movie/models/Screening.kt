package movie.models

import javax.persistence.*
import java.util.Date
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference

@Entity
@Table(name = "sreenings")
@JsonRootName("screening")
class Screening(
    val movieName: String,
    val startTime: Date,
    val endTime: Date,
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

}

@Entity
@Table(name = "resevations")
class Resevation(
    @JsonManagedReference
    @ManyToOne(
        name = "screening_id",
        fetch = FetchType.Lazy
    )
    val screening: Screening,
    @OneToOne(name = "seat_id")
    val seat: Seat
) { 
    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0

}
