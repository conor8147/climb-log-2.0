package com.coneill.climbit.model

import java.time.LocalDate

/**
 * Non-persistent data storage for ClimbIt.
 */
object Model {
    // Mutable list of projects on the users project board.
    val projects = mutableListOf(
        Project("Moonshine", "23", "Mangaokewa"),
        Project("Rubble", "27", "The Cave"),
        Project("Liposuction", "23", "Britten Crag"),
        Project("Lunge Starter", "24", "Hospital Flat")
    )

    // Mutable map of grades to climbs for the user's logbook
    val climbs = mutableMapOf(
        Pair(23, mutableListOf(
            Climb(name="Moonshine", grade="23", crag="Mangaokewa", stars=1, style="Redpoint"),
            Climb(name="Liposuction", grade="23", crag="Britten Crag", stars=3, style="Redpoint")
        )),
        Pair(24, mutableListOf(
            Climb("Lunge Starter", "24", "Wanaka", style="Redpoint")
        ))
    )

    val grades = mutableListOf(23, 24)

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
     * Should always ensure that the grades list is kept up to date
     */
    fun addClimb(name: String, style: String, grade: String, crag: String, stars: Int, date: LocalDate) {
        // TODO: check ascent style is one of constants
        val newClimb = Climb(name, grade, crag, style, stars, date)
        val intGrade = grade.toInt()

        if (climbs.containsKey(intGrade)) {
                climbs[intGrade]?.add(newClimb)
        } else {
            climbs[intGrade] = mutableListOf(newClimb)
            grades.add(intGrade)
            grades.sort()
        }

    }


}