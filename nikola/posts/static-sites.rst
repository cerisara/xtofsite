.. title: Why you MUST use static website generators
.. slug: static-sites
.. date: 2016-02-25 16:16:25 UTC+01:00
.. tags: 
.. category: 
.. link: 
.. description: 
.. type: text

Creating a website or blog can be done in various ways: one of the most famous solution is to use a
Content Management System (CMS), such as WordPress, Joomla, and many others.
Similar solutions may be to use specialised dynamic wiki-like or blog-like software.
But I strongly recommend you to *not* use any of the previous solutions, because they have several important drawbacks:

- They are heavy: they require a lot of libraries and additional software, which take a lot of space, are a source of version conflicts, etc.
- They are sensible to attacks, and you have to upgrade the CMS every 3 months
- If the CMS breaks down, you just loose everything

Nowadays, there is just one serious alternative: statically-generated web sites. After having spent years testing all the other solutions,
I finally came down to this one, and I'm not gonna change for long, because they're just great:

- They're very easy to start with and straight look good and modern, thanks to modern CSS like boostraps.
- They're super-fast, because in the end they're just composed of static html and css files that any browser handles smoothly.
- They're very secure and robust to attacks, because of the very same reason: static html can not be attacked, javascript is pretty robust and is usually automatically upgraded anyway. So you don't need to worry about vulnerabilities of your website: it has none !
- They can be seen on *all* browsers, thanks to the efficient boostrap css. They're mobile-friendly, with all your menus that collapse down into a single button to save space on small screen, and the text that is nicely reshaped. This is very important nowadays, as more and more people open your site on mobile. There are still tons of sites I love, such as arxiv.org, which pitifully are not mobile-friendly, and they'll have to support this in the near future. So, for your site, do it right now, it's so easy with static web sites !
- The site source is lightweight (just a few text files) and you can have it where you want: if the web server machine just breaks, no worry, because you can just re-copy the source code of your site elsewhere. If you're familiar with versioning systems, I strongly recommend you to keep this source code in GIT; you'll then have all of its history at hand, you can edit it collaboratively with your colleagues from anywhere, distribute it, share it...
- You don't loose any feature as compared to dynamic web sites: comments systems, blogs, social networks, analytics... are all supported by modern site compilers.
- Static websites is the way a number of giants of the internet are now using, such as github.io

So you want to try ?
It's very easy, there are several static web site compilers around. I recommend two of them:

- If you're a developer, try jekyll and eventually publish it on github.io
- But I like very much `Nikola <https://getnikola.com/>`__ : it's very nice and use it for all of my sites.

Just download and install one of these two software, read the quickstart guide, and your website is ready in minutes.

