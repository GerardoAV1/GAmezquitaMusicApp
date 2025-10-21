package com.example1.gamezquitamusicapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example1.gamezquitamusicapp.models.Albums
import com.example1.gamezquitamusicapp.ui.theme.GAmezquitaMusicAppTheme


@Composable
fun RecentlyPlayedCard(albums: Albums){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically


    ) {
        AsyncImage(
            model = albums.image,
            contentDescription = albums.title,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))

        )
        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = albums.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                minLines = 1,
                overflow = TextOverflow.Ellipsis

            )
            Text(
                text = "${albums.artist} • Popular Song",
                fontSize = 14.sp,
                color = Color.Black,
                minLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options",
            tint = Color.Gray
        )

    }
}



@Preview
@Composable
fun RecentlyPlayedCardPreview(){
    GAmezquitaMusicAppTheme {
        RecentlyPlayedCard(
            albums = Albums(
                title = "Tales of Ithiria",
                artist = "Haggard",
                description = "Popular song",
                image = "",
                id = "682243ecf60db4caa642a48c",
        )
        )
    }
}