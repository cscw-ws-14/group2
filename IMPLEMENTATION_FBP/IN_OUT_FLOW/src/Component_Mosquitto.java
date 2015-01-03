import com.jpmorrsn.fbp.engine.Component;
import com.jpmorrsn.fbp.engine.ComponentDescription;
import com.jpmorrsn.fbp.engine.InPorts;
import com.jpmorrsn.fbp.engine.InPort;
import com.jpmorrsn.fbp.engine.InputPort;
import com.jpmorrsn.fbp.engine.MustRun;
import com.jpmorrsn.fbp.engine.OutPort;
import com.jpmorrsn.fbp.engine.OutPorts;
import com.jpmorrsn.fbp.engine.OutputPort;
import com.jpmorrsn.fbp.engine.Packet;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
 
@InPorts({
    @InPort(value = "CLIENTID", description = "client id of the client, can put random string here", type = String.class),
    @InPort(value = "TOPIC", description = "topic", type = String.class),
    @InPort(value = "CONTENT", description = "the contents", type = String.class)
})
public class Component_Mosquitto extends Component{
     
    private InputPort clientId;
    private InputPort content;
    private InputPort topic;
     
    private int qos             = 2;
    private String broker       = "tcp://localhost:1883";
     
    @Override
    protected void execute() throws Exception {
         
         
        Packet pCl = clientId.receive();
        String clientId_name = (String) pCl.getContent();
        drop(pCl);
         
        Packet pT = topic.receive();
        String topic_name    = (String) pT.getContent();
        drop(pT);
         
        Packet pC = content.receive();
        String content_name  = (String) pC.getContent();
        drop(pC);
        try {
            MemoryPersistence persistence = new MemoryPersistence();
 
            // connect to mqtt server, make sure that the server is on
            System.out.println("Connecting to broker: " + broker);
            MqttClient sampleClient = new MqttClient(broker, clientId_name, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
 
            // set up topic and publishing the message
            System.out.println("Connected");
            System.out.println(topic_name + " is Publishing message: " + content_name);
            MqttMessage message = new MqttMessage(content_name.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic_name, message);
 
            // disconnect from the server
            sampleClient.disconnect();
            System.out.println("Disconnected");
 
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excerpt "+me);
            me.printStackTrace();
        }
         
    }
     
    @Override
    protected void openPorts() {
        clientId = openInput("CLIENTID");
        content =  openInput("CONTENT");
        topic =  openInput("TOPIC");
    }
 
}