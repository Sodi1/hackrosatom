#include "DHT.h"
#include <PubSubClient.h>
#include <ESP8266WiFi.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#define ONE_WIRE_BUS 2
#include <Wire.h> 
#include "mydef.h"

#define DHTPIN 12
DHT dht(DHTPIN, DHT11);

const int analogSignal = A0; //подключение аналогового сигнального пина
const int digitalSignal = 5; //подключение цифрового сигнального пина
boolean noGas; //переменная для хранения значения о присутствии газа
int gasValue = 0; //переменная для хранения количества газа

WiFiClient espClient;
PubSubClient MqttClient(espClient);

// создаём объект для работы с библиотекой OneWire
OneWire oneWire(ONE_WIRE_BUS);
 
// создадим объект для работы с библиотекой DallasTemperature
DallasTemperature sensor(&oneWire);

void setup() { // различные установки
  WiFi.begin(WIFI_SSID, WIFI_PASS);
  MqttClient.setServer(MQTT_S, MQTT_P);
  sensor.begin();
  // устанавливаем разрешение датчика от 9 до 12 бит
  sensor.setResolution(12);
  dht.begin(); // инициализация опроса датчика
  
Serial.begin(9600); // инициализация COM
}

void loop() {

// Задержка 1 секунды между измерениями

delay(1000);

noGas = 0;//digitalRead(digitalSignal); //считываем значение о присутствии газа
gasValue = analogRead(analogSignal); // и о его количестве
Serial.print (gasValue);
if (gasValue < 100)
gasValue = gasValue*1.2;
else if (gasValue >= 100 && gasValue < 150)
gasValue = gasValue*2.5;
else if (gasValue>=150 && gasValue < 200)
gasValue = gasValue*4.5;
else if (gasValue>=200 && gasValue<250)
gasValue = gasValue*6.5;
else if (gasValue>=250 && gasValue<300)
gasValue = gasValue*7.5;
else gasValue = gasValue*8.5;
Serial.print (gasValue);
Serial.println("");
float temperature;
// отправляем запрос на измерение температуры
sensor.requestTemperatures();
// считываем данные из регистра датчика
temperature = sensor.getTempCByIndex(0);
// Считываем температуру
float t = dht.readTemperature();

/*// Проверка удачно прошло ли считыванnие
if (isnan(t) || isnan(h)) {
Serial.println("Не удается считать показания");
return;
}*/

if(WiFi.status() == WL_CONNECTED)
  {
  //Serial.print(" ");
  Serial.print(WiFi.localIP());
  //Serial.print(" ");
  MqttClient.connect("ESPClientTwo", MQTT_U, MQTT_PASS); 
  char x[4] = {0,0,0,0};
  
  /* перевод значения float в массив символов
  t- что переводим
  4 - длина получаемого символьного значения
  2 - количество символов после запятой
  x - куда записываем преобразованные данные*/
  dtostrf(temperature,4,2,x);  
  MqttClient.publish("oil/tempW",x); // отправка mqtt qos level 1
  dtostrf(t,4,2,x);  
  MqttClient.publish("oil/tempA",x); // отправка mqtt qos level 1
  dtostrf(gasValue,4,2,x);  
  MqttClient.publish("oil/gas",x); // отправка mqtt qos level 1  
  }
}
