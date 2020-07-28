package movie.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column
import javax.persistence.Table
import javax.persistence.OneToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.CascadeType
import javax.persistence.JoinColumn
import javax.persistence.FetchType


@Entity
@Table(name = "sreenings")
class Screening(
    val movieName: String,
    val startTime: Int,
    val endTime: Int,
    @OneToOne(name = "auditorium_id", cascade = [(CascadeType.ALL)])
    val auditorium: Auditorium
) {

    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0


}



@Entity
@Table(name = "resevations")
class Resevation(
    @OneToOne(name = "screening_id")
    val screening: Screening,
    @OneToOne(name = "seat_id")
    val seat: Seat
) { 
    @Id
    @GeneratedValue(stretegy = GenerationType.AUTO)
    val id: Int = 0



}
