import static com.amazonaws.regions.Regions.EU_WEST_1;

import java.util.List;
import java.util.UUID;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class QueueSubscriber implements Runnable {

    public static final String QUEUE_URL = "https://sqs.eu-west-1.amazonaws.com/826817530456/henrik";
    private final AmazonSQSClient sqsClient;

    QueueSubscriber()
    {
        sqsClient = new AmazonSQSClient( new BasicAWSCredentials(
                System.getenv("AWS_ACCESS_KEY"),
                System.getenv("AWS_SECRET_KEY")
        ));
        sqsClient.setRegion(Region.getRegion(EU_WEST_1));
    }


    @Override
    public void run() {
        while(true) {
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(QUEUE_URL);

            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();

            messages.forEach( message -> {
                System.out.println("Message received: " + message);
                sqsClient.deleteMessage(new DeleteMessageRequest(QUEUE_URL, message.getReceiptHandle()));
            });

            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
