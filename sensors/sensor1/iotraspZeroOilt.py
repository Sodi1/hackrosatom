#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import requests
import json
import time
from datetime import datetime
from w1thermsensor import W1ThermSensor
import Adafruit_DHT
import Adafruit_ADS1x15

urlmy = "http://oil-api.kovalev.team/api/telemetry"
headersmy = {'Content-type':'application/json'}
pin = '4'
adc = Adafruit_ADS1x15.ADS1115()

sensor = W1ThermSensor()

while True:
    temperature = sensor.get_temperature()
    values = [0]*4
    tempW = 0
    tempA = 0
    count = 0
    
    for sensor in W1ThermSensor.get_available_sensors():
        if count == 0:
            tempW = sensor.get_temperature()
        else:
            tempA = sensor.get_temperature()
        count = count + 1
    
    for i in range(4):
        values[i] = adc.read_adc(i,1)
    print('| {0:6} | {1:>6} | {2:>6} | {3:>6} |'.format(*values))
    #print(tempW)
    #print(tempA)
    datamy = {
    "deviceId": 1,
    "tempwater": tempW,
    "tempair": tempA,
    "lightwater": values[0],
    "lighair": values[2],
    "gas": values[1],
    "uv": 5003,    
    "oil": 0,
    "protocol": "MQTT"
    }
    r = requests.post(url=urlmy, json=datamy, headers=headersmy, verify=False)
    #print(r)
    time.sleep(1)
