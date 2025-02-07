package blood.blooddonation.service

import blood.blooddonation.model.GeneralInfo
import blood.blooddonation.model.enums.BloodType
import blood.blooddonation.model.enums.Sex
import blood.blooddonation.repository.GeneralInfoRepository
import java.text.SimpleDateFormat
import java.util.Date

class GeneralInfoService(
    private val generalInfoRepository: GeneralInfoRepository
) {
    suspend fun sendGeneralInformation(
        id: String,
        firstName: String,
        lastName: String,
        birthDate: Date,
        sex: Sex,
        bloodType: BloodType
    ): Result<Boolean> {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val generalInfo = GeneralInfo(
            idUser = id,
            firstName = firstName,
            lastName = lastName,
            birthDate = dateFormat.format(birthDate),
            sex = sex.displayName,
            bloodType = bloodType.displayName,
            points = 0
            )
        return generalInfoRepository.sendGeneralInfo(generalInfo)
    }

    suspend fun getGeneralInformation(idUser: String): Result<GeneralInfo> {
        return generalInfoRepository.getGeneralInformation(idUser)
    }
}