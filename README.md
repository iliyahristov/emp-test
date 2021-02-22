# emp-test
Service is deleting all transactions older then hour at the top of every hour of every day.
You can find it in com.ih.schedule.

You can upload csv file by making POST request to http://localhost:8080/api/merchant/upload with file attachet to the body 
or by uploading file on http://localhost:8080/registration
CSV example is abailable here : /src/main/resources/merchantAndAdminList.csv 
