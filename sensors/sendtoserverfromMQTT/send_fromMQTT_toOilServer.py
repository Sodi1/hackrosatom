import json
import requests
import paho.mqtt.subscribe as mqtt
import paho.mqtt.client as mqttmy
import time
#broker = "130.61.187.214"
broker = "130.61.189.171"
port = "1883"
authM = {'username':'admin', 'password':'admin123'}
urlmy = "http://oil-api.kovalev.team/api/telemetry"
headersmy = {'Content-type':'application/json'}
#MQTT_TOPIC = [("student/temp",0), ("student/gas",0)]
MQTT_TOPIC = [("temp",0), ("hump1",0)]
tempW = 0 
tempA = 0 
gas = 0 
lightW = 0 
lightA = 0

def getTempFromDev():
    msg = mqtt.simple("student/temp", qos=0, hostname=broker, auth=authM)
    temp = (str(msg.payload)).replace("b","")
    temp1 = temp.replace("\'","")
    temp2 = float(temp1)
    print(temp2)
    return temp2

def getGasFromDev():
    msg1 = mqtt.simple("student/gas", qos=0, hostname=broker, auth=authM)
    gas = (str(msg1.payload)).replace("b","")
    gas1 = gas.replace("\'","")
    gas2 = float(gas1)
    print(gas2)
    return gas2

def on_connect(client, userdata, flags, rc):  # The callback for when the client connects to the broker
    print("Connected with result code {0}".format(str(rc)))  # Print result of connection attempt
    client.subscribe(MQTT_TOPIC)  # Subscribe to the topic “digitest/test1”, receive any messages published on it


def on_message(client, userdata, msg):  # The callback for when a PUBLISH message is received from the server.
    print("Message received-> " + msg.topic + " " + str(msg.payload))  # Print a received msg    
    tmp = (str(msg.payload)).replace("b","")
    tmp = tmp.replace("\'","")
    print(tmp)
    if msg.topic ==  "temp":
        tempW =  tmp
    if msg.topic ==  "gas":
        gas =  tmp
    if msg.topic ==  "hump":
        lightW =  tmp
    
    datamy = {
    "deviceId": 2,
    "tempwater": tempW,
    "tempair": tempA,
    "lightwater": lightW,
    "lighair": lightA,
    "gas": gas,
    "uv": 5003,    
    "oil": 0,
    "protocol": "MQTT"
    }
    r = requests.post(url=urlmy, json=datamy, headers=headersmy, verify=False)
    print(r)
    time.sleep(1)



client = mqttmy.Client("testMQTT")  # Create instance of client with client ID “digi_mqtt_test”
client.on_connect = on_connect  # Define callback function for successful connection
client.on_message = on_message  # Define callback function for receipt of a message
# client.connect("m2m.eclipse.org", 1883, 60)  # Connect to (broker, port, keepalive-time)
client.username_pw_set("admin", "admin123")
client.connect(broker, 1883)
client.loop_forever()  # Start networking daemon


# while 1:
#     temp = getTempFromDev()
#     gas = getGasFromDev()
#     datamy = {
#     "deviceId": 5,
#     "temperature": temp,
#     "light": 0,
#     "gas": gas,
#     "uv": 5001,
#     "oil": 0
#     }
#     r = requests.post(url=urlmy, json=datamy, headers=headersmy, verify=False)
#     print(r)
#     time.sleep(1)

