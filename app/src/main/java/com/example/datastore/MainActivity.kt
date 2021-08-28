package com.example.datastore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.ui.theme.DataStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreTheme {

                val context = LocalContext.current

                val viewModel: MyViewModel by viewModels()
                val coroutineScope = rememberCoroutineScope()

                var name by remember {
                    viewModel.name
                }

                var age by remember {
                    viewModel.age
                }

                LaunchedEffect(true) {
                    viewModel.getName()
                    viewModel.getAge()
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextField(
                        value = name,
                        onValueChange = { name = it }
                    )
                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = age,
                        onValueChange = { age = it }
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                saveData(
                                    context,
                                    name,
                                    age.toInt()
                                )
                                viewModel.apply {
                                    getName()
                                    getAge()
                                }
                            }
                        },
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 18.sp
                        )
                    }
                }

            }
        }
    }

    private suspend fun saveData(context: Context, name: String, age: Int) {
        context.myDataStore.edit {
            it[stringPreferencesKey("key1")] = name
            it[intPreferencesKey("key2")] = age
        }
    }

}













