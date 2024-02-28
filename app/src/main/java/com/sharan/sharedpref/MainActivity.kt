package com.sharan.sharedpref

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sharan.sharedpref.ui.theme.SharedPrefTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedPrefTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SharedPref()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedPref() {

    val context : Context = LocalContext.current
    var share = Share(context)

    var name = remember { mutableStateOf("")}
    var age = remember { mutableStateOf("")}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = name.value,
            onValueChange = {name.value = it}
        )
        TextField(value = age.value,
            onValueChange = {age.value = it}
        )

        Button(onClick = {
            share.keyName = name.value
            share.keyAge = age.value.toInt()
        }) {
            Text(text = "Set")
        }

        Button(onClick = {
            name.value = share.keyName
            age.value = share.keyAge.toString()
        }) {
            Text(text = "Get")
        }
    }
}

class Share(context: Context){

    private val sharedPreference : SharedPreferences = context.getSharedPreferences(
        context.packageName,
        Context.MODE_PRIVATE
    )

    private val editor = sharedPreference.edit()

    val key_name = "keyName"
    val key_age = "keyAge"

    var keyName
        get() = sharedPreference.getString(key_name,"").toString()
        set(value)
        {
            editor.putString(key_name, value)
            editor.commit()
        }

    var keyAge
        get() = sharedPreference.getInt(key_age, 0)
        set(value) {
            editor.putInt(key_age,value)
            editor.commit()
        }

}