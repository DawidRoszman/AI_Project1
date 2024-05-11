package eu.dawidroszman.foodlicious

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.io.File

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = androidx.compose.ui.Modifier
            .padding(innerPadding)) {
//            Header()
            Column {
                Content(modifier = Modifier.weight(1f, false))
                ScanBtn(navController)
            }
        }

    }
}

@Composable
fun Header(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.secondary)) {
        ElevatedButton(modifier = Modifier
            .weight(.5f)
            .padding(5.dp), shape = RoundedCornerShape(15),onClick = { /*TODO*/ }) {
            Text(text ="Home", color = MaterialTheme.colorScheme.secondary)

        }
        ElevatedButton(modifier = Modifier
            .weight(.5f)
            .padding(5.dp), shape = RoundedCornerShape(15), onClick = { /*TODO*/ }) {
            Text(text = "History", color = MaterialTheme.colorScheme.secondary)

        }

    }
}
//{
//    "name": "Bilimbi",
//    "type": "Fruit",
//    "season": "Available all year in tropical climates",
//    "origin": "Native to tropical regions of Southeast Asia",
//    "description":"Bilimbi fruits are small, averaging 4 to 10 centimeters in length and 1 to 2 centimeters in diameter, and have an elongated, oblong, cylindrical, to ellipsoidal shape with blunt, slightly tapered ends. The fruit's exterior sometimes features five distinct sides created by shallow ribs, and the stem-end is capped with a thin, dark brown, and brittle star-shaped calyx. The fruit’s skin is thin, being easily damaged, bruised, or punctured, and has a smooth, glossy, taut, and waxy feel. The skin ripens from dark green to shades of light green, yellow-green, ivory, to white when ripe. Underneath the surface, the flesh is crisp, firm, aqueous, and pale green when young, lightening in color, and becoming softer and more jelly-like as it ages. When sliced in half, the fruits reveal a five-pointed star in the center of the flesh and contain 6 to 7 tiny, light brown seeds. Bilimbi fruits release a refreshing aroma and are edible raw or cooked. When raw, the fruits taste sour, acidic, tart, and tangy.",
//    "fact": "Bilimbi, botanically classified as Averrhoa bilimbi, is a tropical species belonging to the Oxalidaceae family. The small fruits develop on evergreen trees reaching 10 to 15 meters in height and grow in hanging clusters directly from the trunk or dangling stems. Bilimbi fruits typically mature 50 to 60 days after flowering, and each tree can produce 45 to 50 kilograms of fruit per year. The fruits are carefully picked by hand in clusters or individually from the tree, and some ripe versions are also collected from the ground. Bilimbi is the Hindi name for the species, and the fruits are prevalent in India as medicinal and culinary ingredients. The fruits are also known by several descriptors worldwide, including Tree Cucumbers, Tree Sorrel, and Pickle Tree in English-speaking regions, Billing-Billing, Belimbing Buloh, Belimbing Asam, and B’Ling in Malaysia, Blimbing, Blimbing Wuluh, Balimbing, Belimbing Besu in Indonesia, Taling Pling or Kaling Pring in Thailand, Pias, Camias, and Kamias in the Philippines, and Khe Tay in Vietnam. Bilimbi is favored for its acidic, sour nature and is used for its refreshing, tart, and tangy nuances in a wide array of fresh and cooked culinary preparations. The fruits are also extensively used in natural medicines throughout Southeast Asia as an anti-inflammatory.",
//    "nutrients": "100g of Bilimbi contains: \nCalories: 25 \nProtein: 1g \nFat: 1g \nFibre: 1g \nCarbohydrates: 1.9g \nCalcium: 15mg \nIron: 1mg"
//}
@Composable
fun Content(modifier: Modifier){
    val context = LocalContext.current
    val path = context.getExternalFilesDir(null)!!.absolutePath
    val imagePath = "$path/image.jpg"
    val photo = BitmapFactory.decodeFile(imagePath)
    File(imagePath).deleteOnExit()
    if (photo == null) {
        Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
            Text(text = "No Photo")
        }

        return
    }
    Column(modifier = modifier
        .verticalScroll(rememberScrollState())
    ) {

        Image(bitmap = photo.asImageBitmap(),
            contentDescription = "Photo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Bilimbi", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Item("Origin", "Native to tropical regions of Southeast Asia")
            Item(title = "Description", description = "Bilimbi fruits are small, averaging 4 to 10 centimeters in length and 1 to 2 centimeters in diameter, and have an elongated, oblong, cylindrical, to ellipsoidal shape with blunt, slightly tapered ends. The fruit's exterior sometimes features five distinct sides created by shallow ribs, and the stem-end is capped with a thin, dark brown, and brittle star-shaped calyx. The fruit’s skin is thin, being easily damaged, bruised, or punctured, and has a smooth, glossy, taut, and waxy feel. The skin ripens from dark green to shades of light green, yellow-green, ivory, to white when ripe. Underneath the surface, the flesh is crisp, firm, aqueous, and pale green when young, lightening in color, and becoming softer and more jelly-like as it ages. When sliced in half, the fruits reveal a five-pointed star in the center of the flesh and contain 6 to 7 tiny, light brown seeds. Bilimbi fruits release a refreshing aroma and are edible raw or cooked. When raw, the fruits taste sour, acidic, tart, and tangy.")
            Item(title = "Facts", description = "Bilimbi, botanically classified as Averrhoa bilimbi, is a tropical species belonging to the Oxalidaceae family. The small fruits develop on evergreen trees reaching 10 to 15 meters in height and grow in hanging clusters directly from the trunk or dangling stems. Bilimbi fruits typically mature 50 to 60 days after flowering, and each tree can produce 45 to 50 kilograms of fruit per year. The fruits are carefully picked by hand in clusters or individually from the tree, and some ripe versions are also collected from the ground. Bilimbi is the Hindi name for the species, and the fruits are prevalent in India as medicinal and culinary ingredients. The fruits are also known by several descriptors worldwide, including Tree Cucumbers, Tree Sorrel, and Pickle Tree in English-speaking regions, Billing-Billing, Belimbing Buloh, Belimbing Asam, and B’Ling in Malaysia, Blimbing, Blimbing Wuluh, Balimbing, Belimbing Besu in Indonesia, Taling Pling or Kaling Pring in Thailand, Pias, Camias, and Kamias in the Philippines, and Khe Tay in Vietnam. Bilimbi is favored for its acidic, sour nature and is used for its refreshing, tart, and tangy nuances in a wide array of fresh and cooked culinary preparations. The fruits are also extensively used in natural medicines throughout Southeast Asia as an anti-inflammatory.")
            Item(title = "Nutrients", description = "100g of Bilimbi contains: \nCalories: 25 \nProtein: 1g \nFat: 1g \nFibre: 1g \nCarbohydrates: 1.9g \nCalcium: 15mg \nIron: 1mg")
        }
    }
}

@Composable
fun Item(title: String, description: String){
    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Card(modifier = Modifier.padding(5.dp), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondary
    ) ) {
        Text(modifier = Modifier.padding(10.dp), text = description)
    }
    Spacer(modifier = Modifier.padding(10.dp))
}

@Composable
fun ScanBtn(navController: NavController){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        FloatingActionButton(modifier = Modifier
            .width(250.dp)
            .padding(20.dp), containerColor = MaterialTheme.colorScheme.secondary , onClick =  {navController.navigate(Screen.CameraScreen.route)}
                  ) {
            Text(modifier = Modifier.padding(5.dp), text = "Press to scan", fontSize = 28.sp)

        }
    }
}
