package playground.mocker.mocker;

import be.distrinet.spite.MqttSubscriber;
public class MockerTest {
    public static void main(String[] args) {
        MqttSubscriber subscriber = new MqttSubscriber("tcp://localhost:1883", "myApp/light");
        subscriber.subscribe( (json)->{System.out.println(json.toJSONString());},2*60*1000); //listen for 2 minutes

        MqttSubscriber subscriber2 = new MqttSubscriber("tcp://localhost:1883", "myApp/doorbell");
        subscriber2.subscribe((json)->{System.out.println(json.toJSONString());},2*60*1000);

        MqttSubscriber subscriber3 = new MqttSubscriber("tcp://localhost:1883", "myApp/proximity");
        subscriber3.subscribe((json)->{System.out.println(json.toJSONString());},-1); //listen indefinitely
    }
}
