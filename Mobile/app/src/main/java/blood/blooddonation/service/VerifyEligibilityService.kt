package blood.blooddonation.service

import blood.blooddonation.model.Question
import blood.blooddonation.model.enums.EligibilityType
import blood.blooddonation.repository.VerifyEligibilityRepository
import blood.blooddonation.utils.Constants

class VerifyEligibilityService (
    private val verifyEligibilityRepository: VerifyEligibilityRepository
) {
    private var nrOfQuestion = 0
    private var nrOfPoints = 0
    private val listQuestions = Constants.QUESTIONS
    fun getNewQuestion(): Question? {
        if(nrOfQuestion >= listQuestions.size) {
            nrOfQuestion = 0
            return null
        }
        nrOfQuestion++
        return listQuestions[nrOfQuestion-1]
    }

    fun getNoOfQuestion(): Int {
        return nrOfQuestion
    }

    fun getNoTotalOfQuestions(): Int {
        return listQuestions.size
    }

    fun updatePoints(points: Int) {
        nrOfPoints += points
    }

    fun resetPoints() {
        nrOfPoints = 0
    }

    fun resetQuestions(){
        nrOfQuestion = 0
    }

    fun decideEligibilityType(): EligibilityType {
        return if (nrOfPoints < Constants.ELIGIBILITYTHRESHOLDMIN)
            EligibilityType.ELIGIBIL
        else if (nrOfPoints > Constants.ELIGIBILITYTHRESHOLDMAX)
            EligibilityType.NOT_ELIGIBIL
        else EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION
    }

    suspend fun saveEligibilityType(idUser: String, eligibilityType: Int) {
        verifyEligibilityRepository.saveEligibilityType(
            idUser = idUser,
            eligibilityType = eligibilityType
        )
    }

    suspend fun getEligibilityTypeForUser(idUser: String): Result<EligibilityType> {
        return verifyEligibilityRepository.getEligibilityTypeForUser(idUser = idUser)
    }
}