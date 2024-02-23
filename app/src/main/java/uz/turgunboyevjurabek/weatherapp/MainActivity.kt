@file:OptIn(ExperimentalWearMaterialApi::class, ExperimentalWearMaterialApi::class,
    ExperimentalCoilApi::class, ExperimentalMaterial3Api::class
)

package uz.turgunboyevjurabek.weatherapp

import android.annotation.SuppressLint
import android.icu.text.LocaleDisplayNames.UiListItem
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.weatherapp.Vm.CurrentWeatherViewModel
import uz.turgunboyevjurabek.weatherapp.Vm.HourlyViewModel
import uz.turgunboyevjurabek.weatherapp.model.madels.current2.Current2
import uz.turgunboyevjurabek.weatherapp.model.madels.hourly.ApiHourly
import uz.turgunboyevjurabek.weatherapp.model.madels.hourly.Hourly
import uz.turgunboyevjurabek.weatherapp.model.madels.hourly.Weather
import uz.turgunboyevjurabek.weatherapp.ui.theme.WeatherAppTheme
import uz.turgunboyevjurabek.weatherapp.utils.Status
import kotlin.math.roundToInt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var data by remember{
                        mutableStateOf(Current2())
                    }
                    var data2 by remember{
                        mutableStateOf(ApiHourly())
                    }

                   androidx.compose.material3.Scaffold() {it->
                       Column(modifier = Modifier
                           .fillMaxSize()
                           .padding(bottom = it.calculateBottomPadding())
                           .padding(top = it.calculateTopPadding())
                       ) {
                           val context = LocalContext.current
                           val lat =40.5409
                           val lon = 70.9483
                           val viewModel=viewModel<CurrentWeatherViewModel>()

                           LaunchedEffect(key1 = true){
                               viewModel.getCurrentWeather(lat, lon).observe(this@MainActivity, Observer {
                                   when(it.status){
                                       Status.LOADING -> {

                                       }
                                       Status.ERROR -> {
                                           Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                       }
                                       Status.SUCCESS -> {
                                           data= it.data!!
                                       }
                                   }
                               })
                           }
                           CustomAppBar(data)
                           val viewModel2=viewModel<HourlyViewModel>()
                           LaunchedEffect(key1 = true){
                               viewModel2.getHourlyData(lat, lon).observe(this@MainActivity, Observer {
                                   when(it.status){
                                       Status.LOADING -> {}
                                       Status.ERROR -> {
                                           Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT)
                                               .show()
                                       }
                                       Status.SUCCESS -> {
                                           Toast.makeText(this@MainActivity, "keldi ", Toast.LENGTH_SHORT).show()
                                           data2= it.data!!
                                       }
                                   }
                               })
                           }

                       }
                   }
                    WithScaffoldSheet(apiHourly =data2 )
                }
            }
        }
    }
}
@Composable
fun CustomAppBar(
    data:Current2
){

    var isNightMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .paint(
                painter = painterResource(id = if (isNightMode) R.drawable.light_bac2 else R.drawable.night_bac),
                true,
                contentScale = ContentScale.Crop
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                )
                if (!data.name.equals(null) || !data.sys?.country.equals(null)){
                    Text(
                        text = data.name.toString(),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                    )
                    Text(
                        text = data.sys?.country.toString(),
                        fontSize = 14.sp,
                        modifier=Modifier
                            .padding(5.dp)
                    )
                }
            }

            CustomSwitch(
                height = 30.dp,
                width = 70.dp,
                circleButtonPadding = 5.dp,
                outerBackgroundOnResource =R.drawable.light_switch,
                outerBackgroundOffResource = R.drawable.night_switch,
                circleBackgroundOnResource = R.drawable.ic_light,
                circleBackgroundOffResource = R.drawable.ic_night,
                stateOn = 1,
                stateOff = 0,
                initialValue = 0
            ) {checked ->
                isNightMode=checked
            }
        }//row

        if (data.weather!=null){
            val imgUrl="https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png"
            Image(
                painter = rememberImagePainter(data= imgUrl,
                    builder = {
                        crossfade(1500)
                    }),
                contentDescription = null,
                Modifier
                    .padding(top = 100.dp)
                    .size(120.dp)
            )
        }

        if(data.main!=null){
            val g= data.main.temp_max?.minus(273.0)?.toInt()
            Text(
                text = data.weather!![0].description.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                modifier=Modifier.padding(top = 20.dp)
            )
            Text(
                text = "$g℃",
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                modifier=Modifier.padding(top = 10.dp)
            )
        }
        


    }
}
@Composable
fun WithScaffoldSheet(apiHourly: ApiHourly) {
    val scaffoldState= rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState=scaffoldState,
        sheetContainerColor = Color.Transparent,
        sheetContent ={
            LazyRow{
                apiHourly.list?.size?.let {
                    items(it){ it->
                        UiListItem(apiHourly.list[it],it)
                    }
                }
            }
        }
    ) {
    }

}

@Composable
fun UiListItem(weather: Hourly,it:Int) {
    Column(modifier = Modifier
        .padding(10.dp),
    ) {
        Card(
            modifier = Modifier
                .height(150.dp)
                .width(80.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.White,
                                Color.Magenta
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = rememberImagePainter(data = "https://openweathermap.org/img/wn/${weather.weather?.get(0)?.icon}@2x.png"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
                val g= weather.main?.tempMax?.minus(273.0)?.toInt()
                Text(text = "${g}°", fontFamily = FontFamily.SansSerif, fontSize = 25.sp, color = Color.White)
                val time=weather.dtTxt.toString()
                val sana=time.substring(0..9)
                val vaqt=time.substring(11..time.lastIndex-3)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = vaqt,fontFamily = FontFamily.SansSerif, fontSize = 15.sp, color = Color.White)
                    Text(text =sana ,fontFamily = FontFamily.SansSerif, fontSize = 13.sp, color = Color.White)
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
                            onCheckedChanged(true)
                        } else {
                            swipeableState.animateTo(stateOff)
                            onCheckedChanged(false)
                        }

                    }
                },
        )
    }


}



