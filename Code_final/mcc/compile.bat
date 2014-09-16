echo "compiling $1.c"
avr-gcc  -Wall -O2 -mmcu=atmega8 -Wl,-Map,$1.map -o $1 $1.c

avr-objcopy -j .text -j .data -O ihex $1 $1.hex

avr-objdump -S $1 > $1.lst

