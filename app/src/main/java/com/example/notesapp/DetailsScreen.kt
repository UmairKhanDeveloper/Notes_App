package com.example.notesapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.db.MainViewModel
import com.example.notesapp.db.Note
import com.example.notesapp.db.NoteDataBase
import com.example.notesapp.db.Repository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(navController: NavController) {
    val context = LocalContext.current
    val noteDatabase = remember { NoteDataBase.getDataBase(context) }
    val repository = remember { Repository(noteDatabase) }
    val viewModel = remember { MainViewModel(repository) }
    var title by remember { mutableStateOf("") }
    var des by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "Edit Notes")
        }, navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "",
                modifier = Modifier.clickable { navController.navigateUp() })
        }, actions = {
            Text(
                text = "Save",
                modifier = Modifier.clickable {
                    navController.navigateUp()
                    scope.launch {
                        viewModel.Insert(
                            note = Note(
                                null,
                                title,
                                des
                            )
                        )
                    }
                })
        }, colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color(0XFFFDF3BF)))
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(Color(0XFFFDF3BF)), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = title,
                onValueChange = {
                    title = it
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0XFFFDF3BF),
                    unfocusedContainerColor = Color(0XFFFDF3BF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                placeholder = {
                    Text(text = "Title", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
                },
                textStyle = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = des,
                onValueChange = {
                    des = it
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0XFFFDF3BF),
                    unfocusedContainerColor = Color(0XFFFDF3BF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = "des", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                },
                textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )

        }

    }

}