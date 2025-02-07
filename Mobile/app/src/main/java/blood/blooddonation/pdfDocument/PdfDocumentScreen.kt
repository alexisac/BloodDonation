package blood.blooddonation.pdfDocument

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.ui.theme.AppColor
import java.io.File
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfDocumentScreen(userId: String?, goBack: () -> Unit) {
    val pdfDocumentViewModel = viewModel<PdfDocumentViewModel>(factory = PdfDocumentViewModel.Factory)
    val pdfDocumentUiState = pdfDocumentViewModel.uiState

    val gradient = Brush.verticalGradient(
        colors = listOf(AppColor.RedGrenaline, AppColor.WhiteLevenderBlush),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.generatePdf),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        color = AppColor.BlackSoot
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        onClick = { goBack() }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AppColor.BlackSoot
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        onClick = { pdfDocumentViewModel.sharePdf() }
                    ) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = AppColor.BlackSoot
                        )
                    }
                }
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(gradient)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            PdfViewer(pdfDocumentUiState)
        }
    }

    LaunchedEffect(key1 = true) {
        pdfDocumentViewModel.generatePdf(userId)
    }
}

@Composable
fun PdfViewer(uiState: PdfDocumentUiState) {
    if (uiState.isPdfAvailable) {
        val parcelFileDescriptor = remember { ParcelFileDescriptor.open(File(uiState.path), ParcelFileDescriptor.MODE_READ_ONLY) }
        val pdfRenderer = remember { PdfRenderer(parcelFileDescriptor) }
        val currentPage = remember { pdfRenderer.openPage(0) }
        val bitmap = remember {
            Bitmap.createBitmap(currentPage.width, currentPage.height, Bitmap.Config.ARGB_8888).also {
                currentPage.render(it, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            }
        }
        val zoomState = rememberZoomableState()

        Zoomable(state = zoomState) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "PDF Page",
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColor.WhiteLevenderBlush)
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                currentPage.close()
                pdfRenderer.close()
                parcelFileDescriptor.close()
            }
        }
    } else {
        Text("Niciun PDF valabil pentru a fi afișat.")
    }
}

@Composable
fun Zoomable(
    modifier: Modifier = Modifier,
    state: ZoomableState = rememberZoomableState(),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .graphicsLayer(
                scaleX = max(1f, state.scale),
                scaleY = max(1f, state.scale),
                translationX = state.offsetX,
                translationY = state.offsetY
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    state.onGesture(pan, zoom)
                }
            }
    ) {
        content()
    }
}

@Stable
class ZoomableState(
    var scale: Float = 1f,
    var offsetX: Float = 0f,
    var offsetY: Float = 0f
) {
    fun onGesture(pan: Offset, zoom: Float) {
        scale *= zoom
        offsetX += pan.x * scale
        offsetY += pan.y * scale
    }
}

@Composable
fun rememberZoomableState(): ZoomableState = remember { ZoomableState() }

@Preview(showBackground = true)
@Composable
fun PdfDocumentScreenPreview() {
    PdfDocumentScreen(userId = null, goBack = {})
}