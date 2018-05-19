# XIV GEAR PLANNER

- Goal is to create a gear planning application that can be used to plan raid group progression and gear planning.

# Status
 
Initial structure of website being created

- Static Creation Management (30%)
    * Building the page to create and add static creation 
- Import Players (50%)
- View gear (20%)
- Import gear sets (10%)
- Design (10%)


# Technologies

- Java Spring Boot Web Application
- Using MySQL database backend
- Using ES6 compliant Javascript
- JSON output api to be developed
- WebPack backend for javascript module support

#Known issues

- Nothing works at all :D

#Resources used for data 
https://github.com/viion/ffxiv-datamining
http://xivdb.com


#Setup

1. Clone/Fork repository 
2. Load/Install Maven dependencies
4. NPM Setup
    - 
    - Setup NPM using `npm install` command from command line
    - Once install completed you can auto compire js files using `npm run watch`
    - Webpack settings are stored in webpack.config.js
    
5. MySQL server setup
    - 
    - Install Mysql server for your platform.
    - Mac / Windows / Linux (Install Guides)
    - Setup a username/ password account and a database
    - Copy `example.properties` and change the name to `application.properties`
        - Modify these settings to watch your system setup and database naming.
    
6. Spring Setup
    -
    - JDK will need to be installed to setup on system. 
    - Ensure you update this `spring.devtools.restart.additional-paths` if you plan on using the dev tools.
    - ChromeDriver (Selenium) install and setup driver location
    