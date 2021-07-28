# Wishlist API

Wishlist REST API - Backend Technical Challenge

## Getting Started
### Prerequisites

* Java 11 + 
* Maven
* Git
* Docker

### Installing

Clone the repository

```
git clone https://github.com/fernandogenerato/java-wishlist-magalu.git
```

Go to the project folder

```
cd wishlist
```

Build the project

```
mvn clean package
```

Build the image

```
docker build -t java-api .
```
Build docker-compose with no cache
```
docker-compose build --no-cache
```
Run docker compose
```
docker-compose up
```
Import the Postman collection
```
Magalu-WishlistAPI.postman_collection.json

Verify the path : ~\wishlist\Magalu-WishlistAPI.postman_collection.json
```

