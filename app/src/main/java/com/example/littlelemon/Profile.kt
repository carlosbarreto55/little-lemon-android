package com.example.littlelemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.data.UserPreferences

@Composable
fun Profile(prefs: UserPreferences, onLogout: () -> Unit) {
    val user = remember { prefs.user }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding(),
    ) {
        Header()
        Column(Modifier.weight(1f).padding(16.dp)) {
            Text(
                "Personal information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 24.dp),
            )
            LabeledField("First name", user.firstName, {}, readOnly = true)
            LabeledField("Last name", user.lastName, {}, readOnly = true)
            LabeledField("Email", user.email, {}, readOnly = true)
            Spacer(Modifier.weight(1f))
            PrimaryButton(
                text = "Log out",
                onClick = {
                    prefs.clear()
                    onLogout()
                },
            )
        }
    }
}
