package com.peros.playbook.game

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
 * @property rating A jatek ertekelese
 * @property ratingNumber A jatek ertekeleseinek szama
 */
class GameForFirebase {

    var name: String = "Game"
    var shortDescription: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ligula urna, commodo at varius et, fermentum et lorem. "
    var longDescription: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras congue, purus eu sagittis efficitur, velit risus lobortis justo, vitae euismod diam mi a ipsum. Cras vitae ornare orci. Nunc sagittis a arcu sed efficitur."
    var supplies: String = "Some supplies"
    var numberOfPlayers: String = "SMALL"
    var time: String = "SHORT,MEDIUM"
    var ageGroup: String = "KIDS"
    var location: String = "INDOOR"
    var rating: String = "5.0"
    var ratingNumber: String = "1"

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
     */
    constructor(name : String,
                shortDescription: String,
                longDescription: String,
                supplies: String,
                numberOfPlayers: String,
                time: String,
                ageGroup: String,
                location: String,
                rating: String,
                ratingNumber: String,
        ) {
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
    }

    /**
     * Visszaadja egy Game peldany adataikent
     * @return a Game peldany
     */
    fun getGame(): Game {
        val game = Game()
        game.name = name
        game.shortDescription = shortDescription
        game.longDescription = longDescription
        game.supplies = supplies
        game.numberOfPlayers = numberOfPlayers.split(",").map { NUMBEROFPLAYERS.valueOf(it) }
        game.time = time.split(",").map { TIME.valueOf(it) }
        game.ageGroup = ageGroup.split(",").map { AGEGROUP.valueOf(it) }
        game.location = location.split(",").map { LOCATION.valueOf(it) }
        game.rating = rating.toInt()
        game.ratingNumber = ratingNumber.toInt()
        return game
    }
}