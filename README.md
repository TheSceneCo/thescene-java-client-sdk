# TheScene.Co Java Client SDK

##Contact information
* **John Deverall** email: john@thescene.co, (skype johndeverall@gmail.com)
* **Craig Deverall** craig@thescene.co
* **Georgy Popov** gorge39@gmail.com
* **Darwin Kurabi** darwin.rocker@gmail.com
* **Alexis Garland** alexisgarland@gmail.com
* **Yaffa Ibreighith** yaffa.ibreghith@gmail.com

##What is it? 

This repository contains a java client library to assist in the development of applications using TheScene.Co API. 

It may be useful for android or other applications that are built using the java programming language. 

This software is provided under the GNU Lesser General Public License. See https://www.gnu.org/licenses/lgpl-3.0.en.html for details.

##Useful links

Swagger documentation for TheScene.Co API can be found at http://docs.thescene.co

A RESTful HAL+JSON API browser for TheScene.Co API can be found at http://browser.thescene.co

Interested in developing a php application instead? Please see [Yasir you can insert your repository link in here :) ]

#TheScene.Co API Application

##Project Structure

Currently there are three modules included in this project.

* **console** A refrence implementation of a java client using TheScene.Co Java Client SDK.
* **presentation-dto** A collection of data transfer objects that can be used for representing TheScene.Co API requests and responses.
* **java-api-client-library** A library that takes care of the underlying http fundamentals of connecting to TheScene.Co API. It is this library that is most useful for importing into other java applications, to connect to TheScene.Co API.

##Java API Client Library Usage Examples

All API Access can be handled through the SceneClient interface. The SceneClient object manages

* Authorization state.
* Management of hal+json hateoas links and rels.
* The caching of base 'rels'. Links that will not change during the course of a single session of API usage. 

###1. To get a client credentials access token using the java api client library

```
SceneClient.configureClientContext().setBaseUrl("http://api.thescene.co")
					.setClientCredentials("Enter your oauth2 client id in here", "Enter your oauth2 client secret in here");

SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
```

###2. To get a resource owner access token using the java api client library (using resource owner's credentials)

```
SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(registeredMember.getEmail(),
				"testtest");
```

###3. To register a new member

First you need a client credentials access token. Then you can do like the following.

```
MemberRegistrationRequest memberRegistrationRequest = new MemberRegistrationRequest(...);
MemberResource registeredMember = SceneClient.getMemberClient().register(memberRegistrationRequest);
```

Once you have registered a member, you can swap your client credentials access token for a resource owner access token. You'll need to do this if you want to do anything with your newly registered member.  

```
SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(registeredMember.getEmail(), <enter resource owner password in here>);
```

###4. To get the member resource for the current client credentials access token

```
MemberResource memberResourceForCurrentClientCredentialsAccessToken = SceneClient.memberInfo();
```

###5. To create a new event

Creating a new event can be done as follows

```
EventRequest eventRequest = new EventRequest(...);

SceneClient.getEventClient().createEvent(memberResource, eventRequest);

```


