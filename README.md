# TheScene.Co Java Client SDK

## Contact information
* **John Deverall** email: john@thescene.co, (skype johndeverall@gmail.com)
* **Georgy Popov** gorge39@gmail.com
* **Darwin Kurabi** darwin.rocker@gmail.com
* **Yaffa Ibreighith** yaffa.ibreghith@gmail.com

## What is it? 

This repository contains a java client library to assist in the development of applications using TheScene.Co API. 

It may be useful for android or other applications that are built using the java programming language. 

This software is provided under the GNU Lesser General Public License. See https://www.gnu.org/licenses/lgpl-3.0.en.html for details.

## Useful links

Swagger documentation for TheScene.Co API can be found at http://docs.thescene.co

A RESTful HAL+JSON API browser for TheScene.Co API can be found at http://browser.thescene.co

Interested in developing a php application instead? Please see https://github.com/logic-master/thescene-php-client-sdk.git

## Project Structure

Currently there are three modules included in this project.

* **console** A refrence implementation of a java client using TheScene.Co Java Client SDK.
* **presentation-dto** A collection of data transfer objects that can be used for representing TheScene.Co API requests and responses.
* **java-api-client-library** A library that takes care of the underlying http fundamentals of connecting to TheScene.Co API and also manages all hateoas links and rels. It is this library that is most useful for importing into other java applications, to connect to TheScene.Co API.

## Java API Client Library Usage Examples

All API Access can be handled through the SceneClient interface. The SceneClient object manages

* Authorization state.
* Management of hal+json hateoas links and rels.
* The caching of base 'rels'. Links that will not change during the course of a single session of API usage. 

### 1. To get a client credentials access token using the java api client library

```
SceneClient.configureClientContext().setBaseUrl("http://api.thescene.co")
					.setClientCredentials("Enter your oauth2 client id in here", "Enter your oauth2 client secret in here");

SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
```

### 2. To get a resource owner access token using the java api client library (using resource owner's credentials)

```
SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(registeredMember.getEmail(),
				"testtest");
```

### 3. To register a new member

First you need a client credentials access token. Then you can do like the following.

```
MemberRegistrationRequest memberRegistrationRequest = new MemberRegistrationRequest(...);
MemberResource registeredMember = SceneClient.getMemberClient().register(memberRegistrationRequest);
```

Once you have registered a member, you can swap your client credentials access token for a resource owner access token. You'll need to do this if you want to do anything with your newly registered member.  

```
SceneClient.getClientContext().exchangeResourceOwnerCredentialsForAccessToken(registeredMember.getEmail(), <enter resource owner password in here>);
```

### 4. To get the member resource for the current client credentials access token

```
MemberResource memberResourceForCurrentClientCredentialsAccessToken = SceneClient.memberInfo();
```

### 5. To create a new event

Creating a new event can be done as follows

```
EventRequest eventRequest = new EventRequest(...);

SceneClient.getEventClient().createEvent(memberResource, eventRequest);

```
### 6. General principles and a list of all client classes available from the SceneClient object

#### General principles

In general, client classes can be used to send `*Request` objects when performing CRUD operations on resources, and will receive `*Resource` objects as a response. When making idempotent operations (GET requests), resource objects can be used to GET more resource objects, as each client class will automatically follow HAL links in Resource objects to GET resources that have been requested.

In some cases *Resource objects can be passed to the SceneClient classes to achieve some modifying operation. This is because the client classes sometimes handle the conversion of a Resource object to a Request object internally.

Below is a list of client classes that are available from the object SceneClient

#### AdministraitonClient
#### CategoryClient
#### ContentClient
#### EventClient
#### FolderClient
#### ImageClient
#### LocationClient
#### MemberClient
#### MessageClient
#### ProfileClient
#### TagClient


### Building the project

To build the project you will need the following tools. 

* **Git** [[Download]](https://git-scm.com/downloads)
* **Maven** [[Download]](https://maven.apache.org/download.cgi)
* **Java 8 SDK** [[Download]](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### Make changes to the project

To make changes to the project you will need an IDE

* **An IDE** [[Eclipse Download]](https://eclipse.org/downloads/) or [[Netbeans Download]](https://netbeans.org/downloads/) or [[IntelliJ IDEA Download]](https://www.jetbrains.com/idea/download/)
* **Lombok** [[Download]](https://projectlombok.org/) The latest version of lombok can be downloaded, or the lombok jar can be found in the root of thescene-spa repository on this page. 

### Clone the project

Clone this project by using the following git command and the HTTPS URL:

    $ git clone https://github.com/TheSceneCo/thescene-java-client-sdk.git

### Run the project

To run this project, you have to build the whole project in the root module.

    mvn clean install

Then start up the console module (reference client implementation) via spring-boot maven plugin from the console folder.

    mvn spring-boot:run


Happy coding!
