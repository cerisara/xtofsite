+++
date = "2016-06-26T18:02:45+02:00"
draft = false
title = "About this site"

+++

Nowadays, personal websites and blogs are a bit depreciated, because
everybody is posting on facebook. But a personal website is *yours*, you
control all of it from start to end. And so, you can really be proud of it,
just like when you craft a small statue with your hands.
It's just unique, it's you.

Over the years, I've tested many, many different solutions to build a website:
in particular pure html (but it's really too much work, isn't it ?)
and cms/wordpress (but you're not in control, it's heavy, no git, full of
vulnerabilities...).
And I've come to a single conclusion: there is really nothing like static web
site generators: sites are fast, not vulnerable, modern-looking, git-friendly,
and you're the boss. period.

I started with the old "blogofile", and switched to *Nikola* after one year:
Nikola is really good, but after 2 years, I think it's time to change, and
*Hugo* looks best.

A short recipe, if you're a linux addict:

* Install hugo

    sudo dkpg -i hugo.deb

* Create your site directories:

    hugo new site mysite

* Choose and install a theme:

    cd mysite/themes
    git clone https://github.com/vjeantet/hugo-theme-casper

* Add images in static/images/
* Add a post with

    hugo new post/mypost.md
    hugo undraft post/mypost.md

* Compile and deploy

    hugo --theme=hugo-theme-casper
    scp/sft to you site host (github pages is your friend here)

Enjoy !

I also recommend you to version your site code with git.

