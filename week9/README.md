# Week 9 - Calculator on Akka HTTP

## Test

Run `POST localhost:8080/calc` with data in body 
```json
{
    "char": "1"
}
```

where value of field char in one character of command. For example, 
run "1", then "+", then "2", then "=". 
Final result after sending "=" received in json format
```json
{
    "result": 3.0
}
```
