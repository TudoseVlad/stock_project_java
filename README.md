A simple Java Spring project made for extracting and processing csv files and predicting future behaviours.

**Steps to use:**

In the project directory start maven API:

**mvn spring-boot::run**

To use the extractor API use a POST on the **http://localhost:8080/extractor/upload** address and add to the body the form-data as such:

**key = csvfile**

**Value = the Csvfile you want to process**

Afterwards the data will be sent back to the user and also put into the OUTPUT_FILE mentioned in Common.Java.

To use the predictor API use a GET on the **http://localhost:8080/predictor** address and will return a list composed of the **OUTPUT_FILE** and 3 new predictions based on the behaviour before
