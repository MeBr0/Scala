# Week 12 - Tiny URL

## Test

Run `POST localhost:8080/urls` for shorting url

Request:
```json
{
  "original": "https://facebook.com",
  "alias": "face",
  "expiresAt": "01-05-2021"
}
```

Response:
```json
{
    "alias": "face",
    "original": "https://facebook.com",
    "createdAt": "29-04-2021",
    "expiresAt": "01-05-2021"
}
```

Run `DELETE localhost:8080/tasks/{alias}` for deleting alias

Response:
```json
{
  "alias": "face",
  "original": "https://facebook.com",
  "createdAt": "29-04-2021",
  "expiresAt": "01-05-2021"
}
```

Run `GET localhost:8080/short/{alias}` for redirecting with alias
