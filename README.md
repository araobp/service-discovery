# Very simple service discovery protocol

## Motivation

My RaspberryPi 3 obtains an IP address from DHCP server on my WiFi router. The IP address changes everytime I boot the Raspberry PI.

I just want a very simple service discovery protocol to find an IP address of my RaspberryPi 3 on the LAN.

## Message sequence diagram

An UDP packet with a service ID in its payload is broadcast to the LAN's broadcast IP address at a certain interval:

```
  Web cam          Launcher app on Android
     |                      |
     |----- UDP 18084 ----->|
     |                      |
     |----- UDP 18084 ----->|
     |                      |
     |----- UDP 18084 ----->|
     |                      |
```

## Source code

=> [Code](./discovery)

## Sample systemd service file

=> [Service file](./systemd)

## Android app to show an IP address of my RaspberryPi 3

(Work in progress)
