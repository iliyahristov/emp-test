# emp-test
Service is deleting all transactions older then hour at the top of every hour of every day.
You can find it in com.ih.schedule.

You can upload csv file by making POST request to http://localhost:8080/api/merchant/upload with file attachet to the body 
or by uploading file on http://localhost:8080/registration
CSV example is abailable here : /src/main/resources/merchantAndAdminList.csv 

You can make payment with PostMan with **XML or JSON POST** request to **http://localhost:8080/api/transaction/pay**
with BaseAuth (username and password of some merchant)
and body :

JSON : 
```
{
    "amount": 5000,
    "customerEmail": "tes1@abv.bg",
    "customerPhone": "+359896644333",
    "referenceId": "1200000039"
}
```

XML : 
```
<?xml version="1.0" encoding="UTF-8"?>
<transaction>
    <amount>1234</amount>
    <customerEmail>test@abv.bg</customerEmail>
    <customerPhone>+359896644557</customerPhone>
    <referenceId>1200000034</referenceId>
</transaction>
```
Keep in mind that the amount is stored as coins (in Integer not Decimal) so if we need to pay **10.20** we need to write in amount **1020**.

You can refund some transaction by its referenceId with POST to **http://localhost:8080/api/transaction/refund/{referenceId}** with BaseAuth (username and password of some merchant) 
