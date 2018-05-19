# XIV GEAR PLANNER

- Goal is to create a gear planning application that can be used to plan raid group progression and gear planning.

# Status
 
Initial structure of website being created

- Static Creation Management (30%)
    * Building the page to create and add static creation 
- Import Players (70%)
- View gear (40%)
- Import gear sets (50%)
    - Able to import directly from lodestone
    - Will only import matching with current job sets currently.
    - Possible switching of gear sets to be added at a later time. 
- Design (40%)


# Technologies

- Java Spring Boot Web Application
- Using MySQL database backend
- Using ES6 compliant Javascript
- JSON output api to be further developed
- WebPack backend for javascript module support
- Use of Selenium WebDriver for parsing utilities.
    * Integrated with Chrome browser to take fully rendered page data and manipulate as needed.

#Known issues

- Nothing works at all :D
- Ok some things work now, but its mostly a mess still.

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
    - ChromeDriver (Selenium) install and setup driver location. 
        - Location path must be specified in the `application.properties` file.
    
    