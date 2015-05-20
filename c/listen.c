/*
** This is a simple train horn detector that I use on a Raspberry Pi 2
** to detect and log train horns. Getting all of this working on a
** Raspberry Pi is a moderately complicated task.
** 
** You need some libraries installed on the Pi to use this program:
** 1. http://www.mega-nerd.com/libsndfile/
** 2. http://www.fftw.org/
** 
** You can then compile the binary:
** gcc listen.c -lsndfile -lfftw3 -lm -std=c99
**
** Hardware:
** 1. Raspberry Pi 2 with 32 GB memory card.
** 2. Plugable® USB Audio Adapter with 3.5mm Speaker/Headphone and Microphone Jacks
**    (Black Aluminum; C-Media CM108 Chip).
** 3. Sony ECM-DS70P Electret Condenser Stereo Microphone.
**
** To make the audio input work, see:
** https://learn.adafruit.com/downloads/pdf/usb-audio-cards-with-a-raspberry-pi.pdf
**
** Recording 60 seconds of CD Quality audio on the Raspberry Pi
** arecord -d 60 -f cd -t wav filename
**
** Strategy:
** Use crontab to record 60 second sound files (11 MBytes) every minute. Then have
** another crontab script run that through the train detector (this program) and 
** write the results back out. The results are whether a given 1 second chunk
** contains a horn, and the 12 numbers associated with the binned FFT.
** Then use a MVC framework (I use Spring Boot) to handle the persistence and
** presentation layers of the app.
**
** You may have to adjust the detector settings in the program, based on how
** loud train horns are where you live.
**
**
** Copyright (c) 2015 GEORGE CAMPBELL
** 
** Permission is hereby granted, free of charge, to any person obtaining a copy
** of this software and associated documentation files (the "Software"), to deal
** in the Software without restriction, including without limitation the rights
** to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
** copies of the Software, and to permit persons to whom the Software is
** furnished to do so, subject to the following conditions:
** 
** The above copyright notice and this permission notice shall be included in
** all copies or substantial portions of the Software.
** 
** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
** IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
** AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
** LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
** OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
** THE SOFTWARE.
*/

#include    <stdio.h>
#include    <stdlib.h>
#include    <string.h>
#include    <math.h>

#include    <sndfile.h>
#include    <fftw3.h>

#define     SAMPLES_PER_SEC 44100
#define     BUFFER_LEN      SAMPLES_PER_SEC * 2
#define     BIN_SIZE        100
#define     BIN_MAX         1200
#define     THRESHOLD       0.020

/*
** Look for patterns in the frequency spectrum of the sound
** There are 12 bins, each is 100 Hz wide. We only look at the first 1200 Hz of the spectrum.
*/
void writeFftBins(double *fftBin, char *filename, int second, double magnitude) {
        char outDataFile[128];
        char secondStr[8];
        int isTrain = 0;
        int isPlane = 0;
        int isRain = 0;
        int isThunder = 0;
        int shouldIgnore = 0;

        strcpy(outDataFile, filename);
        sprintf(secondStr, "%.2d", second);
        strcat(outDataFile, "-");
        strcat(outDataFile, secondStr);
        strcat(outDataFile, ".dat");

        // Ignore signals with a spike in the first bin (static)
        if (fftBin[0] == 1) {
                shouldIgnore = 1;
        }

        // Simple rain detector - rain is loud because the microphone is near the roof
        else if (magnitude > 0.06 && fftBin[9] > 0.2 && fftBin[10] > 0.2 && fftBin[11] > 0.2) {
                 isRain = 1;
        }

        // Simple plane detector
        else if (magnitude < 0.05 && fftBin[0] < 0.9 && fftBin[0] > 0.7 && fftBin[1] > 0.7 && fftBin[2] > 0.7 && fftBin[3] > 0.4) {
                isPlane = 1;
        }

        // Simple train detector
        else if (magnitude < 0.05 && fftBin[0] < 0.9 && (fftBin[3] > 0.99 || (fftBin[3] > 0.80 && (fftBin[4] > 0.99 || fftBin[5] > 0.99))) && fftBin[10]<0.25 && fftBin[11]<0.25) {
                isTrain = 1;
        }

        // Write guess as to what the sound is and the frequency spectrum to a file
        if (shouldIgnore == 0) {
                FILE *f = fopen(outDataFile, "w");
                if (f == NULL)
                {
                        printf("Error opening %s!\n", outDataFile);
                        exit(1);
                }

                fprintf(f, "%0.4f\n", magnitude);

                if (isThunder == 1) {
                        fprintf(f, "%s", "Thunder\n");
                } else if (isRain == 1) {
                        fprintf(f, "%s", "Rain\n");
                } else if (isTrain == 1) {
                        fprintf(f, "%s", "Train\n");
                } else if (isPlane == 1) {
                        fprintf(f, "%s", "Airplane\n");
                } else {
                        fprintf(f, "%s", "\n");
                }

                for (int i = 0; i < BIN_MAX / BIN_SIZE; i++) {
                        fprintf(f, "%.4f\n", fftBin[i]);
                }
                fclose(f);
        }
}

/*
** Normalize the fftBin so the maximum number is 1.0
*/
void normalizeFft(double *fftBin) {
        double max = 0;
        for (int i = 0; i < BIN_MAX / BIN_SIZE; i++) {
                if (fftBin[i] > max) {
                        max = fftBin[i];
                }
        }
        for (int i = 0; i < BIN_MAX / BIN_SIZE; i++) {
                fftBin[i] /= max;
        }
}

/*
** Perform a Fourier Transform on the "loud second" of data.
*/
void fft(double *data, double *fftBin) {
        fftw_complex *in, *out;
        fftw_plan p;
        int i;
        int binCounter;
        int bin;
        double magnitude;
        double magnitudeSum;

        in = (fftw_complex*) fftw_malloc(sizeof(fftw_complex) * SAMPLES_PER_SEC);
        for (i = 0; i < SAMPLES_PER_SEC; i++) {
                in[i][0] = data[i];
                in[i][1] = 0.0;
        }

        out = (fftw_complex*) fftw_malloc(sizeof(fftw_complex) * SAMPLES_PER_SEC);

        p = fftw_plan_dft_1d(SAMPLES_PER_SEC, in, out, FFTW_FORWARD, FFTW_ESTIMATE);
        fftw_execute(p);
        fftw_destroy_plan(p);

    // Take the FFT, which has one value for every one hertz (44100 total), and sum the values into bins
    // that are 100 hz wide. We only care about the first 1200 hz - so 12 bins total.
        binCounter = 0;
        magnitudeSum = 0.0;
        bin = 0;
        for (i = 0; i < SAMPLES_PER_SEC; i++) {
                magnitude = sqrt(out[i][0] * out[i][0] + out[i][1] * out[i][1]);
                magnitudeSum += magnitude;
                if (binCounter++ == BIN_SIZE) {
                        fftBin[bin / BIN_SIZE] = magnitudeSum;
                        binCounter = 0;
                        magnitudeSum = 0.0;
                        bin += BIN_SIZE;
                }
                if (bin > BIN_MAX) {
                        break;
                }
        }

        fftw_free(in); fftw_free(out);
        return;
}

/*
** Main program
*/
int main(int argc, char *argv[]) {
        SNDFILE *file;
        SF_INFO sfinfo;
        int readcount;
        static double data [BUFFER_LEN];
        double *fftBin = malloc(sizeof(double) * BIN_MAX / BIN_SIZE);
        int second = 0;
        char filename[128];
        char infile[128];

        // Calculate the input filename
        if (argc > 1) {
                strcpy(filename, argv[1]);
                strcpy(infile, filename);
                strcat(infile, ".raw.wav");
        } else {
                printf("No filename specified.\n");
                return 0;
        }

        // Try to open the input file, 60 seconds of sound
        if (! (file = sf_open (infile, SFM_READ, &sfinfo))) {
                printf ("Error : Not able to open %s\n", infile);
                puts (sf_strerror (NULL));
                exit(1);
        };

        // Process the file as 60, 1 second chunks of 44,100 double pairs (left and right channel)
        while ((readcount = sf_read_double (file, data, BUFFER_LEN))) {

                double max = 0, maxl = 0, maxr = 0;

                for (int j = 0; j < BUFFER_LEN; j += 2) {
                        double left = fabs(data[j]);
                        double right = fabs(data[j + 1]);

                        maxl = left > maxl ? left : maxl;
                        maxr = right > maxr ? left : maxr;

                        max = maxl > max ? maxl : max;
                        max = maxr > max ? maxr : max;
                }

                // If the 1 second chunk contains a noise louder than a threshold, process it.
                // Note that we are only looking at the left channel. The right channel is ignored.
                if (max > THRESHOLD) {
                        fft(data, fftBin);
                        normalizeFft(fftBin);
                        writeFftBins(fftBin, filename, second, max);
                }
                second++;
        };

        // TODO - why do these cause an error on the Pi but work fine on my Mac? Doesn't matter
        // because the program works fine without them...
        //free(fftBin);
        //sf_close(file);

        return 0;
}
