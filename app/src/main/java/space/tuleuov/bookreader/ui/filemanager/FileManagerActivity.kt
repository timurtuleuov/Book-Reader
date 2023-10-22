package space.tuleuov.bookreader.ui.filemanager

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import space.tuleuov.bookreader.R
import java.io.File
import androidx.compose.material.Checkbox
import androidx.navigation.NavController
import java.net.URLEncoder

class FileManagerActivity : ComponentActivity() {
    private val storagePermissionCode = 101
    private val rootDirectory = Environment.getExternalStorageDirectory() // Укажите корневую директорию

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class)
@Composable
fun FileManagerContent(directoryPath: String?, navController: NavController) {
    println("привет из файл менеджера")
    val selectedFiles = remember { mutableStateListOf<File>() }
    var fileNames by remember { mutableStateOf(listOf<String>()) }
    val permissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)

    // Отображение списка файлов и папок
    val directory = if (directoryPath != null) File(directoryPath) else null

    val fileList = directory?.listFiles()
    fileList?.let {
        fileNames = fileList.map { it.name }
    }
    val onFileSelected: (File) -> Unit = { file ->
        if (selectedFiles.contains(file)) {
            selectedFiles -= file
        } else {
            selectedFiles += file
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Файловый менеджер") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BasicTextField(
                value = "",
                onValueChange = { /*TODO: Implement search functionality */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 18.sp)
            )

            if (permissionState.status.isGranted) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(fileNames) { fileName ->
                        val file = File(directoryPath, fileName)

                        val isDirectory = file.isDirectory

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            if (!isDirectory) {
                                Checkbox(
                                    checked = selectedFiles.contains(file),
                                    onCheckedChange = {
                                        if (it) {
                                            selectedFiles.add(file)
                                        } else {
                                            selectedFiles.remove(file)
                                        }
                                    }
                                )
                            }
                            Icon(
                                painterResource(if (isDirectory) R.drawable.ic_directory else R.drawable.ic_file),
                                contentDescription = null
                            )
                            Text(
                                text = fileName,
                                modifier = Modifier.clickable {
                                    if (isDirectory) {
                                        val pathToDirectory = URLEncoder.encode(file.path, "UTF-8")
                                        navController.navigate("fileManager/$pathToDirectory")
                                    } else {
                                        // Открыть файл (здесь можно добавить логику открытия файла)
                                    }
                                }
                            )
                        }
                    }
                }


            } else {
                Column {
                    val textToShow = if (permissionState.status.shouldShowRationale) {
                        // If the user has denied the permission but the rationale can be shown,
                        // then gently explain why the app requires this permission
                        "Вы не разрешили доступ приложению для чтения файлов"
                    } else {
                        // If it's the first time the user lands on this feature, or the user
                        // doesn't want to be asked again for this permission, explain that the
                        // permission is required
                        "Camera permission required for this feature to be available. " +
                                "Please grant the permission"
                    }
                    Text(textToShow)
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Разрешить доступ")
                    }
                }
//                PermissionRationale(
//                    permissionState = permissionState,
//                    rationale = {
//                        // Выводите пользователю объяснение, почему необходимо разрешение
//                    },
//                    onPermissionRequested = {
//                        // Запросите разрешение у пользователя
//                        permissionState.launchPermissionRequest()
//                    }
//                )
            }
        }
    }
}
