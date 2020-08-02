package movie.models

import javax.persistence.*
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference

@Entity
@Table(name = "auditoriums")
class Auditorium(
    val name: String
) {
    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0

    @JsonIgnore
    @OneToMany(
        fetch = fetchType.Lazy,
        cascade = [(CascadeType.ALL)]
    )
    var seats: List<Seat> = ArrayList()

    fun addSeats(amount: Int) {
        for (i in 1..amount ) seats += Seat(i, this)
    } 
}

@Entity
@Table(name = "seats")
class Seat(
    val seatNumber: Int,
    @JsonIgnore
    @ManyToOne(
        name = "auditorium_id",
        fetch = FetchType.Lazy
    )
    val auditorium: Auditorium
) { 
    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0
}
