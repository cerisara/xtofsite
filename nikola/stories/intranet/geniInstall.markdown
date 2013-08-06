<!---
.. link: 
.. description: 
.. tags: intranet, geni
.. date: 2013/05/24 17:25:15
.. title: GenI installation
.. slug: geniInstall
-->

#### GenI Installation (Ubuntu 11.10 64 bits) ####

These are the steps I followed to do a recent clean installation of GenI. These
instructions might not be 100% accurate, so please correct them if you find any
mistakes.

##### Install GHC #####

   1. Install the libgmp.so.3 library. libgmp3c2 installs the 64 bits version
      and lib32gmp3 the 32 bits. I'm not quite sure in which situation you
      would need the 32 bits version, but It's happened: sudo apt-get install
      libgmp3c2 lib32gmp3
   2. Install Happy: sudo apt-get install happy
   3. Install stow: sudo apt-get install stow
   4. Download ghc-6.12.3 binary packages: http://www.haskell.org/ghc/
      download_ghc_6_12_3#binaries
   5. Uncompress the file: tar jxvf ghc-6.12.3-x86_64-unknown-linux-n.tar.bz2
   6. Create a "stow" directory in /usr/local: sudo mkdir /usr/local/stow
   7. Configure the ghc to install in a subdirectory of the stow directory: cd
      ghc-6.12.3 &amp;&amp; ./configure --prefix=/usr/local/stow/ghc-6.12.3
   8. Install it: sudo make install
   9. Step for future problems prevention: sudo mkdir -p /usr/local/bin
  10. Create symlinks: cd /usr/local/stow &amp;&amp; sudo stow ghc-6.12.3
  11. Install cabal-install: sudo apt-get install cabal-install (I'm not 100%
      sure about this step)
  12. Add the following commands to your .bashrc file:
  
        # These environment variables are only necessary if you don't use the defaults
        # shown.
        GHC_CONFIG_DIR="$HOME/.ghc-config"
        CABAL_USER_DIR="$HOME/.cabal"
        
        # Add the paths for ghc-config, ghc, and cabal-installed binaries. Use
        # the actual paths if you don't set the variables above.
        PATH=$PATH:$GHC_CONFIG_DIR/ghc/bin    # ghc
        PATH=$PATH:$CABAL_USER_DIR/bin        # cabal and cabal-installed binaries
    
##### Mozart Installation (for 64-bits platforms) #####

32 bits platforms: I think you can just install the Ubuntu packages for "Mozart Programming System".

   1. sudo apt-get install autoconf tk-dev tcl-dev build-essential libc6-dev
      g++-multilib ia32-libs lib32z1-dev lib32gmp3-dev libgdbm-dev flex bison
      emacs autoconf (You may have problems with i386/amd86, try to add various
      architectures, for example: sudo dpkg --add-architecture i386 &amp;&amp;
      sudo apt-get update &amp;&amp; sudo apt-get install ia32-libs)
   2. mkdir ~/.oz
   3. mkdir -p ~/.dev/mozart
   4. cd ~/.dev/mozart
   5. git clone git://github.com/mozart/mozart.git
   6. mkdir build
   7. cd build
   8. ../mozart/configure --prefix=$HOME/.oz --enable-contrib-gdbm=no
   9. Edit ~/.dev/mozart/build/platform/tools/gump/ozflex/Makefile and change
      @BUILDTOP@ for $(HOME)/.dev/mozart/build and @SRCTOP@ for $(HOME)/.dev/
      mozart/mozart
  10. make &amp;&amp; make install
  11. amend and append the below to your ~/.bashrc file
         1. export OZHOME=$HOME/.oz
         2. export PATH=$PATH:$OZHOME/bin:$OZHOME/1.4.99/bin
  12. either reload your bash console or execute source ~/.bashrc
  13. Now, we need to disable a "sudo" feature that changes your PATH and will
      prevent you to run "sudo ozmake". To do so:
         1. sudo visudo
         2. Then, if there is no "Defaults secure_path=..." add the line
            Defaults !secure_path. If there is a line defining secure_path,
            change it to match Defaults !secure_path
  14. mkdir ~/.dev/mozart-stdlib
  15. cd ~/.dev/mozart-stdlib
  16. git clone git://github.com/mozart/mozart-stdlib.git
  17. mkdir build
  18. cd build
  19. ../mozart-stdlib/configure --prefix=$HOME/.oz
  20. make &amp;&amp; make install
  
###### XMG Installation ######

   1. Install mozart (instructions above)
   2. Download Select library package file from http://www.mozart-oz.org/mogul/info/duchier/select.html (eg. duchier-select_ _1.3.0_ _source_ _1.8.pkg)
   3. Go to your Downloads directory and run: sudo ozmake --install --package=duchier-select_ _1.3.0_ _source_ _1.8.pkg
   4. Under an appropriate directory: svn checkout https://subversion.cru.fr/xmg
   5. cd xmg/trunk/MGCOMPILER
   6. ozmake --upgrade
   7. cd ../XMG-TOOLS/CHECKER
   8. ozmake --upgrade
   9. cd ../SELECTOR
  10. ozmake --upgrade
  11. cd ../VIEWER
  12. ozmake --upgrade
  13. sudo apt-get install xsltproc
  14. Install lex2all
  
    1. Follow the GHC installation procedure to install the ghc-7.0.1 in /usr/local/stow/ghc-7.0.1 (but #don't execute# the stow ghc-7.0.1 command)
    2. Checkout semtag if you don't have it and go to semtag/trunk/english/geni/bundled/lex2all
    3. Edit lex2all.cabal and change base < 4 to base >= 4
    4. cabal update &amp;&amp; cabal install --with-compiler=/usr/local/stow/ghc-7.0.1/bin/ghc
            
##### Install GenI #####

   1. Download GenI-0.21 from either scp -r talaris@dombras:~/GenI ~/Downloads/
      GenI-0.21 OR wget http://talc1.loria.fr/users/gkruszew/GenI-0.21.tar.gz
   2. cd ~/Downloads/GenI-0.21
   
    1. For 32/64 bits:
         
      1. cabal install Cabal-1.14.0
      2. cabal install --only-dependencies
      3. runhaskell Setup configure --user
      4. runhaskell Setup build
      5. runhaskell Setup install
               
    2. For 32 bits only:
         
      1. cabal install
               
   3. Install the GUI
   
    1. sudo apt-get install libwxgtk2.8-dev libghc-glut-dev graphviz
    2. cabal install wxcore-0.12.1.6
    3. cabal install --reinstall -fgui
