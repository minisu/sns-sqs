public class MessagingApp {

    public static void main(String... args) {
        QueueSubscriber queueSubscriber = new QueueSubscriber();
        new Thread(queueSubscriber).start();

        TopicPublisher topicPublisher = new TopicPublisher();
        new Thread(topicPublisher).start();
    }
}
