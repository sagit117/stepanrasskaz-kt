package ru.axel.stepanrasskaz.connectors

import io.ktor.application.*
import org.litote.kmongo.KMongo

fun Application.moduleMongoDB() {
    val connectionString = environment.config.propertyOrNull("mongodb.connectionString")?.getString()
    val dataBaseName = environment.config.propertyOrNull("mongodb.dataBaseName")?.getString()

    val mongoClient = connectionString?.let { KMongo.createClient(it) }
    val dataBase = dataBaseName?.let { mongoClient?.getDatabase(it) }
}