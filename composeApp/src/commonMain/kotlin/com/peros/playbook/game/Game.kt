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
    val shortDescription: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ligula urna, commodo at varius et, fermentum et lorem. "
    val longDescription: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras congue, purus eu sagittis efficitur, velit risus lobortis justo, vitae euismod diam mi a ipsum. Cras vitae ornare orci. Nunc sagittis a arcu sed efficitur."
    val supplies: String = "Some supplies"
    val numberOfPlayers: List<NUMBEROFPLAYERS> = listOf(NUMBEROFPLAYERS.SMALL)
    val time: List<TIME> = listOf(TIME.SHORT, TIME.MEDIUM)
    val ageGroup: List<AGEGROUP> = listOf(AGEGROUP.KIDS)
    val location: List<LOCATION> = listOf(LOCATION.INDOOR)
    var liked: Boolean = false
}