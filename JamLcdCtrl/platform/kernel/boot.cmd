mmc init
fatload mmc 0 0x80300000 uImage
setenv bootargs console=ttyS2,115200n8 noinitrd init=/init root=/dev/mmcblk0p3 rootfstype=ext3 rw rootwait nohz=off
bootm 0x80300000
