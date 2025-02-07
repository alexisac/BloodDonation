package Controller;

import Utils.Constants;
import Utils.RestAPIException;
import Utils.ServiceException;
import org.example.BloodDonationML;
import org.example.ChatBot;
import org.example.ChatMessage;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class ChatGptEndpoints {
    private final ChatBot chatBot;
    private final BloodDonationML blml;

    // constructor
    public ChatGptEndpoints() {
        BloodDonationML blml = new BloodDonationML(
                "TrainingData/keyWords.txt",
                "TrainingData/trainingData.txt",
                0.5);
        blml.bloodDonationMLMain();

        this.chatBot = new ChatBot();
        this.blml = blml;
    }

    @RequestMapping(value = "/chatBot/getAnswer", method = RequestMethod.POST)
    public ChatMessage getChatGptAnswer(@RequestBody String question) throws RestAPIException {
        try {
            int value = blml.predictSentence(question);
            if (value == 1) {
                String alex = chatBot.gptEndpoints(question);
                return new ChatMessage(alex);
            } else {
                return new ChatMessage("Nu este legat de donarea de sange");
            }
        }
        catch (Exception ex){
            throw new RestAPIException(ex.getMessage());
        }
    }
}
