# MonokromCL

![Screenshot](screenshots/jeanne_moreau_la_baie_des_anges_dithered_1516519350150.jpg)
A command-line Java app to generate monochrome dithered images

```
Usage:
-d, -dither - dither algorithm (see below)
-s, -source - path to source image
-t, -target - path to save file
-th, -threshold - default is 128
-svg - output to SVG
?, -help - display this help

Available dither types:
  ordered2By2Bayer
  ordered3By3Bayer
  ordered4By4Bayer
  ordered8By8Bayer
  floydSteinberg
  jarvisJudiceNinke
  sierra
  twoRowSierra
  sierraLite
  atkinson
  stucki
  burkes
  falseFloydSteinberg
  simpleLeftToRightErrorDiffusion
  randomDithering
  simpleThreshold
```
