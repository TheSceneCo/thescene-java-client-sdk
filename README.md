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

* Authentication state.
* Management of hal+json hateoas links and rels.
* The caching of base 'rels'. Links that will not change during the course of a single session of API usage. 

###1. To log in using the client library and oauth2 client credentials: 

```
SceneClient.configureClientContext().setBaseUrl("http://api.thescene.co")
					.setClientCredentials("Enter your oauth2 client id in here", "Enter your oauth2 client secret in here");

SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
```

###2. To upgrade with oauth2 resource owner credentials

```
SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(registeredMember.getEmail(),
				"testtest");
```

###3. To register a new member

First establish a client credentials authentication token. Then you can do like the following.

```
MemberRegistrationRequest memberRegistrationRequest = new MemberRegistrationRequest(...);
MemberResource registeredMember = SceneClient.getMemberClient().register(memberRegistrationRequest);
```

One you have a registered member you can log in with that member. 

```
SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(registeredMember.getEmail(),
				<enter resource owner password in here>);
```


