package com.coneill.climbit.model

import java.time.LocalDate

/**
 * Non-persistent data storage for ClimbIt.
 */
class Singleton {
    companion object {

        // Mutable list of projects on the users project board.
        val projects = mutableListOf(
            Project("Moonshine", "23", "Mangaokewa"),
            Project("Rubble", "27", "The Cave"),
            Project("Liposuction", "23", "Britten Crag"),
            Project("Lunge Starter", "24", "Hospital Flat")
        )

        // Mutable map of grades to climbs for the user's logbook
        val climbs = mutableListOf(
                Climb(name="Moonshine", grade="23", crag="Mangaokewa", stars=1, style="Redpoint"),
                Climb(name="Liposuction", grade="23", crag="Britten Crag", stars=3, style="Redpoint"),
                Climb("Lunge Starter", "24", "Mangaokewa", style="Redpoint")
        )

        /**
         * Add new project to the top of the projectsList
         * @param name  Must not be empty or null
         * @param grade Must be between 0 and 45 (inclusive)
         * @param crag  Can be empty, but not null
         */
        fun addProject(name: String, grade: String, crag: String="") {
            projects.add(0, Project(name, grade, crag))
        }

        /**
         * Add new climb to projectsList
         * @param name: String      Must not be empty or null
         * @param style: String     Style of ascent. Should be one of the below ascent styles.
         * @param grade: String     Must be between 0 and 45 (inclusive)
         * @param crag: String      Can be empty, but not null
         * @param stars: Int        Must be between 0 and 3 (inclusive)
         * @param date: Date
         */
        fun addClimb(name: String, style: String, grade: String, crag: String, stars: Int, date: LocalDate) {
            // TODO: check ascent style is one of constants
            climbs.add(Climb(name, grade, crag, style, stars, date))

        }
    }
}