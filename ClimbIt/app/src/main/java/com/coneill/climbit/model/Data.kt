package com.coneill.climbit.model

import java.time.LocalDate

/**
 * Simple interface to allow projects and climbs to be lumped together in certain functions.
 * At some point should more clearly enforce requirements for base climb. (e.g. has name, grade, crag)
 */
interface BaseClimb

/**
 * Data class used to represent a climb on the projects board.
 * @param name  The name of the climb
 * @param grade The grade of the climb (in the NZ Ewbank grading system)
 * @param crag  The crag at which the climb is located.
 */
data class Project(
    val name: String,
    val grade: Int,
    val crag: String
) : BaseClimb

/**
 * Data class used to represent a climb.
 * @param name  The name of the climb
 * @param grade The grade of the climb (in the NZ Ewbank grading system)
 * @param crag  The crag at which the climb is located.
 * @param style Style of ascent. Should be one of the below ascent styles.
 * @param stars Star rating for the climb. Should be an int between 0 and 3 inclusive
 * @param date  The date the route was climbed on
 */
class Climb(
    val name: String,
    val grade: Int,
    val crag: String,
    val style: String,
    val stars: Int = 0,
    val date: LocalDate? = null
) : BaseClimb {
    companion object {
        const val REDPOINT = "Redpoint"
        const val FLASH = "Flash"
        const val ONSIGHT = "Onsight"

        val ascentTypes = listOf(REDPOINT, FLASH, ONSIGHT)
    }
}

