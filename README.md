# NFCAccessRest

REST API used to access the database for the NFC system.

Following URL are defined :

http://serverip:port/RestTest/webapi/nfcaccess/ 

/get/{uid} : Produces raw text containing rank of user or -1 if not found

/get/all : Produces JSON array containing the users in the database

/post/ : POST request. Consumes JSON body with following fields :
	*uid : String
	*name : String
	*lastName : String
	*rank : Integer

	Produces JSON body with uid added and result field.

/put/ : PUT request. Same

/delete/{uid} :  DELETE request. Return JSON with uid deleted and result.


ALL PREVIOUS REQUESTS RETURN 2XX HTTP STATUS IF SUCCESSFUL. 
So if you only need a simple checkup, use only the http status, it is reliable.

USING JERSEY FRAMEWORK AND MAVEN.
