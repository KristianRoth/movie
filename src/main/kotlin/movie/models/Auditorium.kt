package movie.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column
import javax.persistence.Table
import javax.persistence.OneToMany
import javax.persistence.ManyToOne
import javax.persistence.CascadeType
import javax.persistence.JoinColumn
import javax.persistence.FetchType


@Entity
@Table(name = "auditoriums")
class Auditorium(
    val name: String
) {

    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0

    @OneToMany(
        fetch = fetchType.LAZY,
        cascade = [(CascadeType.ALL)]
    )
    var seats: List<Seat> = ArrayList()

    fun addSeats(amount: Int) {
        for (i in 1..amount ) seats += Seat(i)
    } 

}



@Entity
@Table(name = "seats")
class Seat(
    val seatNumber: Int
) { 
    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0

}