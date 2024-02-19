@file:OptIn(ExperimentalWearMaterialApi::class, ExperimentalWearMaterialApi::class)

package uz.turgunboyevjurabek.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.weatherapp.ui.theme.WeatherAppTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CustomAppBar()
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun UI() {
    CustomAppBar()
}

@Composable
fun CustomAppBar(
    modifier: Modifier =Modifier
        .fillMaxSize()
) {
    Column(modifier=Modifier
        .fillMaxSize()
    ) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",

                )
                Text(
                    text = "Quqon",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                )
                Text(
                    text = "Uzbekistan",
                    fontSize = 14.sp,
                    modifier=Modifier
                        .padding(5.dp)
                )
            }
            val context=LocalContext.current
            CustomSwitch(
                height = 40.dp,
                width = 90.dp,
                circleButtonPadding = 5.dp,
                outerBackgroundOnResource =R.drawable.light_switch,
                outerBackgroundOffResource = R.drawable.night_switch,
                circleBackgroundOnResource = R.drawable.ic_light,
                circleBackgroundOffResource = R.drawable.ic_night,
                stateOn = 1,
                stateOff = 0,
                initialValue = 0
            ) {checked ->
                if (checked){
                    Toast.makeText(context, "true", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "false", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun CustomSwitch(
    height: Dp,
    width: Dp,
    circleButtonPadding: Dp,
    outerBackgroundOnResource: Int,
    outerBackgroundOffResource: Int,
    circleBackgroundOnResource: Int,
    circleBackgroundOffResource: Int,
    stateOn: Int,
    stateOff: Int,
    initialValue: Int,
    onCheckedChanged: (checked: Boolean) -> Unit
) {

    val swipeableState = rememberSwipeableState(
        initialValue = initialValue,
        confirmStateChange = { newState ->
            if (newState == stateOff) {
                onCheckedChanged(false)
            } else {
                onCheckedChanged(true)
            }
            true
        }
    )

    val sizePx = with(LocalDensity.current) { (width - height).toPx() }
    val anchors = mapOf(0f to stateOff, sizePx to stateOn) // Maps anchor points (in px) to states

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(RoundedCornerShape(height))
            .border(1.dp, Color.DarkGray, CircleShape)
            .background(Color.Transparent)
            .then(
                if (swipeableState.currentValue == stateOff) Modifier.paint(
                    painterResource(id = outerBackgroundOffResource),
                    contentScale = ContentScale.FillBounds
                ) else Modifier.paint(
                    painterResource(id = outerBackgroundOnResource),
                    contentScale = ContentScale.FillBounds
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .size(height)
                .padding(circleButtonPadding)
                .clip(RoundedCornerShape(100))
                .then(
                    if (swipeableState.currentValue == stateOff) Modifier.paint(
                        painterResource(id = circleBackgroundOnResource),
                        contentScale = ContentScale.FillBounds
                    )
                    else Modifier.paint(
                        painterResource(id = circleBackgroundOffResource),
                        contentScale = ContentScale.FillBounds
                    )
                )
                .clickable {
                    scope.launch {
                        if (swipeableState.currentValue == stateOff) {
                            swipeableState.animateTo(stateOn)
                        } else {
                            swipeableState.animateTo(stateOff)
                        }

                    }
                },
        )
    }


}



