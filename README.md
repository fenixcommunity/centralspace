# The main goal of the project is to collect all the knowledge about Java/Spring(and related topics) and organize it in a logical way.
# The current project is part of backend-frontend communication

~~~~~~~~~~~~~~~~

----------------
SECURITY TRICKS:
- XXE (XML EXTERNAL ENTITIES) -> if you use xml parsing tool (newest spring-xml-integration)
- Check Your Dependencies with Snyk
- https in production 
- upgrade To Latest Releases
- enable CSRF Protection
- use a Content Security Policy to Prevent XSS Attacks and lifter all scripts and html-tags (front and backend side)
- use OAuth 2.0 with OpenId (for example Okta)
- all secrets to Spring Vault
- test Your App with OWASP’s ZAP
- remove important and sensitive info in loggs, rest, error messages
- do not show names of components outside
- remove sensitive data in URL - Get methods
- frameOptions only for the same origin 
- cookies http only and secure
- X-XSS-Protection
- Strict-Transport-Security
- SQL INJECTION -> Hibernate and setParameter(x, x). No native queries and concatenation
- LFI/RFI (LOCAL/REMOTE FILE INCLUSION) com?file=xxx , com?file=../../etc/password
- all sensitive, secure places „deny by default"

PLUGINS 
~~~~~~~~~~~~~~~~
CodeStream
https://plugins.jetbrains.com/plugin/12206-codestream  
group -> centralspace

Stack Trace To Uml 
https://plugins.jetbrains.com/plugin/10749-stack-trace-to-uml  
no works yet

Whitesource Advise 
https://plugins.jetbrains.com/plugin/11534-whitesource-advise  
not free but interesting

Squaretest
https://squaretest.com/

Grazie translator
https://plugins.jetbrains.com/plugin/12175-grazie  

Jenkins Plugin Control
https://plugins.jetbrains.com/plugin/6110-jenkins-control-plugin  

.ignore
https://plugins.jetbrains.com/plugin/7495--ignore  

SonarLint
https://plugins.jetbrains.com/plugin/7973-sonarlint  

PlantUML
https://plugins.jetbrains.com/plugin/7017-plantuml-integration  

String Manipulation
https://plugins.jetbrains.com/plugin/2162-string-manipulation  

Maven Helper
https://plugins.jetbrains.com/plugin/7179-maven-helper  

Mongo plugin
https://plugins.jetbrains.com/plugin/7141-mongo-plugin  

Swagger
https://plugins.jetbrains.com/plugin/8347-swagger
to modify swagger yml settings

IDE Features Trainer
https://plugins.jetbrains.com/plugin/8554-ide-features-trainer

AWS Toolkit
https://plugins.jetbrains.com/plugin/11349-aws-toolkit

YAML File Generator
https://plugins.jetbrains.com/plugin/11069-yaml-file-generator

SLINT 
format rules for react code

Pmd
static analysis of the code
https://plugins.jetbrains.com/plugin/1137-pmdplugin

JProfiler
https://plugins.jetbrains.com/plugin/253-jprofiler

HttpClient Intellij Tool
