# Train Counter

This project listens for train whistles near my home and logs them in a MySQL database. A UI presents the data in a useful format.

We use crontab to record 60 second sound files (11 MBytes) every minute, then run that through the train detector (this program) and 
write the results back out. We detect if any 1 second chunk contains a horn, and the 12 numbers associated with the binned FFT.
Finally we use an MVC framework (I use Spring Boot) to handle the persistence and presentation layers of the app.

You may have to adjust the detector settings in the program, based on how loud train horns are where you live.

## Hardware:
1. Raspberry Pi 4 with 64 GB memory card.
2. Plugable® USB Audio Adapter with 3.5mm Speaker/Headphone and Microphone Jacks
   (Black Aluminum; C-Media CM108 Chip).
3. Sony ECM-DS70P Electret Condenser Stereo Microphone.

## Configure Audio Input
per https://learn.adafruit.com/downloads/pdf/usb-audio-cards-with-a-raspberry-pi.pdf

For my use case, I have an adapter with a CM108 chip. I can verify that with this command

    dmesg | grep C-Media
    usb 1-1.3: Manufacturer: C-Media Electronics Inc.
    C-Media Electronics Inc. USB Audio Device as
    /devices/platform/scb/fd500000.pcie/pci0000:00/0000:00:00.0/0000:01:00.0/usb1/1-1/1-1.3/1-1.3:1.3/0003:0D8C:0014.0001/input/input0
    hid-generic 0003:0D8C:0014.0001: input,hidraw0: USB HID v1.00 Device [C-Media Electronics Inc. USB Audio Device] on usb-0000:01:00.0-1.3/input3   

Plug the audio adapter into the USB port. You should see it identified:

    ls usb
    Bus 002 Device 001: ID 1d6b:0003 Linux Foundation 3.0 root hub
    Bus 001 Device 003: ID 0d8c:0014 C-Media Electronics, Inc. Audio Adapter (Unitek Y-247A)
    Bus 001 Device 002: ID 2109:3431 VIA Labs, Inc. Hub
    Bus 001 Device 001: ID 1d6b:0002 Linux Foundation 2.0 root hub
    
Update the system:    
    
    sudo apt-get update -y
    sudo apt-get upgrade -y
    sudo reboot
    
Configure the alsa to use the adaper (1) instead of the default internal audio (0):

    sudo nano /usr/share/alsa/alsa.conf
    
    defaults.ctl.card 1
    defaults.pcm.card 1

Try recording 60 seconds of CD quality audio

    arecord -d 60 -f cd -t wav filename

## Install the software

TODO

## Strategy
Use crontab to record 60 second sound files (11 MBytes) every minute. Then have
another crontab script run that through the train detector (this program) and 
write the results back out. The results are whether a given 1 second chunk
contains a horn, and the 12 numbers associated with the binned FFT.
Then use a MVC framework (I use Spring Boot) to handle the persistence and
presentation layers of the app.

You may have to adjust the detector settings in the program, based on how
loud train horns are where you live.
