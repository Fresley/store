To run application just run SpringBoot StoreApplication class.  
To communicate with endpoints, use e.g. Postman.
1) http://localhost:8080/store/rent
PUT - method with "customer" header and JSON body e.g.
   [
   {
   "videoName" : "Spider Man",
   "daysToRent": 5
   },
   {
   "videoName" : "Matrix 11",
   "daysToRent": 1
   },
   {
   "videoName" : "Spider Man 2",
   "daysToRent": 2
   },
   {
   "videoName" : "Out of Africa",
   "daysToRent": 7
   }
   ]  

This will return calculated fee for renting all films for mentioned days,  
as also create entry to hold information about this rent for future return.
2) http://localhost:8080/store/return
PUT - method with "customer" header and JSON body e.g.
   [
   {
   "videoName" : "Spider Man"
   },
   {
   "videoName" : "Matrix 11"
   },
   {
   "videoName" : "Spider Man 2"
   },
   {
   "videoName" : "Out of Africa"
   }
   ]  
This will return rented videos, calculated their possible additional fees if necessary  
and return fee value. Also update in db will be made to mark those videos as returned.
3) http://localhost:8080/store/videos
GET - method will return list of all films in store.  


There were couple trade-offs made to simplify solution because time-costs. F.e no db time zone was set,  
so date time is taken for machine. There shouldn't be possibility to rent same videos with  
same customer if previous ones were not returned. If rent days are less than maximum possible  
for initial period, still full price will be charged. But for extra days within it, no extra  
fee will be charged (different approach than in task example, but I noticed it to late).
There is no Oauth implementation at all, customer to simplify things is taken from header,  
still no time to implement simple oauth for build in credts. Also same video can be rented  
multiple times at the same times, there is no 'rented' flag on purpouse. Design was made for easy further  
development and adding additional functionalities what is visible in code. For tests, as mentioned in  
task description, there is only view of structure and approach of testing, no full test coverage,  
but it should give good overview what should be accomplished. Possibly there are more  
things worth to mention, but those can be discused individualy.
