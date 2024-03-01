# PG3402_2024V_KN41

## Starting the services
1. Make sure docker engine is installed and running.
2. cd into src/modules.
3. run "docker compose create" to create the containers.
4. run "docker compose start" to start the containers. (Might take a while for all containers to start)
5. then "docker compose logs -f logs" to view the "important" logs (prevents CONSUL info dump).
6. open http://localhost:3000 to view the very basic frontend created in Vue.

## Arbeidskrav
### primary user stories

1. The user should be able to create an account and log in.
2. The user should be able to request an integer division equation and submit.
3. The user should be able to view analytics of their own performance.
4. A guest user should be able to view their session analytics?

### arbeidskrav architecure 
![arbeidskrav_architecture.png](images%2Farbeidskrav_architecture.png)
## Exam user stories

1. [x] The user should be able to request an integer multiplication challenge and submit.
2. [x] the user should be able to request an integer division challenge and submit.
3. [x] the division challenge service should use the multiplication service to generate the challenge.
4. [x] the user should be able to view how many challenges have been generated.
5. [ ] the user should be able to view how many challenges have been solved.
6. [ ] the user should be able to log in and view their own performance.

### architecture
![exam_architecture.drawio.png](images%2Fexam_architecture.drawio.png)

## Why the differences between arbeidskrav and exam
1. I started creating the challenge services, and thought only having a multiplication service was very little.
thus I started on a division service, because division is basically the opposite of multiplication,
and this is how far I got with my limited time...
2. I considered investing more time in analytics, but then I realized most of it would be done in the frontend,
which isn't "really" relevant to this course in my opinion.

## Http requests I didn't have time to implement in the frontend
### multiplication
- http://localhost:8080/multiplication/api/v1/get/all - returns a list of all multiplication challenges
- http://localhost:8080/multiplication/api/v1/get/{userName} - returns a list of all multiplication challenges for a specific userName

### division
- http://localhost:8080/division/api/v1/get/all - returns a list of all division challenges
- http://localhost:8080/division/api/v1/get/{userName} - returns a list of all division challenges for a specific userName

