# Week 11 - Tasks API

## Test

Run `GET localhost:8080/tasks` for listing all tasks

Response:
```json
[
  {
    "id": "8bf98241-f25e-4318-be22-4f963f71219d",
    "title": "qwe",
    "description": "qwe",
    "done": false
  }
]
```

Run `POST localhost:8080/tasks` for creating tasks

Request:
```json
{
  "title": "qwe",
  "description": "qwe"
}
```

Response:
```json
{
    "id": "8bf98241-f25e-4318-be22-4f963f71219d",
    "title": "qwe",
    "description": "qwe",
    "done": false
}
```

## Build

Build docker image with `sbt docker:publishLocal`

Run docker container as usual

## Deploy

First, need to build docker image

After run `./deploy.sh {heroku-app} {image-id} {token}`
