# Pixel-Rem-Convertor
In the process of web development, pixels are more intuitive to use and test with in the beginning. Yet, to make fonts responsive to the size of windows, we need to use rem instead. This Java Program provides quick px to rem conversion to aid you in the process of web developement. It takes in a file and output a file with pixels converted to rem.

# Commands
### javac PxConvert.java
This command compiles the Java program

### java PxConvert inputFile outPutFile
This commd runs the conversion program. It takes in the ***inputFile*** and outputs a file with pixels converted to rems to ***outPutFile***.

# Functionalities
- Avoid px-to-rem conversion when the pixel is part of media query\
e.g   @media (min-width: 320px) { ... }

- Handles conversions of pixel to rem including scenarios with multiple pixels on a single line\
e.g border-width: 2px 3px 1px 2px

- Trailing zeros of converted pixels to rem are removed\
e.g  1.0rem --> 1rem
