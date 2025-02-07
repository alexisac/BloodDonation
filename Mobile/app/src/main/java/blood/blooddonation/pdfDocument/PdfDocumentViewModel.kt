package blood.blooddonation.pdfDocument

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.model.GeneralInfo
import blood.blooddonation.model.enums.EligibilityType
import blood.blooddonation.model.enums.convertIntToBloodType
import blood.blooddonation.service.GeneralInfoService
import blood.blooddonation.service.PdfDocumentService
import blood.blooddonation.service.VerifyEligibilityService
import blood.blooddonation.utils.Constants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import kotlinx.coroutines.launch
import java.io.File

data class PdfDocumentUiState(
    val path: String = "",
    val eligibilityType: EligibilityType = EligibilityType.UNDEFINED,
    val generalInfo: GeneralInfo? = null,
    val error: Throwable? = null,
    val inProgress: Boolean = false,
    val complete: Boolean = false,
    val isPdfAvailable: Boolean = false
)

class PdfDocumentViewModel(
    private val pdfDocumentService: PdfDocumentService,
    private val verifyEligibilityService: VerifyEligibilityService,
    private val generalInfoService: GeneralInfoService,
    private val context: Context
):ViewModel() {

    var uiState: PdfDocumentUiState by mutableStateOf(PdfDocumentUiState())

    private fun createPdfWithText(idUser: String?) {
        getGeneralInformation(idUser) {generalInfo ->
            val filledText = Constants.PDF_TEXT
                .replace("{PERSON_NAME}", generalInfo?.lastName + " " + generalInfo?.firstName)
                .replace("{TEST_RESULT}", uiState.eligibilityType.displayName)
                .replace("{BIRTH_DATE}", generalInfo?.birthDate ?: "--------------")
                .replace("{BLOOD_TYPE}", generalInfo?.bloodType?.toIntOrNull()?.let { convertIntToBloodType(it).displayName } ?: "--------------")
            try {
                val path = context.getExternalFilesDir(null)?.absolutePath + "/donation_info.pdf"
                val pdfWriter = PdfWriter(path)
                val pdfDocument = PdfDocument(pdfWriter)
                val document = Document(pdfDocument)
                document.add(Paragraph(filledText))
                document.close()
                uiState = uiState.copy(path = path, isPdfAvailable = true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun generatePdf(idUser: String?) {
        getEligibilityTypeForUser(idUser) { eligibilityType ->
            if (eligibilityType == EligibilityType.ELIGIBIL ||
                eligibilityType == EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION) {
                createPdfWithText(idUser)
            }
        }
    }

    private fun getEligibilityTypeForUser(idUser: String?, onResult: (EligibilityType?) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            try {
                val result = idUser?.let { verifyEligibilityService.getEligibilityTypeForUser(it) }
                if (result != null) {
                    result.onSuccess { eligibilityType ->
                        uiState = uiState.copy(
                            eligibilityType = eligibilityType,
                            complete = true,
                            inProgress = false
                        )
                        onResult(eligibilityType)
                    }.onFailure { exception ->
                        uiState = uiState.copy(
                            error = exception,
                            inProgress = false
                        )
                        onResult(null)
                    }
                } else {
                    uiState = uiState.copy(
                        error = IllegalArgumentException("User ID is null"),
                        inProgress = false
                    )
                    onResult(null)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e,
                    inProgress = false
                )
                onResult(null)
            }
        }
    }

    private fun getGeneralInformation(idUser: String?, onResult: (GeneralInfo?) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            try {
                val result = idUser?.let { generalInfoService.getGeneralInformation(it) }
                if (result != null) {
                    result.onSuccess { generalInfo ->
                        uiState = uiState.copy(
                            generalInfo = generalInfo,
                            complete = true,
                            inProgress = false
                        )
                        onResult(generalInfo)
                    }.onFailure { exception ->
                        uiState = uiState.copy(
                            error = exception,
                            inProgress = false
                        )
                        onResult(null)
                    }
                } else {
                    uiState = uiState.copy(
                        error = IllegalArgumentException("User ID is null"),
                        inProgress = false
                    )
                    onResult(null)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e,
                    inProgress = false
                )
                onResult(null)
            }
        }
    }

    fun sharePdf() {
        val fileUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            File(uiState.path)
        )
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, fileUri)
            type = "application/pdf"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooserIntent = Intent.createChooser(shareIntent, "Share PDF via:")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                PdfDocumentViewModel(
                    app.container.pdfDocumentService,
                    app.container.verifyEligibilityService,
                    app.container.generalInfoService,
                    app.container.context
                )
            }
        }
    }
}