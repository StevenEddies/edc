# EDC

Software based "every day calendar" to build good habits.



## Usage

### Required Components

 * An internet-accessible server.
    * These instructions assume Linux-based, but other operating systems should work in theory.
    * The following should be installed:
       * Java JDK (17+).
       * Docker engine (18.06.0+), CLI and compose (1.22.0+).
       * Git.


### Downloading

Run `git clone https://github.com/StevenEddies/edc.git` from a terminal.

You'd usually want to `cd edc` in order to run the steps below.


### Customisation

Run `nano ./edc-deploy/src/main/resources/var/config.yaml` to edit the configuration file.

More detailed instructions tbc.


### Running the Server

 * To start the server: `./gradlew  :edc-deploy:dockerComposeUp`
 * To stop the server: `./gradlew  :edc-deploy:dockerComposeDown`

TCP port 80 needs to be internet-accessible (or ideally, forwarded from a reverse proxy which handles HTTPS termination).



## Development

### Contributing

If you would be interested in collaborating on this project, please get in touch (see below).

If you would like to suggest a feature or report a bug, please use the [issue tracker](https://github.com/StevenEddies/edc/issues).


### Technology

The backend is Java using the Dropwizard framework. The frontend is Typescript, HTML and CSS using the Angular framework and the NGINX server. Builds are managed via Gradle and containerised using Docker and Docker Compose.


### Links

 * Master SCM repository: https://github.com/StevenEddies/edc
 * Issue tracker: https://github.com/StevenEddies/edc/issues


### Building

To get started:
 1. Ensure you have:
     * Java JDK (17+).
     * Docker engine (18.06.0+), CLI and compose (1.22.0+).
     * Eclipse IDE for Enterprise Java and Web Developers.
     * Angular CLI.
 3. Clone this repository into Eclipse.
 4. Import the project into an Eclipse workspace.
 5. Use the `:edc-backend:run` Gradle task to run the backend; and `ng serve` from a terminal (working directory `edc-frontend`) to run the frontend locally.



## Contact

Please email [steven@eddies.me.uk](mailto:steven@eddies.me.uk).



## Credits

This application is developed by and copyright [Steven Eddies](http://www.eddies.me.uk), 2021-22.

It is licensed under the Apache licence version 2.0 (see the LICENCE file alongside this one).

### Libraries

 * [Angular](https://angular.io/) - [MIT license](https://github.com/angular/angular/blob/master/LICENSE).
    * Various dependencies of Angular.
 * [Angular Material](https://material.angular.io/) - [MIT license](https://github.com/angular/angular/blob/master/LICENSE).
 * [Docker](https://www.docker.com/) - [Apache License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
 * [Docker Gradle Plugin](https://github.com/palantir/gradle-docker) - [Apache License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
 * [Dropwizard](https://www.dropwizard.io/) - [Apache License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
    * Various dependencies of Dropwizard.
 * [Gradle](https://gradle.org/) - [Apache License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
 * [Gradle Plugin for Node](https://github.com/node-gradle/gradle-node-plugin) - [Apache License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
 * [Java](https://openjdk.java.net/) - [GNU General Public License, version 2, with the Classpath Exception](https://openjdk.java.net/legal/gplv2+ce.html).
 * [slf4j](http://www.slf4j.org) - [MIT license](http://www.slf4j.org/license.html).
 * Various test libraries and tools which are not distributed to end-users: please see `build.gradle` for details.
