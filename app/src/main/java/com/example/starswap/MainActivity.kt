package com.example.starswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AddClothesScreen()
            }
        }
    }
}

@Preview(device = "id:pixel_8")
@Composable
fun AddClothesScreen() {
    var description by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.BOYS) }
    var sliderPosition by remember { mutableFloatStateOf(0.5f) }
    var selectedClothingType by remember { mutableStateOf<ClothingType?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top bar with back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        // Placeholder for clothing icon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "T",
                    fontSize = 40.sp,
                    color = Color.Gray
                )

                // Add item circle
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Description field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Description") },
            singleLine = false,
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Gender selection
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.LightGray.copy(alpha = 0.2f))
        ) {
            GenderOption(
                title = "Girls",
                isSelected = selectedGender == Gender.GIRLS,
                onClick = { selectedGender = Gender.GIRLS },
                modifier = Modifier.weight(1f)
            )
            GenderOption(
                title = "Boys",
                isSelected = selectedGender == Gender.BOYS,
                onClick = { selectedGender = Gender.BOYS },
                modifier = Modifier.weight(1f)
            )
            GenderOption(
                title = "Both",
                isSelected = selectedGender == Gender.BOTH,
                onClick = { selectedGender = Gender.BOTH },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Size slider
        Text(
            text = "Size",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF673AB7),
                activeTrackColor = Color(0xFF673AB7)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Clothing type grid
        ClothingTypeGrid(
            onClothingTypeSelected = { selectedClothingType = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Add item button
        Button(
            onClick = { /* Handle add item */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            )
        ) {
            Text("Add item to the shop", color = Color.White)
        }
    }
}

@Composable
fun GenderOption(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color(0xFFE6E0EC) else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xFF673AB7),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = title,
                color = if (isSelected) Color(0xFF673AB7) else Color.Gray
            )
        }
    }
}

@Composable
fun ClothingTypeGrid(onClothingTypeSelected: (ClothingType) -> Unit) {
    val clothingTypes = listOf(
        ClothingType.HOODIES,
        ClothingType.TSHIRTS,
        ClothingType.SHOES,
        ClothingType.SHORTS,
        ClothingType.DRESSES,
        ClothingType.PANTS,
        ClothingType.HEADWEAR,
        ClothingType.ACCESSORIES
    )

    var selectedType by remember { mutableStateOf<ClothingType?>(ClothingType.TSHIRTS) }

    Column {
        // Split into 2 rows with 4 items each
        for (i in clothingTypes.indices step 4) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in i until minOf(i + 4, clothingTypes.size)) {
                    val type = clothingTypes[j]
                    val isSelected = selectedType == type

                    // Get first letter of the clothing type as a simple "icon"
                    val letter = type.toString().substring(0, 1)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(80.dp)
                            .clickable {
                                selectedType = type
                                onClothingTypeSelected(type)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected) {
                                        when (type) {
                                            ClothingType.TSHIRTS -> Color(0xFF4CAF50)
                                            else -> Color.LightGray.copy(alpha = 0.3f)
                                        }
                                    } else {
                                        Color.LightGray.copy(alpha = 0.3f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letter,
                                color = if (isSelected && type == ClothingType.TSHIRTS)
                                    Color.White else Color.Gray,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = type.toString().lowercase().replaceFirstChar { it.uppercase() },
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

enum class Gender {
    GIRLS, BOYS, BOTH
}

enum class ClothingType {
    HOODIES, TSHIRTS, SHOES, SHORTS, DRESSES, PANTS, HEADWEAR, ACCESSORIES
}
