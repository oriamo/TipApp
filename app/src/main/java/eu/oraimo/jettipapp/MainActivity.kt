package eu.oraimo.jettipapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.oraimo.jettipapp.components.InputField
import eu.oraimo.jettipapp.ui.theme.JetTipAppTheme
import eu.oraimo.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Myapp {
                TopHeader()
                MainContent()
            }
        }
    }
}
@Composable
fun Myapp(content: @Composable () -> Unit ){
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column() {
            content()
        }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double = 133.0){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
        .padding(5.dp), color = Color(0xFFE9D7F7)) {
        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total per person", style = MaterialTheme.typography.headlineMedium)
            Text(text = "$$total", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
            Text(text = "chnage 2")
            Text(text = "youtube")
            Text(text = "this line will be merged")

        }
    }

}
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent(){

    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(), shape = CircleShape.copy(all = CornerSize(8.dp)), border = BorderStroke(width = 1.dp, color = Color.LightGray)) {
        Column {
                BillForm(){

                }
        }
        
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MoneyTextField(
    totalBillState: MutableState<String>,
    validState: Boolean,
    keyboardController: SoftwareKeyboardController?,
    onValChange : (String) -> Unit = {}
) {
    InputField(valueState = totalBillState,
        labelId = "enter bill",
        enabled = true,
        isSingleLine = true,
        onAction = KeyboardActions {
            if (!validState) return@KeyboardActions
            onValChange(totalBillState.value.trim())
            keyboardController?.hide()
        })
}
@Preview
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier : Modifier = Modifier, onValChange : (String) -> Unit = {}){
    val totalBillState = remember {
        mutableStateOf("")

    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val splitState = remember{
        mutableStateOf(1)
    }
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState.value * 100).toInt()
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    TopHeader(totalPerPersonState.value)
    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(), shape = CircleShape.copy(all = CornerSize(8.dp)), border = BorderStroke(width = 1.dp, color = Color.LightGray)) {
        Column(modifier = Modifier.padding(6.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            MoneyTextField(totalBillState, validState, keyboardController, onValChange)
            if (validState){
                Row(modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Start) {
                    Text(text = "Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            iconDiscription = "minus button" ,
                            onClick = { if (splitState.value > 1) splitState.value--
                                        totalPerPersonState.value = calculateTotalPerperson(splitState.value,totalBillState.value.toDouble(), tipAmountState.value)})
                        Text(text = "${splitState.value}", modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp))
                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            iconDiscription = "plus button",
                            onClick = { splitState.value++
                                totalPerPersonState.value = calculateTotalPerperson(splitState.value,totalBillState.value.toDouble(), tipAmountState.value)})
                    }

                    
                }
                Row(modifier = Modifier
                    .padding(3.dp)
                    .padding(top = 10.dp)
                    , horizontalArrangement = Arrangement.Start) {
                    Text(text = "tip", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(120.dp))
                    Text(text = "$${tipAmountState.value}")
                }
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 10.dp)) {
                    Text(text = "$tipPercentage%")
                    Spacer(modifier = Modifier.height(14.dp))
                    //slider

                    Slider(value = sliderPositionState.value,
                        onValueChange = { sliderPositionState.value = it
                            tipAmountState.value = calculateTotalTip(totalBill = totalBillState.value.toDouble(), tipPercentage = (sliderPositionState.value *100).toInt())
                            totalPerPersonState.value = calculateTotalPerperson(splitState.value,totalBillState.value.toDouble(), tipAmountState.value)
                                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        steps = 5)
                    
                }
            }else {
                Box() {}
            }
        }

    }

}

fun calculateTotalTip(totalBill: Double, tipPercentage: Int): Double {
    return  if(totalBill > 1 && totalBill.toString().isNotEmpty())
            (totalBill*tipPercentage) /100  else 0.0


}
fun calculateTotalPerperson(split : Int, totalbill : Double, tip : Double) : Double{
    val totalPlusTip = totalbill + tip
    return totalPlusTip/split
}

@Composable
fun GreetingPreview() {
    JetTipAppTheme {
        Myapp {

        }
    }
}