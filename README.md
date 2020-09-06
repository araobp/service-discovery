# Very simple service discovery protocol

<img src="./screenshot_client.png" width=220>

## Motivation

My RaspberryPi 3 obtains an IP address from DHCP server on my WiFi router. The IP address changes everytime I boot the Raspberry PI.

I just want a very simple service discovery protocol to find an IP address of my RaspberryPi 3 on the LAN.

## Why not use a standard procotol?

I used to be a SIP (Session Initiation Protocol) expert. The protocol basically provides a mean to find serivces on the network, such as a VoIP service or a video broadcasting service.

But, if the scope is limited to my LAN at home, such a very complicated protocol is not necessary.

## Message sequence diagram

An UDP packet with a service ID in its payload is broadcast to the LAN's broadcast IP address at a certain interval:

```
  Server                  Client
     |                      |
     |----- UDP 18084 ----->|
     |                      |
     |----- UDP 18084 ----->|
     |                      |
     |----- UDP 18084 ----->|
     |                      |
```

## Source code

### Server (C++)

C++ server code implementation that runs as a Linux daemon on Raspberry Pi:

=> [Code](./server)

And a sample systemd service file is here:

=> [Service file](./systemd)

### Client (Kotlin)

Kotlin client code for Android smartphones:

=> [Code](./client)

The GUI also shows its own IP address on its WLAN interface.
