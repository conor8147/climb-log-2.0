package com.coneill.climbit.model

import java.util.*

/**
 * Data class used to represent a climb on the projects board.
 * @param name  The name of the climb
 * @param grade The grade of the climb (in the NZ Ewbank grading system)
 * @param crag  The crag at which the climb is located.
 */
data class Project(
    val name: String,
    val grade: String,
    val crag: String
)

/**
 * Data class used to represent a climb.
 * @param name  The name of the climb
 * @param grade The grade of the climb (in the NZ Ewbank grading system)
 * @param crag  The crag at which the climb is located.
 * @param notes Optional notes on the climb provided by the user
 * @param stars Star rating for the climb. Should be an int between 0 and 3 inclusive
 * @param date  The date the route was climbed on
 */
data class Climb(
    val name: String,
    val grade: String,
    val crag: String,
    val notes: String,
    val stars: Int,
    val date: Date
)