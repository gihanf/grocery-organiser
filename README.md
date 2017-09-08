# grocery-organiser

#### Template Goals
- [ ] Add support for bower
- [ ] Include usual javascript libraries
- [ ] Include sample web test template
- [x] Include sample service test template
- [x] Fix logging colours, during mvn clean install and during console output
- [ ] Add logging to file
- [ ] Suppress logging during tests (for non com.gihan classes)
- [ ] How to write tests that check something was logged

#### Feature Goals
- [ ] Handwriting recognition to convert image -> list of unsorted groceries
    - [!] Google docs?
    - [!] Tesseract is NOT great for handwritten text
    - [x] Notes plus iPhone app
- [ ] Import produced shopping list into 'notes' app with checklist format
    - [ ] Use 'Workflows' to macro this task
    - [ ] Experiment with producing file formats that 'notes' will recognise. E.g. .enex file, import via email
- [ ] Make shopping lists shareable and updateable between two people (only if not intending on using the 'notes' app)
- [ ] Feature to allow descriptive text to appear in () but not affect sorting / not be used when sorting products

#### Implementation Goals
- [x] Product preferences should ideally be sourced from an external file. In tests, could use test property file
- [x] Separate the 'find preferred store' method into a different port, covered by a separate test?
- [/] Perhaps the 'find preferred store' method only needs a product name not a whole product. Kept as product for future features
- [x] Remove the hardcoding of preferred products in java
- [x] Flesh out all services for remaining stores, UDAYA, IGA. Look at storeService, sorter classes
- [x] Create an integration test to make sure the context loads correctly. E.g. delete a property from application.property (prod) and context will fail to start.
- [x] Use the shopping list printer somewhere
- [x] Create a controller that can receive requests for creating shopping lists
- [x] Do a hello world example of service in AWS
- [ ] Add extra service in GroceriesPort to start everything from a file
- [ ] put this command somewhere. curl -d '@data.json' -H "Content-Type: application/json" -X POST http://localhost:8886/createShoppingList
- [ ] In each store product sorter, make them implement an interface 'productNameAware'? have common methods here..

#### Deployment Goals
- [ ] Where to host this service?
    - [/] Locally (not viable)
    - [x] AWS EC2 (good until 12 months of free tier expire)
    - [ ] AWS Lambda (only if required, and only if not doing the standalone phone app)
    - [ ] IOS phone app communicating with external server (e.g. AWS)
    - [ ] IOS phone app standalone (i.e. completely self contained)
