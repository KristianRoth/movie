package movie.models

import javax.persistence.*


@Entity
@Table(name = "auditoriums")
class Auditorium(
    val name: String
) {

    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0

    @OneToMany(
        fetch = fetchType.EAGER,
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
