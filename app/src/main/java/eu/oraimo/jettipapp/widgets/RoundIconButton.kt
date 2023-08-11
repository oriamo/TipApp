package eu.oraimo.jettipapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundIconButton(modifier : Modifier = Modifier,
                    imageVector: ImageVector,
                    iconDiscription : String ,
                    onClick: () -> Unit,
                    tint: Color = Color.Black.copy(alpha = 0.8f),
                    backgroundColor: Color = MaterialTheme.colorScheme.background,
                    elevation:Dp = 4.dp ){

    Card(modifier = Modifier
        .padding(6.dp)
        .clickable { onClick.invoke() },

        shape = CircleShape, colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(elevation)
    ){
        Icon(imageVector = imageVector, contentDescription = iconDiscription, tint = tint)
    }

}