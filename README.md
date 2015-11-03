# LogServer

#Description 

Servlet implementation which receive a JSON data request.
The JSON should be like : {"errMessage":"message","errURL":"domain:port/resources/...","line":x,"col":y}
This will pass the JSON String to Java Object, then use logback for log it in a file.

#Usage

Make sure have maven installed before
```./install.sh```
Check the pom.xml file for ```<configuration>
    			                      <additionalClasspathDirs>
    				                      <additionalClasspathDir>${project.basedir}/.conf</additionalClasspathDir>
    			                      </additionalClasspathDirs>
    		                      </configuration>```
to add your own directory to the classpath : this will be the directory for the "properties" files and the logback.xml file.
The property file it use for define the Control-Access-Allow-Origin header and the Max-Age header like in the log-server.properties
then you can change the web.xml file for the servlet configuration
