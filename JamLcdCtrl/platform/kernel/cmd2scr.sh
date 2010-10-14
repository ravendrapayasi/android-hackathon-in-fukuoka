#!/bin/sh
mkimage -A arm -O linux -T script -C none -a 0 -e 0 -n "uImage" -d ./boot.cmd ./boot.scr
