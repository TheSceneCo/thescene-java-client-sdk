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

1. To log in using the client library

```
SceneClient.configureClientContext().setBaseUrl("http://api.thescene.co")
					.setClientCredentials("Enter your oauth2 client id in here", "Enter your oauth2 client secret in here");

SceneClient.getClientContext().exchangeClientCredentialsForAccessToken();
```
