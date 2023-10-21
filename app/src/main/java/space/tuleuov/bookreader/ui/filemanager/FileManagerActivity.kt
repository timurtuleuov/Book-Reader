package space.tuleuov.bookreader.ui.filemanager

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File

class FileManagerActivity : ComponentActivity() {
    private val storagePermissionCode = 101
    private val rootDirectory = Environment.getExternalStorageDirectory() // Укажите корневую директорию

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FileManagerContent(rootDirectory)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class)
@Composable
fun FileManagerContent(rootDirectory: File) {
    var fileNames by remember { mutableStateOf(listOf<String>()) }
    val permissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)

    // Отображение списка файлов и папок
    val fileList = rootDirectory.listFiles()
    fileList?.let {
        fileNames = fileList.map { it.name }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("File Manager") }
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
                LazyColumn {
                    items(fileNames) { fileName ->
                        Text(text = fileName)
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
