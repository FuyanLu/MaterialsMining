
The problem is to find all possible semiconductors protected by non-symmorphic symmetries.

We need mine out all possible materials from existing databases (https://icsd.fiz-karlsruhe.de/search/basic.xhtml;jsessionid=13FB8E83564E2584EC4C61AB7C05BE64).

The necessary condition is the matching between the number of electrons and space group.There are around 100 space groups, each of which contains 2,000-20,000 materials. We need to check all of those materials.

Mining.java: This file is to screen all possible materials. The difficulty of this problem is that we need extract informations based on chemical formula string. The chemical formula string is difficult to address because of brackets, fractional numbers and default numbers. 

Matching.java: This file is to match the screened materials from last file with existing 2D materials databases. 

  

