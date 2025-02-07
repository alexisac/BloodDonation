package blood.blooddonation.utils

import blood.blooddonation.model.Answer
import blood.blooddonation.model.Question

class Constants {
    companion object {
        val QUESTIONS = listOf(
            Question(
                "Ce vârstă aveți?",
                listOf(
                    Answer("sub 18", 3),
                    Answer("18 - 65", 0),
                    Answer("peste 65", 3)
                )
            ),
            Question(
                "Ați avut febră / tuse / greață în ultimele 2 săptămâni?",
                listOf(
                    Answer("Da", 2),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați fost diagnosticat cu diabet/tensiune sau alte boli cronice?",
                listOf(
                    Answer("Da", 4),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați călătorit în țări cu risc ridicat de îmbolnăvire în ultimele 6 luni?",
                listOf(
                    Answer("Da", 3),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați avut intervenții chirurgicale în ultimele 6 luni?",
                listOf(
                    Answer("Da", 3),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Care este greutatea dumneavoastră?",
                listOf(
                    Answer("sub 50Kg", 2),
                    Answer("mai mult de 50Kg", 0)
                )
            ),
            Question(
                "Ați consumat alcool în ultimele 24 de ore?",
                listOf(
                    Answer("Da", 4),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați fost însărcinată/ați născut în ultimele 6 luni?",
                listOf(
                    Answer("Da", 3),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați făcut vreun tatuaj/piercing în ultimele 6 luni?",
                listOf(
                    Answer("Da", 3),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați luat medicamente în ultima lună?",
                listOf(
                    Answer("Da", 3),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați avut HIV sau alte boli transmisibile?",
                listOf(
                    Answer("Da", 4),
                    Answer("Nu", 0)
                )
            ),
            Question(
                "Ați avut/aveți alergii severe?",
                listOf(
                    Answer("Da", 2),
                    Answer("Nu", 0)
                )
            )
        )

        val ELIGIBILITYTHRESHOLDMIN = 1
        val ELIGIBILITYTHRESHOLDMAX = 10

        /**
         * {PERSON_NAME} = numele donatorului
         * {TEST_RESULT} = rezultatul testului efectuat
         * {BIRTH_DATE} = data de nastere a donatorului
         * {BLOOD_TYPE} = grupa de sange a donatorului
         */
        val PDF_TEXT = """
            INFORMATII PRIVIND DONAREA DE SANGE

                Pentru ca aceasta procedura sa se poata desfasura in deplina securitate pentru subsemnatul {PERSON_NAME},
            nascut la data de {BIRTH_DATE}, ca donator, cu grupa de sange {BLOOD_TYPE}, cat si pentru pacientul 
            transfuzat, va invitam sa cititi cu atentie informatiile cuprinse in acest document:
            Donarea de sange total consta in recoltarea unei cantitati de 450 ml in timp de 10-15 minute.
            In aceasta situatie barbatii pot dona de 5 ori pe an, iar femeile de 4 ori pe an.

            Inainte de recoltare se recomanda:
                - sa fiti odihnit, bine hidratat, sa se consume un mic dejun lejer
                - sa evite alimentele grase, bauturile alcoolice si fumatul

            Recoltarea de sange se face in conditii stricte de siguranta de catre personalul medical calificat,
            materialul de recoltare este steril si de unica folosinta - nu reprezinta risc de imbolnavire.

            Testele biologice efectuate sistematic la fiecare donare cuprind: determinarea grupului
            sanguin OAB si Rh, a hemoglobinei, depistarea siluetei sifilitice, anticorpii anti HIV si anti HTLV,
            hepatitele virale B si C, determinarea enzimelor hepatice (transaminazele).

            Eligibilitatea pentru donarea de sange se determina pe baza unui test preliminar completat inainte de donare. 
            Rezultatul acestui test poate fi:
            - ELIGIBIL: Puteti dona sange fara restrictii suplimentare.
            - ELIGIBIL CU NECESITATE DE CONSULT MEDICAL: Puteti dona sange, dar este necesara o evaluare suplimentara de catre un medic.
            - NEELIGIBIL: Nu puteti dona sange pe baza criteriilor actuale de eligibilitate.
            - NEDETERMINAT: Este necesara o evaluare pentru a determina eligibilitatea.

            Rezultatul dumneavoastra este: {TEST_RESULT}

            Nedeclararea intentionata a bolilor transmisibile sau factorilor de risc cunoscuti dupa
            prealabila dvs. informare constituie infractiune si se pedepseste conform art. 39 si 40 din Legea
            nr. 282/2005 privind organizarea activitatii de transfuzie sanguina.

            Nu oferiti sangele dvs. urmarind rezultatul testelor de depistare (analize).

            "Daruind o picatura din sangele dvs., dati o sansa vietii aproapelui."
            """.trimIndent()
    }
}