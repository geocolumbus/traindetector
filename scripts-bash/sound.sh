#!/usr/bin/env bash
rename s/.raw.wav.loading/.raw.wav/ /var/sound/*
filename=/var/sound/$(date "+%s").raw.wav.loading
/usr/bin/arecord -d 60 -f cd -t wav ${filename}
