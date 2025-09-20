package com.peros.playbook.presentation.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gearlistapp.ui.theme.AppBlue
import com.example.gearlistapp.ui.theme.AppDarkGreen
import com.example.gearlistapp.ui.theme.AppGray
import com.example.gearlistapp.ui.theme.AppYellow
import com.example.gearlistapp.ui.theme.BaseWhite
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.game.TIME
import com.peros.playbook.presentation.ui.FavoriteButton
import com.peros.playbook.theme.CrowIcon
import com.peros.playbook.theme.FrogIcon
import com.peros.playbook.theme.HorseIcon
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Egy jatek elem megjelenitese
 * @param game a jatek
 * @param onClick a kattintas esemeny
 */
@Composable
fun GameCard(game: Game,
               //onClick: () -> Unit
) {

    val defaultTextColor = BaseWhite

    /**
     * A Korosztaly alapjan beallitja a kartyat szinet
     */
     val backgroundColor = if (game.ageGroup[0] == AGEGROUP.KIDS) {
        AppYellow
    } else if (game.ageGroup[0] == AGEGROUP.TEENS) {
        AppDarkGreen
    } else if (game.ageGroup[0] == AGEGROUP.ADULTS) {
        AppGray
    } else {
        AppBlue
    }

    Card(
    modifier = Modifier
    .fillMaxWidth()
    .padding(5.dp)
    //.clickable {onClick()},                   //TODO kattinthatosag
    ,shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GameIcon(game = game,
                color = defaultTextColor)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = game.name, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = defaultTextColor
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = game.shortDescription,
                    fontSize = 14.sp,
                    color = defaultTextColor
                )
            }
            Spacer(modifier = Modifier.width(16.dp).weight(1f))

            FavoriteButton(
                isInitiallyFavorite = false,
                onFavoriteChange = { isFav ->
                    game.liked = isFav
                    println("Favorite state changed: $isFav")
                }
            )
        }
    }
}

/**
 * A jatek ikonjat jeleniti meg a jatek idotartama alapjan
 * @param game a jatek
 * @param color az ikon szine
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun GameIcon(game: Game,
             color: Color = BaseWhite) {
    val icon = when (game.time[0]) {
        TIME.SHORT -> FrogIcon
        TIME.MEDIUM -> CrowIcon
        TIME.LONG -> HorseIcon
        else -> {FrogIcon}
    }

    Icon(
        painter = icon,
        contentDescription = null,
        tint = color,
        modifier = Modifier.size(40.dp)
    )
}

/**
 * Elonezet a GameCard-hoz
 */
@Preview
@Composable
fun GameCardPreview() {
    GameCard( Game())
}
