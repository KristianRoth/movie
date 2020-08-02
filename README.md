## Elokuvalippujen varausjärjestelmä

### Devaus ympäristön asennus
```bash
docker-compose up 
```
Palvelu vastaa osoitteesta `localhost:8080`  
Tietokantaa pystyy katsomaan `localhost:8081`

### Rajapinnan kuvaus

#### Teatteri rajapinta
`GET` `/api/auditoriums` Listaa luodut teatterit  
`POST` `/api/auditoriums/make` Luo uuden teatterin.   Parametrit `auditoriumName` `numberOfSeats`  
`GET` `/api/auditoriums/{auditoriumId}` Hae teatteri tietyllä id:llä  
`GET` `/api/auditoriums/{auditoriumId}/seats` Hae teatterin istumapaikat  

#### Näytös rajapinta
`GET` `/api/screenings` Listaa näytökset. Parametrit `startTime`, `endTime` ja `upComing`  
`POST` `/api/screenings/make` Luo uuden näytöksen. Parametrit `auditoriumId`, `name`, `startTime` ja `endTime`  
`GET` `/api/screenings/{screeningId}` Hae näytös tietyllä id:llä  
`GET` `/api/screenings/{screeningId}/seats` Hae vapaat/varatut paikat näytöksestä. Parametrit `getReserved`  
`GET` `/api/screenings/{screeningId}/resevations` Hae näytöksen varaukset  
`POST` `/api/screenings/{screeningId}/resevations/make` Luo varaus näytökseen. Parametrit `seatIds`  

#### Huomioita
Kaikki ajat käyttävät ISO DATE_TIME formattia `2020-07-30T14:30:00.000000`

