package blood.blooddonation.repository

import blood.blooddonation.pdfDocument.PdfDocumentInterface
import blood.blooddonation.utils.Api

class PdfDocumentRepository {
    private val pdfDocumentInterface: PdfDocumentInterface =
        Api.retrofit.create(PdfDocumentInterface::class.java)
    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"
}