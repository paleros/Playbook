package com.peros.playbook.features.game.model

/**
 * A szuresi beallitasokat tartalmazo adatosztaly
 * @param players a kivalasztott jatekosok szama
 * @param time a kivalasztott jatekido
 * @param age a kivalasztott korcsoport
 * @param location a kivalasztott helyszin
 * @param noSupplies ha true, csak olyan jatekokat mutat, amikhez nem kellenek eszkozok
 * @param onlyFavorites ha true, csak a kedvencek jelennek meg
 * @param minRating a kivalasztott minimum ertekeles
 */
data class FilterState(
    val players: Set<NUMBEROFPLAYERS> = emptySet(),
    val time: Set<TIME> = emptySet(),
    val age: Set<AGEGROUP> = emptySet(),
    val location: Set<LOCATION> = emptySet(),
    val noSupplies: Boolean = false,
    val onlyFavorites: Boolean = false,
    val minRating: Int = 1
)