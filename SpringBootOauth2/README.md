# OAuth-service-Springboot

## Register endpoint
~~~
POST
localhost:8080/oauth/users
{
  "userName": "hare",
  "password": "hare@123",
  "email": "haeramsingh89@gmail.com",
  "mobile": "9902583940"
}
~~~

## Generate accesstoken endpoint
~~~~
Generate Access Token:
POST localhost:8080/oauth/token?username=hare&password=hare@123&grant_type=password
Basic Auth : username=test, passoword=temp
~~~


## Validate endpoint
~~~
POST
localhost:8080/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjMzMjkwMzMsInVzZXJfbmFtZSI6ImhhcmUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZWU4ZTY5M2UtZjhkMC00YzFlLWI1NjYtYTQ0YTRmY2ZlY2I5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.mfuV-XmWtkPDrc50ZXYE6DVWM07GpFUkSzmp2DzbwAQ

Basic Auth : username=test, passoword=temp
~~~

## Get Access token from Refresh token endpoint
~~~
POST
localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJoYXJlIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6ImVlOGU2OTNlLWY4ZDAtNGMxZS1iNTY2LWE0NGE0ZmNmZWNiOSIsImV4cCI6MTY2MzMzMDE4MiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImRkYjAyZTZhLTU0N2YtNDVlMS1iMjk0LTAwNjBmMmQ4MjQyMyIsImNsaWVudF9pZCI6InRlc3QifQ.qSyIf6M70uHQB0isRWAetTLPEKFVPSt0Z4141OBQrE8

Basic Auth : username=test, passoword=temp
~~~
