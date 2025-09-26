package com.peros.playbook.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

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
 * @property liked A jatek kedveltsege
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
    var liked by mutableStateOf(false)

    /**
     * Alapertelmezett konstruktor
     */
    constructor(name : String,
                shortDescription: String,
                longDescription: String,
                supplies: String,
                numberOfPlayers: List<NUMBEROFPLAYERS>,
                time: List<TIME>,
                ageGroup: List<AGEGROUP>,
                location: List<LOCATION>,
                liked: Boolean) {
        this.name = name
        this.shortDescription = shortDescription
        this.longDescription = longDescription
        this.supplies = supplies
        this.numberOfPlayers = numberOfPlayers
        this.time = time
        this.ageGroup = ageGroup
        this.location = location
        this.liked = liked
    }
    constructor()
}