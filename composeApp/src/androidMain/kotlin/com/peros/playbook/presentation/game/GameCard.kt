package com.peros.playbook.presentation.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peros.playbook.game.Game

/**
 * Egy jatek elem megjelenitese
 * @param game a jatek
 * @param onClick a kattintas esemeny
 */
@Composable
fun GameCard(game: Game,
               //onClick: () -> Unit
) {

    val defaultColor = Color.White

    Card(
    modifier = Modifier
    .fillMaxWidth()
    .padding(5.dp)
    //.clickable {onClick()},                   //TODO kattinthatosag
    ,shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Blue)    //TODO színt automatikusra
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox, //TODO ikont automatikusra
                contentDescription = null,
                tint = defaultColor,
                modifier = Modifier.run { size(40.dp) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = game.name, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = defaultColor
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = game.shortDescription,
                    fontSize = 14.sp,
                    color = defaultColor
                )
            }
            Spacer(modifier = Modifier.width(16.dp).weight(1f))

            //TODO kedveles gomb letrehozasa
            if (game.liked) { //Ez nem jo, mert nem gomb
                Icon(
                    imageVector = Icons.Default.Favorite, //TODO automatikus frissites
                    contentDescription = null,
                    tint = defaultColor,
                    modifier = Modifier.run { size(30.dp) }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder ,
                    contentDescription = null,
                    tint = defaultColor,
                    modifier = Modifier.run { size(30.dp) }
                )
            }
        }
    }
}

@Preview
@Composable
fun GameCardPreview() {
    GameCard( Game())
}
