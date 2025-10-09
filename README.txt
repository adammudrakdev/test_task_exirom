Here you can find some JSON data to test against:

1) Valid (happy path — Luhn OK, future expiry)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": 123456789
}

2) Invalid Luhn
{
  "cardNumber": "4111111111111112",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "USD",
  "merchantId": 123456789
}

3) Expired card (valid format & Luhn, but expiry in past)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "09/25",
  "cvv": "123",
  "amount": 5.00,
  "currency": "USD",
  "merchantId": 123456789
}

4) Bad CVV (wrong size)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "12",
  "amount": 1.00,
  "currency": "USD",
  "merchantId": 123456789
}

5) Card number wrong length (not 16 digits)
{
  "cardNumber": "41111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 2.50,
  "currency": "USD",
  "merchantId": 123456789
}

6) Malformed expiry format
{
  "cardNumber": "4111111111111111",
  "expiryDate": "2026-01",
  "cvv": "123",
  "amount": 3.00,
  "currency": "USD",
  "merchantId": 123456789
}

7) Amount with too many fractional digits
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.123,
  "currency": "USD",
  "merchantId": 123456789
}

8) Amount exceeding max
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 1000001.00,
  "currency": "USD",
  "merchantId": 123456789
}

9) Invalid currency (not part of enum)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "XYZ",
  "merchantId": 123456789
}

10) Missing required field (merchantId)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "USD"
}