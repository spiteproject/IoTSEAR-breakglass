package be.hcbgsystem.core.context.sources;

import be.distrinet.spite.MqttSubscriber;
import be.hcbgsystem.core.context.ContextProcessor;
import be.hcbgsystem.core.models.ContextData;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class MqttSensorData extends ContextDataSource {
    ArrayList<MqttSubscriber> subs;

    public MqttSensorData(ContextProcessor processor) {
        super(processor);
        subs = new ArrayList<>();
    }

    @Override
    public void startSource() {
   //     MqttSubscriber subscriber = new MqttSubscriber("tcp://localhost:1883", "hcbg/heartrate");
     //   subs.add(subscriber);
     //   subscriber.subscribe(this::onNewMqttMessage,-1);
    }

    @Override
    public void stopSource() {
        // Unsubscribe unsupported in current Mqtt mocker
    }

    private void onNewMqttMessage(JSONObject obj) {
        // Format: {"DeviceType":"BTSensor","AttributeValue":"1","AttributeType":"heartrate","DeviceIdentifier":"heartratesensor-1"}
        ContextData<String> contextData = new ContextData<>(
                obj.get("DeviceIdentifier").toString(),
                obj.get("AttributeType").toString(),
                obj.get("AttributeValue").toString(),
                new Timestamp(Instant.now().getEpochSecond())
        );

        onReceived(contextData);
    }
}
