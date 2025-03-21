package com.verimsolution.tailwindplugin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verimsolution.tailwind_prod.TailwindDrawerDirection
import com.verimsolution.tailwind_prod.TailwindDrawerItem
import com.verimsolution.tailwind_prod.TailwindDrawerValue
import com.verimsolution.tailwind_prod.TailwindModalDrawer
import com.verimsolution.tailwind_prod.TailwindScaffold
import com.verimsolution.tailwind_prod.TailwindText
import com.verimsolution.tailwind_prod.rememberTailwindDrawerState
import com.verimsolution.tailwind_prod.tokens.TailwindDrawerTokens
import com.verimsolution.tailwindplugin.ui.themes.TailwindPluginTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TailwindPluginTheme {
                TailwindScaffoldExample()
            }
        }
    }
}

@Composable
fun TailwindScaffoldExample() {
    TailwindScaffold(
        topBar = {
            TailwindTopBar(
                title = "Mon Application",
                backgroundColor = Color(0xFF3B82F6) // bg-blue-500
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                FlowbiteDrawerExample()
            }
        }
    )
}

/**
 * Exemple de TopBar simple.
 */
@Composable
fun TailwindTopBar(
    title: String,
    backgroundColor: Color = Color(0xFFFFFFFF),
    contentColor: Color = Color(0xFF1F2937)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TailwindText(
            text = title,
            style = TextStyle(fontSize = 18.sp, color = contentColor)
        )
    }
}

@Composable
fun FlowbiteDrawerExample() {
    val drawerState = rememberTailwindDrawerState(TailwindDrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isEcommerceExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TailwindModalDrawer(
            drawerState = drawerState,
            direction = TailwindDrawerDirection.Right, // Changez à Right pour tester
            elevation = 0.dp,
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 0.dp),
            header = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TailwindText(
                        text = "MENU",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TailwindDrawerTokens.HeadlineColor
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { scope.launch { drawerState.close() } },
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            drawLine(
                                color = TailwindDrawerTokens.CloseButtonColor,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2f
                            )
                            drawLine(
                                color = TailwindDrawerTokens.CloseButtonColor,
                                start = Offset(size.width, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 2f
                            )
                        }
                    }
                }
            },
            content = {
                TailwindDrawerItem(
                    label = { TailwindText("Dashboard", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)) },
                    selected = false,
                    onClick = { println("Dashboard clicked") },
                    icon = {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            drawCircle(TailwindDrawerTokens.InactiveIconColor, radius = 8f)
                        }
                    }
                )
                TailwindDrawerItem(
                    label = { TailwindText("E-commerce", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)) },
                    selected = false,
                    onClick = { isEcommerceExpanded = !isEcommerceExpanded },
                    icon = {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            drawRect(TailwindDrawerTokens.InactiveIconColor)
                        }
                    },
                    trailing = {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            if (isEcommerceExpanded) {
                                drawLine(Color.Black, Offset(0f, size.height), Offset(size.width / 2, 0f), 2f)
                                drawLine(Color.Black, Offset(size.width / 2, 0f), Offset(size.width, size.height), 2f)
                            } else {
                                drawLine(Color.Black, Offset(0f, 0f), Offset(size.width / 2, size.height), 2f)
                                drawLine(Color.Black, Offset(size.width / 2, size.height), Offset(size.width, 0f), 2f)
                            }
                        }
                    }
                )
                if (isEcommerceExpanded) {
                    Column(modifier = Modifier.padding(start = 44.dp)) {
                        TailwindDrawerItem(
                            label = { TailwindText("Products", style = TextStyle(fontSize = 14.sp)) },
                            selected = false,
                            onClick = { println("Products clicked") }
                        )
                        TailwindDrawerItem(
                            label = { TailwindText("Billing", style = TextStyle(fontSize = 14.sp)) },
                            selected = false,
                            onClick = { println("Billing clicked") }
                        )
                        TailwindDrawerItem(
                            label = { TailwindText("Invoice", style = TextStyle(fontSize = 14.sp)) },
                            selected = false,
                            onClick = { println("Invoice clicked") }
                        )
                    }
                }
            }
        )

        // Bouton pour ouvrir le tiroir
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color(0xFF1D4ED8), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                .clickable { scope.launch { drawerState.open() } }
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            TailwindText(
                text = "Show body scrolling disabled",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            )
        }
    }
}