package com.peros.playbook.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.peros.playbook.database.Games

/**
 * A jatek osztaly, amely tartalmazza a jatek adatait
 * @property name A jatek neve
 * @property shortDescription A jatek rovid leirasa
 * @property longDescription A jatek hosszu leirasa
 * @property supplies A jatekhoz szukseges felszerelesek, szovegesen
 * @property numberOfPlayers A jatekhoz szukseges jatekosok szama, tobb kategoria is lehet
 * @property time A jatekhoz szukseges idotartam, tobb kategoria is lehet
 * @property ageGroup A jatekhoz alkalmas korosztaly, tobb kategoria is lehet
 * @property location A jatek helyszine, tobb kategoria is lehet
 * @property rating A jatek ertekelese
 * @property ratingNumber A jatek ertekeleseinek szama
 * @property liked A jatek kedveltsege
 * @property isRatinged A jatek ertekeltsege
 */
class Game {

    var name: String = "Game"
    var shortDescription: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ligula urna, commodo at varius et, fermentum et lorem. "
    var longDescription: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras congue, purus eu sagittis efficitur, velit risus lobortis justo, vitae euismod diam mi a ipsum. Cras vitae ornare orci. Nunc sagittis a arcu sed efficitur."
    var supplies: String = "Some supplies"
    var numberOfPlayers: List<NUMBEROFPLAYERS> = listOf(NUMBEROFPLAYERS.SMALL)
    var time: List<TIME> = listOf(TIME.SHORT, TIME.MEDIUM)
    var ageGroup: List<AGEGROUP> = listOf(AGEGROUP.KIDS)
    var location: List<LOCATION> = listOf(LOCATION.INDOOR)
    var rating: Int = 0
    var ratingNumber: Int = 0
    var liked by mutableStateOf(false)
    var isRatinged by mutableStateOf(false)

    /**
     * Alapertelmezett konstruktor
     * @param name A jatek neve
     * @param shortDescription A jatek rovid leirasa
     * @param longDescription A jatek hosszu leirasa
     * @param supplies A jatekhoz szukseges felszerelesek, szovegesen
     * @param numberOfPlayers A jatekhoz szukseges jatekosok szama, tobb kategoria is lehet
     * @param time A jatekhoz szukseges idotartam, tobb kategoria is lehet
     * @param ageGroup A jatekhoz alkalmas korosztaly, tobb kategoria is lehet
     * @param location A jatek helyszine, tobb kategoria is lehet
     * @param rating A jatek ertekelese
     * @param ratingNumber A jatek ertekeleseinek szama
     * @param liked A jatek kedveltsege
     */
    constructor(name : String,
                shortDescription: String,
                longDescription: String,
                supplies: String,
                numberOfPlayers: List<NUMBEROFPLAYERS>,
                time: List<TIME>,
                ageGroup: List<AGEGROUP>,
                location: List<LOCATION>,
                rating: Int,
                ratingNumber: Int,
                liked: Boolean,
                isRatinged: Boolean){
        this.name = name
        this.shortDescription = shortDescription
        this.longDescription = longDescription
        this.supplies = supplies
        this.numberOfPlayers = numberOfPlayers
        this.time = time
        this.ageGroup = ageGroup
        this.location = location
        this.rating = rating
        this.ratingNumber = ratingNumber
        this.liked = liked
        this.isRatinged = isRatinged
    }
    constructor()

    /**
     * Game objektumot alakit at GameForFirebase objektumma
     * @return a jatek Firebase-kompatibilis reprezentacioja
     */
    fun gameToFirebase(): GameForFirebase {
        return GameForFirebase(
            name = name,
            shortDescription = shortDescription,
            longDescription = longDescription,
            supplies = supplies,
            numberOfPlayers = numberOfPlayers.joinToString(",") { it.name },
            time = time.joinToString(",") { it.name },
            ageGroup = ageGroup.joinToString(",") { it.name },
            location = location.joinToString(",") { it.name },
            rating = rating.toString(),
            ratingNumber = ratingNumber.toString(),
        )
    }

    /**
     * Game objektumot alakit at Games objektumma
     * @param id az adatbazisban levo jatek egyedi azonositoja
     * @return a jatek adatbazis-kompatibilis reprezentacioja
     */
    fun gameToGames(id: Long): Games {
        return Games(
            id = id,
            name = name,
            shortDescription = shortDescription,
            longDescription = longDescription,
            supplies = supplies,
            numberOfPlayers = numberOfPlayers.joinToString(",") { it.name },
            time = time.joinToString(",") { it.name },
            ageGroup = ageGroup.joinToString(",") { it.name },
            location = location.joinToString(",") { it.name },
            rating = rating.toLong(),
            ratingNumber = ratingNumber.toLong(),
            liked = if (liked) 1 else 0,
            isRatinged = if (isRatinged) 1 else 0
        )
    }
}