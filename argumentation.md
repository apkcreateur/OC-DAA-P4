# Pourquoi utiliser Java plutôt que Kotlin ?

## Contre Kotlin

Les principaux freins a l'utilisation de Kotlin par rapport a Java dans 
nos services sont :  

  - actuellement nous n'avons pas les compétences en interne et nous 
  manquons de temps et de ressources pour former un développeur 
  (ou que celui-ci se forme par lui-même)
  - ce problème risque d'être récurrent dans le temps pour le maintien 
  à jour de l'application qui ne devrait pas être fait par une seule 
  personne. Je m'explique avec un exemple : si nous n'avons qu'un seul 
  développeur qui connait le Kotlin, mais que celui-ci venait a être 
  indisponible (travail sur un autre projet, en congé, départ 
  d'entreprise ...) alors il faudrait qu'un autre développeur se forme 
  en urgenge
  - Kotlin est un langage encore jeune ce qui signifie que sa 
  communauté est "petite" et n'est "pas encore assez expérimentée". Il 
  est possible que des libs spécifiques ne soient pas encore présentes

## Pour Java

Alors qu'avec Java :  

  - nous n'aurons plus de problème de compétences en interne car plus 
  de la moitié de nos développeurs maîtrise Java. De plus si nous 
  avions un départ, il est plus facile de trouver un remplaçant Dev 
  Java qu'un Dev Kotlin sur le marché du travail
  - la communauté Java est grande et expérimentée. Nous n'aurons aucun 
  mal a trouver de l'aide si nécessaire
  - la fiabilité et le contenu des libs Java n'est plus a démontrer, 
  elles ont été éprouvées et étoffées au fil des années

## Conclusion

Je préconise donc l'utilisation de Java pour commencer. Plus tard rien 
ne nous empêchera de migrer sous Kotlin lorsque nous aurons les 
compétences en interne. Cela pourra se faire petit a petit étant donné 
que Java et Kotlin peuvent cohabiter au sein d'un même projet (Kotlin 
se basant aussi sur la JVM).  
