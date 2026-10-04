package com.example.littlelemon

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.data.User
import com.example.littlelemon.data.UserPreferences
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Onboarding(prefs: UserPreferences, onRegistered: () -> Unit) {
    val context = LocalContext.current
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .imePadding(),
    ) {
        Header()
        Box(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(LittleLemonColor.green),
            contentAlignment = Alignment.Center,
        ) {
            Text("Let's get to know you", color = Color.White, fontSize = 24.sp)
        }
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Text(
                "Personal information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 24.dp),
            )
            LabeledField("First name", firstName, { firstName = it })
            LabeledField("Last name", lastName, { lastName = it })
            LabeledField("Email", email, { email = it }, keyboardType = KeyboardType.Email)
        }
        PrimaryButton(
            text = "Register",
            onClick = {
                val user = User(firstName.trim(), lastName.trim(), email.trim())
                when {
                    user.firstName.isBlank() || user.lastName.isBlank() || user.email.isBlank() ->
                        Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_LONG).show()
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches() ->
                        Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_LONG).show()
                    else -> {
                        prefs.save(user)
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                        onRegistered()
                    }
                }
            },
            modifier = Modifier.padding(16.dp),
        )
        Spacer(Modifier.height(8.dp))
    }
}
