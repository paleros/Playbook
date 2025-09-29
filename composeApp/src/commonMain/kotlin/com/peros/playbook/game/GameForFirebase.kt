package com.peros.playbook.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.peros.playbook.database.convertAgeGroup
import com.peros.playbook.database.convertLocation
import com.peros.playbook.database.convertNumberOfPlayers
import com.peros.playbook.database.convertTime

/**
 * A jatek osztaly, amely tartalmazza a jatek adatait, ez kimondottan a Firebase-hez van optimalizalva
 * @property name A jatek neve
 * @property shortDescription A jatek rovid leirasa
 * @property longDescription A jatek hosszu leirasa
 * @property supplies A jatekhoz szukseges felszerelesek, szovegesen
 * @property numberOfPlayers A jatekhoz szukseges jatekosok szama, tobb kategoria is lehet
 * @property time A jatekhoz szukseges idotartam, tobb kategoria is lehet
 * @property ageGroup A jatekhoz alkalmas korosztaly, tobb kategoria is lehet
 * @property location A jatek helyszine, tobb kategoria is lehet
 */
class GameForFirebase {

    var name: String = "Game"
    var shortDescription: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ligula urna, commodo at varius et, fermentum et lorem. "
    var longDescription: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras congue, purus eu sagittis efficitur, velit risus lobortis justo, vitae euismod diam mi a ipsum. Cras vitae ornare orci. Nunc sagittis a arcu sed efficitur."
    var supplies: String = "Some supplies"
    var numberOfPlayers: String = "SMALL"
    var time: String = "SHORT, MEDIUM"
    var ageGroup: String = "KIDS"
    var location: String = "INDOOR"

    /**
     * Alapertelmezett konstruktor
     */
    constructor(name : String,
                shortDescription: String,
                longDescription: String,
                supplies: String,
                numberOfPlayers: String,
                time: String,
                ageGroup: String,
                location: String) {
        this.name = name
        this.shortDescription = shortDescription
        this.longDescription = longDescription
        this.supplies = supplies
        this.numberOfPlayers = numberOfPlayers
        this.time = time
        this.ageGroup = ageGroup
        this.location = location
    }

    /**
     * GameForFirebase objektumot alakit at Game objektumma
     */
    fun firebaseToGame(): Game {
        return Game(
            name = this.name,
            shortDescription = this.shortDescription,
            longDescription = this.longDescription,
            supplies = this.supplies,
            numberOfPlayers = convertNumberOfPlayers(this.numberOfPlayers.split(",")),
            time = convertTime(this.time.split(",")),
            ageGroup = convertAgeGroup(this.ageGroup.split(",")),
            location = convertLocation(this.location.split(",")),
            liked = false
        )
    }
}