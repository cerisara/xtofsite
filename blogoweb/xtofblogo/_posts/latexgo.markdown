---
categories: go
date: 2010/06/29 15:25:00
title: Go and Latex
---

Latex is wonderful to typeset nearly anything: texts, maths,
drawings (PGF/TIKZ), dependency trees (tikzdependency),
musical score (well, not really latex, but lilypond has the same spirit)...

... and even Gobans (the best-ever board game that exists).
Just include the package "igo" in your latex document, and here you go,
you can just write simple lines as:

\white{b4,c4,d4,e4,f4,g3,g2,c3}  
\black{b3,b2,c2,d3,e3,f3,f2}  
\shortstack{  
\showgoban\\\\  
White to kill  
}

to show up a goban.

