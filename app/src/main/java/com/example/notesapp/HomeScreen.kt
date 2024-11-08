package com.example.notesapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val noteDatabase = remember { NoteDataBase.getDataBase(context) }
    val repository = remember { Repository(noteDatabase) }
    val viewModel = remember { MainViewModel(repository) }

    val notesList by viewModel.allNotes.observeAsState(initial = emptyList())

    val cardColors = listOf(
        Color(0xFFE0F7FA),
        Color(0xFFFFF9C4),
        Color(0xFFFFCDD2),
        Color(0xFFC8E6C9),
        Color(0xFFD1C4E9),
        Color(0xFFFFF59D)
    )

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "Recent Notes")
        }, navigationIcon = {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "")
        }, actions = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate(Screens.DetailsScreen.router) },
            shape = CircleShape,
            containerColor = Color.Red
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
        }
    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 11.dp, end = 11.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            notesList.let { notes ->
                items(notes.size) { index ->
                    val backgroundColor = cardColors[index % cardColors.size]
                    CardItem(note = notes[index], viewModel, backgroundColor = backgroundColor)
                }
            }
        }
    }
}

@Composable
fun CardItem(note: Note, viewModel: MainViewModel, backgroundColor: Color) {
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf(note.title) }
    var des by remember { mutableStateOf(note.des) }
    var AlertDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(190.dp)
            .wrapContentHeight()
            .clickable { AlertDialog = !AlertDialog },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "",
                Modifier
                    .clickable { scope.launch { viewModel.Delete(note) } }
                    .align(Alignment.End))
            Text(
                text = note.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = note.des, fontSize = 14.sp, color = Color.DarkGray
            )
        }
    }

    if (AlertDialog) {
        (AlertDialog(onDismissRequest = { AlertDialog = false }, confirmButton = {
            Text(text = "Update", modifier = Modifier.clickable {
                AlertDialog=false
                scope.launch {
                    viewModel.Update(
                        Note(
                            id = note.id, title, des
                        )
                    )
                }
            })
        }, title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFFFDF3BF)),
                horizontalAlignment = Alignment.CenterHorizontally
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
        }))

    }
}
