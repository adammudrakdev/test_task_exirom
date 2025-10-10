1) In this task, I tried to showcase primarily my previous experience and versatility, proving almost
full back-end experience: db, entities, repo, services,controllers, utils, validators, dtos, enums, exceptions
everything here at your service;

2) POST is slow intentionally to imitate all path of transaction. Refer [here](src/main/kotlin/com/example/test_task_exirom/service/subservices/CommonPaymentService.kt) and set Thread.sleep(0) to speed things up)))

3) I decided to use two DBs - for transactions and merchants separately,
since we have to know what merchants we work with before we work with them;

4) I implemented util functions as singleton Kotlin objects - this way, they can be reused just as functions,
and there is no need in more than one instance per app;

5) Where applicable, I added const val - to escape magic numbers as much as I can;

6) I provide custom date validator so that user can insert date in the way they are used to, not the way a programmer would do;

7) I decided to have higher level service - TransactionService - above AcquirerRouter
because I need not only to get route, but also get transactions from db, for example, which is not Acquirer responsibility;

8) I decided to have global exception handler to clearly communicate errors in input to front instead of eating them backed-wise;

9) I used functional interface (one-function) PaymentService and used abstract class CommonPaymentService,
since the task says that the logic of evaluation is the same for both A and B;

10) From German, I heard that we temporarily store real card data in Redis. Internal endpoints - are, say, for future another
endpoint accessible only from redis container;
11) When testing project against test data, do not hesitate to look to console - will see some service messages;

---
### You can also run this app in docker! Just run locally inside project folder:
# Prepare build
```sh
mvn clean package
```
# Build Docker image
```sh
docker build -t myapp .
```
# Run container (detached)
```sh
docker run -d --name myapp-container -p 8080:8080 myapp
```
# Visit Swagger Docs to explore the app [here](http://localhost:8080/swagger-ui/index.html#/)
# Alternatively, just visit [remote swagger](https://exirom-tasks.adammudrak.space/swagger-ui/index.html#/) I set up
### Important!!!
Here you can find some JSON data to test against in *POST /transactions* in Swagger:


#### 1) Invalid Luhn
```json
{
  "cardNumber": "4111111111111112",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 2) Expired card (expiry in the past)
```json
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/20",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 3) The card number is too long (17 digits - violates \d{16})
```json
{
  "cardNumber": "41111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 4) The card number is too short (15 digits)
```json
{
  "cardNumber": "411111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 5) Too many decimal places in amount (3 digits) - violates fraction = 2
```json
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 12.345,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 6) The amount is greater than the allowed threshold (exceeded 1_000_000.0)
```json
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 1000000.01,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 7) The amount is less than the minimum threshold (less than 0.1)
```json
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.09,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 8) Currency not in enum (unknown value)
```json
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "ABC",
  "merchantId": "1qW"
}
```

#### 9) MerchantId does not match the regular expression (too long - length check)
```json
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "USD",
  "merchantId": "123456789"
}
```

#### 10) Valid request → PrivatBank
```json
{
  "cardNumber": "4000000000000002",
  "expiryDate": "12/30",
  "cvv": "123",
  "amount": 100.00,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 11) Valid card → OTPBank
```json
{
  "cardNumber": "4111111110000000",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 12) Invalid request → PrivatBank
```json
{
  "cardNumber": "5105105115125221",
  "expiryDate": "12/30",
  "cvv": "123",
  "amount": 50.50,
  "currency": "USD",
  "merchantId": "1qW"
}
```

#### 13) Invalid request → OTPBank
```json
{
  "cardNumber": "4012888888881881",
  "expiryDate": "12/30",
  "cvv": "123",
  "amount": 1.00,
  "currency": "USD",
  "merchantId": "1qW"
}
```