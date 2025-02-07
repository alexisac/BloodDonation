package blood.blooddonation

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import blood.blooddonation.item.ItemRepository
import blood.blooddonation.item.ItemService
import blood.blooddonation.item.ItemWsClient
import blood.blooddonation.preferences.UserPreferencesRepository
import blood.blooddonation.repository.AuthRepository
import blood.blooddonation.repository.ChatLiveRepository
import blood.blooddonation.repository.DonationCentersRepository
import blood.blooddonation.repository.DonationHistoryRepository
import blood.blooddonation.repository.GeneralInfoRepository
import blood.blooddonation.repository.PdfDocumentRepository
import blood.blooddonation.repository.ScheduleRepository
import blood.blooddonation.repository.VerifyEligibilityRepository
import blood.blooddonation.service.AuthService
import blood.blooddonation.service.ChatLiveService
import blood.blooddonation.service.DonationCentersService
import blood.blooddonation.service.DonationHistoryService
import blood.blooddonation.service.GeneralInfoService
import blood.blooddonation.service.PdfDocumentService
import blood.blooddonation.service.ScheduleService
import blood.blooddonation.service.VerifyEligibilityService
import blood.blooddonation.utils.Api

val Context.userPreferencesDataStore by preferencesDataStore(name = "user_preferences")

class AppContainer(val context: Context) {
    private val itemService: ItemService = Api.retrofit.create(ItemService::class.java)
    private val itemWsClient: ItemWsClient = ItemWsClient(Api.okHttpClient)

    private val authRepository: AuthRepository = AuthRepository()
    private val donationCentersRepository: DonationCentersRepository = DonationCentersRepository()
    private val generalInfoRepository: GeneralInfoRepository = GeneralInfoRepository()
    private val donationHistoryRepository: DonationHistoryRepository = DonationHistoryRepository()
    private val verifyEligibilityRepository: VerifyEligibilityRepository = VerifyEligibilityRepository()
    private val chatLiveRepository: ChatLiveRepository = ChatLiveRepository()
    private val pdfDocumentRepository: PdfDocumentRepository = PdfDocumentRepository()
    private val scheduleRepository: ScheduleRepository = ScheduleRepository()

    private val database: BloodDonationDatabase by lazy {
        BloodDonationDatabase.getDatabase(context)
    }

    val itemRepository: ItemRepository by lazy {
        ItemRepository(itemService, itemWsClient, database.itemDao(), context)
    }

    val authService: AuthService by lazy {
        AuthService(authRepository)
    }
    val donationCentersService: DonationCentersService by lazy {
        DonationCentersService(donationCentersRepository)
    }
    val generalInfoService: GeneralInfoService by lazy {
        GeneralInfoService(generalInfoRepository)
    }
    val donationHistoryService: DonationHistoryService by lazy {
        DonationHistoryService(donationHistoryRepository)
    }
    val verifyEligibilityService: VerifyEligibilityService by lazy {
        VerifyEligibilityService(verifyEligibilityRepository)
    }
    val chatLiveService: ChatLiveService by lazy {
        ChatLiveService(chatLiveRepository)
    }
    val pdfDocumentService: PdfDocumentService by lazy {
        PdfDocumentService(pdfDocumentRepository)
    }
    val scheduleService: ScheduleService by lazy {
        ScheduleService(scheduleRepository)
    }

    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.userPreferencesDataStore)
    }
}