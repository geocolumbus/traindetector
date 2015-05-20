# www.traindetector.com

This project listens for train whistles near my home and logs them in a MySQL database. A UI presents the data in a useful format.

## Hardware:
1. Raspberry Pi 2 with 32 GB memory card.
2. Plugable® USB Audio Adapter with 3.5mm Speaker/Headphone and Microphone Jacks
   (Black Aluminum; C-Media CM108 Chip).
3. Sony ECM-DS70P Electret Condenser Stereo Microphone.

## To make the audio input work, see:
https://learn.adafruit.com/downloads/pdf/usb-audio-cards-with-a-raspberry-pi.pdf

## Recording 60 seconds of CD Quality audio on the Raspberry Pi
arecord -d 60 -f cd -t wav filename

## Strategy
Use crontab to record 60 second sound files (11 MBytes) every minute. Then have
another crontab script run that through the train detector (this program) and 
write the results back out. The results are whether a given 1 second chunk
contains a horn, and the 12 numbers associated with the binned FFT.
Then use a MVC framework (I use Spring Boot) to handle the persistence and
presentation layers of the app.

You may have to adjust the detector settings in the program, based on how
loud train horns are where you live.
