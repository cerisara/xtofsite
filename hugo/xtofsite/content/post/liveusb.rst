+++
markup= "md"
date = "2016-01-11T18:02:45+02:00"
title= "Persistent live USB"
description= ""
+++

How to simply create a bootable USB stick with a live Ubuntu 14 and persistency, UEFI-compatible,
and all of this without ever formatting or repartitionning the disk !

I don't like repartitionning or reformating USB sticks, because they tend to be quite "fragile" these days:
when you buy them, they're usually pre-partitionned with one big vfat partition.
So let's keep with that, and install a bootable live Ubuntu with persistency on it.
Thus, you'll still be able to use you USB stick on Windows, and in your favourite car CD-player device.

In short, you'll have to

* install GRUB on your USB stick
* copy the latest Ubuntu 14 iso on your USB stick
* create a casper-rw file in the root of your USB
* edit the grub configuration to boot from the iso and be persistent

Note that your USB may not boot with "legacy" boot mode, but at least it's booting just fine with UEFI mode !
So you may have to edit the BIOS in order to

* Disable "secure boot"
* Enable UEFI boot instead of legacy boot

Create the persistent file
--------------------------

Must be done on a different linux system than the one that is on the USB key:

    dd if=/dev/zero of=/path/to/casper-rw bs=1M count=512 

    mkfs.ext3 /path/to/casper-rw

In your grub.txt on your USB key, edit your boot by adding the persisten keyword, to get something like:

    linux (loop)/casper/vmlinuz boot=casper iso-scan/filename=$isofile quiet splash noprompt persistent --


