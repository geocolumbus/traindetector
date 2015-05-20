#!/usr/bin/env bash
for f in /var/sound/*.raw.wav
do
  g=${f/.raw.wav/};
  /var/java/project1/c/a.out "$g" && rename s/.raw.wav/.processed.wav/ "$f";
done && for f in /var/sound/*.dat
do
  g=${f/-??.dat/.processed.wav};
  rename s/.processed.wav/.wav/ "$g";
done && rm /var/sound/*.processed.wav
