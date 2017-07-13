# grocery-organiser

TODO
[ ] Add support for bower
[ ] Include usual javascript libraries
[ ] Include sample web test template
[ ] Include sample service test template
[x] Fix logging colours, during mvn clean install and during console output
[ ] Add logging to file
[x] Product preferences should ideally be sourced from an external file. In tests, could use test property file
[x] Separate the 'find preferred store' method into a different port, covered by a separate test?
[/] Perhaps the 'find preferred store' method only needs a product name not a whole product. Kept as product for future features
[x] Remove the hardcoding of preferred products in java
[x] Flesh out all services for remaining stores, UDAYA, IGA. Look at storeService, sorter classes
[x] Create an integration test to make sure the context loads correctly. E.g. delete a property from application.property (prod) and context will fail to start.
[ ] How to write tests that check something was logged
[ ] Suppress logging during tests (for non com.gihan classes)
[x] Use the shopping list printer somewhere
[x] Create a controller that can receive requests for creating shopping lists
[ ] Do a hello world example of service in AWS
[ ] Add extra service in GroceriesPort to start everything from a file

[ ] Where to host this service?
    - Locally?
    - AWS?

[ ] OCR to read handwritten text.
    - [!] Google docs?
    - [!] Tesseract is NOT great for handwritten text
    - [x] Notes plus iPhone app
    
[ ] Way of creating a checklist in the iPhone notes app automatically
    - Workflow can automate up to creating a new plain note with text from attachment/email
    - Experiment and make a manual .enex file, send to email and see if it can be imported into notes