#!/bin/tcsh

blogofile build -s xtofblogo/

scp -r xtofblogo/_site/* talc1:/var/www/users/cerisara/

