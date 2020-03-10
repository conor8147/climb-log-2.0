package com.coneill.climbit.model

import org.json.JSONObject

class Singleton {
    companion object {
        val projects = mutableListOf(
            Project("Moonshine", "23", "Mangaokewa"),
            Project("Rubble", "27", "The Cave"),
            Project("Liposuction", "23", "Britten Crag"),
            Project("Lunge Starter", "24", "Hospital Flat")
        )

        /**
         * Add new project to the top of the projectsList
         * @param proj
         */
        fun addProject(name: String, grade: String, crag: String="") {
            projects.add(0, Project(name, grade, crag))
        }
    }
}