/* 
 Copyright (C) 2012 - 2016 TheScene.Co
 This file is part of TheScene.Co Java Client SDK.

 Authors: John Deverall, Georgy Popov.

 TheScene.Co Java Client SDK is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 TheScene.Co Java Client SDK is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the Lesser GNU General Public License.
*/

package nz.co.thescene.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "nz.co.thescene.*")
@SpringBootApplication
public class ConsoleApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
    }

}
