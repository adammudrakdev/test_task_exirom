Here you can find some JSON data to test against:

1) Valid (correct Luhn, not expired, all fields are valid)
{
  "cardNumber": "4111111111111112",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}

2) Invalid Luhn (last digit changed)
{
  "cardNumber": "4111111111111112",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}

3) Expired card (expiry in the past)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/20",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}

4) The card number is too long (17 digits - violates \d{16})
{
  "cardNumber": "41111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}

5) The card number is too short (15 digits)
{
  "cardNumber": "411111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.10,
  "currency": "USD",
  "merchantId": "1qW"
}

6) Too many decimal places in amount (3 digits) - violates fraction = 2
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 12.345,
  "currency": "USD",
  "merchantId": "1qW"
}

7) The amount is greater than the allowed threshold (requested - exceeded 1_000_000.0)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 1000000.01,
  "currency": "USD",
  "merchantId": "1qW"
}

8) The amount is less than the minimum threshold (less than 0.1)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 0.09,
  "currency": "USD",
  "merchantId": "1qW"
}

9) Currency not in enum (unknown value)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "ABC",
  "merchantId": "1qW"
}

10) MerchantId does not match the regular expression (too long - length check)
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "USD",
  "merchantId": "123456789"
}

11) Valid request → PrivatBank
{
  "cardNumber": "4000000000000002",
  "expiryDate": "12/30",
  "cvv": "123",
  "amount": 100.00,
  "currency": "USD",
  "merchantId": "1qW"
}

12) Valid card → OTPBank
{
  "cardNumber": "4111111111111111",
  "expiryDate": "01/26",
  "cvv": "123",
  "amount": 10.00,
  "currency": "USD",
  "merchantId": "1qW"
}

13) Invalid request → PrivatBank
{
  "cardNumber": "5105105105105100",
  "expiryDate": "12/30",
  "cvv": "123",
  "amount": 50.50,
  "currency": "USD",
  "merchantId": "1qW"
}

14) Invalid request → OTPBank
{
  "cardNumber": "4012888888881881",
  "expiryDate": "12/30",
  "cvv": "123",
  "amount": 1.00,
  "currency": "USD",
  "merchantId": "1qW"
}
