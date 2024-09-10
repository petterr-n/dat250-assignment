### REPORT ###
## REST API implementation ##
The REST API is built by further developing the Spring boot project we started on in expass 1. 
Here, by following the Domain Models provided by the assignment implementation details, I have split the functionality into different packages.
One package for the controllers, containing everything that is used for the programming interface; all the CRUD methods that can be interacted with.
Another package contains the models, which consists of all the parts that can be created and edited by the interacting with the API (Users, Poll, etc.).
The third and last package is the Manager which acts like a repository to save objects, this is at the moment only using an in memory database, and stores objects made in hashmaps.

The REST API for this project was initially created by following the Test-Driven approach, by creating test scenarios first, and implementing the code to handle the scenarios later.
The tests created for test automation is provided in an HTTP file contained in the test folder, this file is in the "client" directory. They are meant to run in sequence from top to bottom.
Most of the testing while creating the API was performed using POSTMAN, which is an application for easily sending http requests to a server either hosted locally or on the internet.

I have stumbled upon some issues while implementing the API. The most annoying one has to do with automatically generating IDs for Users, Polls, Votes and Vote-Options.
At the moment the IDs are not correctly generated and I am getting null values whenever an object is created. This has made it harder to test some of the API implementations, especially multiple tests chained together. 
As a result the automating of a test scenario has not been implemented yet, and has been put on a bit of a hold while I try to get the unique IDs to work. 
There are some issues with the nested objects, like the VoteOptions made for each poll, not being generated or passed along with the poll correctly, could be a issue with how the Json is parsed, or how the Json POST bodies are set up.

To summarize the assignment; The REST API for the Voting/Poll application has been created following a domain model showing the relationships between the models, and following a test driven approach.
There are some issues with the current implementation, leading to the test automation task not being completed, and some of the api not working as expected.

I will try to finish the implementation on time, but if I don't I will create a new branch with the finished iteration.

UPDATE: The necessary implementation of the backend is finished with the correct ID generation, and a test scenario for the api has been made and can be found in the src/test directory. This was finished after the deadline, and can therefore be found in the A2 branch.

