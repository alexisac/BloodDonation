package blood.blooddonation.service

import blood.blooddonation.model.ScheduleCenter
import blood.blooddonation.repository.ScheduleRepository

class ScheduleService(
    private val scheduleRepository: ScheduleRepository
) {
    suspend fun getBookedForUser(idUser: String): Result<List<ScheduleCenter>> {
        return scheduleRepository.getBookedForUser(idUser)
    }
}