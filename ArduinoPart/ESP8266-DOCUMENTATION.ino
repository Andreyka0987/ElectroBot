
/*
  ESP8266-01 basic command-response Connection
  ESP8266-01     UNO
  3.3v+EN  ->    3.3V
  GND      ->    GND
  TX       ->    TX(DIGITAL PIN 1)
  RX       ->    RX(DIGITAL PIN 0)


  Common Serial speed baud 115200


  Basic commands
  AT
  AT+CWMODE?
  AT+CWMODE= 1 || 2 || 3
  AT+CWLAP 
  AT+CWJAP_DEF= "Name of the connectrion","Password"

  AT+CIFSR to check ip adress

  AT+CIPMUX?
  AT+CIPMUX = count of connections setting
  AT+CIPSERVER=1,port creates or deletes server
  AT+CIPSEND
  AT+CIPSTART="TCP","example.com",8000 || AT+CIPSTART=1,"TCP","example.com",8000
  AT+CIPCLOSE=0 close the conenction

POST /sentdata HTTP/1.1\r\n
Host:192.168.0.55:67\r\n
Content-Type: application/x-www-form-urlencoded\r\n
Content-length: 5\r\n
Connection: close\r\n\r\n
var=0\r\n


 */

#include <SoftwareSerial.h>

SoftwareSerial ESP8266(4,5); // RX TX

bool isWorking = false;
bool isConnected = false;
int analog6 = A6;


void setup(){
    Serial.begin(115200);
   ESP8266.begin(115200);

    pinMode(analog6, INPUT);

}


void loop(){

    tryAgain:
    ESP8266.print("AT\r\n");
    if (ESP8266.find("OK\r\n") == 1 && !isWorking){
      isWorking = true;
      Serial.println("Connected");
    }
    else{
      if (!isWorking) {
      Serial.println("Cannot confirm connectrion");
      goto tryAgain;
      }
    }


    String var = "0";

    if (analogRead(analog6) <= 100){var = "0";}
    else{var = "1";}
    Serial.println(analogRead(analog6));

    

    if(isWorking){
      
      ESP8266.println("AT+CIPSTART=\"TCP\",\"192.168.0.55\",8080");
      // tried to make not hard-coded version but it's doesn't work
   
      
      if (ESP8266.find("OK\r\n") == 1) {isConnected = true;}
      if (isConnected) {
        ESP8266.print("AT+CIPSEND=143\r\n");
        if(ESP8266.find(">") == 1){
      
        ESP8266.print("POST /sentdata HTTP/1.1\r\n");
        ESP8266.print("Host:192.168.0.55:8080\r\n");
        ESP8266.print("Content-Type: application/x-www-form-urlencoded\r\n");
        ESP8266.print("Content-length: 5\r\n");
        ESP8266.print("Connection: close\r\n");
        ESP8266.print("\r\n");
        ESP8266.print("var="+var);

        Serial.println("Post sended");
        
        delay(5000);
      }
       
      }
      else{
        Serial.println(ESP8266.read());
      }

    }


}
