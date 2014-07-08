import static com.amazonaws.regions.Regions.EU_WEST_1;

import java.util.UUID;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class TopicPublisher implements Runnable {

    public static final String TOPIC_ARN = "arn:aws:sns:eu-west-1:826817530456:henrik_testing";
    private final AmazonSNSClient snsClient;

    TopicPublisher()
    {
        snsClient = new AmazonSNSClient( new BasicAWSCredentials(
                System.getenv("AWS_ACCESS_KEY"),
                System.getenv("AWS_SECRET_KEY")
        ));
        snsClient.setRegion(Region.getRegion(EU_WEST_1));
    }


    @Override
    public void run() {
        while(true) {
            String msg = "Message - " + UUID.randomUUID();
            PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, msg);
            PublishResult publishResult = snsClient.publish(publishRequest);

            System.out.println("Published message: " + publishResult.getMessageId());
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
