package com.coneill.climbit.model

import java.time.LocalDate

/**
 * Non-persistent data storage for ClimbIt.
 */
object Model {
    // Mutable list of projects on the users project board.
    val projects = mutableListOf(
        Project("Moonshine", 23, "Mangaokewa"),
        Project("Rubble", 27, "The Cave"),
        Project("Liposuction", 23, "Britten Crag"),
        Project("Lunge Starter", 24, "Hospital Flat")
    )

    // Mutable map of grades to climbs for the user's logbook
    val climbs = mutableMapOf(
        Pair(21, mutableListOf(
            Climb("Uhaul", 21, "Britten Crags", "Onsight", 1)
        )),
        Pair(22, mutableListOf(
            Climb("Storming the Gates of Troy", 22, "Mangaokewa", "Onsight", 2)
        )),
        Pair(23, mutableListOf(
            Climb(name="Moonshine", grade=23, crag="Mangaokewa", stars=1, style="Redpoint"),
            Climb(name="Liposuction", grade=23, crag="Britten Crag", stars=3, style="Redpoint")
        )),
        Pair(24, mutableListOf(
            Climb("Lunge Starter", 24, "Wanaka", style="Redpoint")
        )),
        Pair(26, mutableListOf(
            Climb("Rubble", 26, "The Cave", "Redpoint", 1)
        ))
    )

    val grades: MutableList<Int> = climbs.keys.toMutableList()

    /**
     * Add new project to the top of the projectsList
     * @param name  Must not be empty or null
     * @param grade Must be between 0 and 45 (inclusive)
     * @param crag  Can be empty, but not null
     */
    fun addProject(name: String, grade: Int, crag: String="") {
        projects.add(0, Project(name, grade, crag))
    }

    /**
     * Add new climb to projectsList
     * Should always ensure that the grades list is kept up to date
     */
    fun addClimb(name: String, style: String, grade: Int, crag: String, stars: Int, date: LocalDate) {
        // TODO: check ascent style is one of constants
        val newClimb = Climb(name, grade, crag, style, stars, date)

        if (climbs.containsKey(grade)) {
                climbs[grade]?.add(newClimb)
        } else {
            climbs[grade] = mutableListOf(newClimb)
            grades.add(grade)
            grades.sort()
        }

    }


}