package com.peros.playbook.game

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
    val name: String = "Game"
    val shortDescription: String = "A game"
    val longDescription: String = "This is a game"
    val supplies: String = "Some supplies"
    val numberOfPlayers: List<Enum<NUMBEROFPLAYERS>> = listOf(NUMBEROFPLAYERS.SMALL)
    val time: List<Enum<TIME>> = listOf(TIME.SHORT)
    val ageGroup: List<Enum<AGEGROUP>> = listOf(AGEGROUP.KIDS)
    val location: List<Enum<LOCATION>> = listOf(LOCATION.INDOOR)
    val liked: Boolean = false

}