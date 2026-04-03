# TRIPLAN

## Description

TRIPLAN est une application web de planification et de recommandation de voyages intelligents et responsables.
Elle permet aux utilisateurs de construire un voyage personnalisé en combinant :

- des recommandations adaptées à leur profil
- la planification d'activités et d'itinéraires optimisés
- le calcul de l'empreinte carbone du voyage

Application déployée à l'adresse suivante : http://172.31.253.128/

---

## Fonctionnalités

### Recommandation de voyages — Célia KOUFFI
- Génération de recommandations de voyages en fonction du profil utilisateur
- Visualisation des voyages recommandés via une interface dédiée

### Planification et itinéraires — Roumayssae EL KENZ

**R2 — Planification des activités**
- Visualiser son itinéraire sur une carte interactive
- Renseigner sa durée de séjour
- Obtenir un programme d'activités généré automatiquement via un algorithme du plus proche voisin
- Consulter son planning quotidien d'activités (mono-jour et multi-jours)
- Supprimer une activité de son planning

**R3 — Gestion des coûts**
- Consulter le coût total estimé de son voyage
- Visualiser la répartition des coûts par catégorie (activités / hébergement)
- Modifier un élément du voyage pour voir l'impact sur le coût
- Comparer le coût estimé avec son budget initial

### Empreinte carbone — Khadija IDRISSI
- Calcul de l'empreinte carbone pour les trajets, l'hébergement et les activités

---

## Technologies

| Couche | Technologie |
|--------|-------------|
| Frontend | React |
| Backend | Java Spring Boot |
| Base de données | PostgreSQL |
| CI/CD | Jenkins |
---

## Équipe

| Membre             | Responsabilité |
|--------------------|---------------|
| Celia KOUFFI       | Recommandation et analyse du profil utilisateur |
| Roumayssae EL KENZ | Planification, itinéraires et coûts |
| Khadija IDRISSI    | Calcul de l'empreinte carbone |

---