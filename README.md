	  .    .     .            +         .         .                 .  . 
	   .                 .                   .               .           
	           .    ,,o         .                  __.o+.                
	 .            od8^                  .      oo888888P^b           .   
	    .       ,".o'      .     .             `b^'""`b -`b   .          
	          ,'.'o'             .   .          t. = -`b -`t.    .       
	         ; d o' .        ___          _.--.. 8  -  `b  =`b           
	     .  dooo8<       .o:':__;o.     ,;;o88%%8bb - = `b  =`b.    .    
	 .     |^88^88=. .,x88/::/ | \\`;;;;;;d%%%%%88%88888/%x88888         
	       :-88=88%%L8`%`|::|_>-<_||%;;%;8%%=;:::=%8;;\%%%%\8888         
	   .   |=88 88%%|HHHH|::| >-< |||;%;;8%%=;:::=%8;;;%%%%+|]88        .
	       | 88-88%%LL.%.%b::Y_|_Y/%|;;;;`%8%%oo88%:o%.;;;;+|]88  .      
	       Yx88o88^^'"`^^%8boooood..-\H_Hd%P%%88%P^%%^'\;;;/%%88         
	      . `"\^\          ~"""""'      d%P """^" ;   = `+' - P          
	.        `.`.b   .                :<%%>  .   :  -   d' - P      . .  
	           .`.b     .        .    `788      ,'-  = d' =.'            
	    .       ``.b.                           :..-  :'  P              
	         .   `q.>b         .               `^^^:::::,'       .       
	 MC            ""^^               .                                  
	                                                                     
	THE EMPIRE DOESN'T LIKE FRONTENDERS FOR SOME REASON (THEY'RE VERY RUDE) 


#### Tecnológicas Utilizadas

 - Service gateway and registry using [Spring Cloud Netflix](https://cloud.spring.io/spring-cloud-netflix/) (Zuul, Eureka)
 - External configuration using [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/)
 - Java Microservices with [Spring Boot](http://projects.spring.io/spring-boot/)
 - Command & Query responsibility Separation with the [Axon CQRS Framework](http://www.axonframework.org/)
 - Event Sourcing & Materialised Views with RabbitMQ, MongoDB and H2


## Dependências

 - Java SDK 8
 - [Docker](https://www.docker.com)
 - Docker-compose

> Se você já possui o MongoDB ou RabbitMQ sendo executado localmente, encerre esses serviços antes de continuar para evitar conflitos de porta.

### 1 - Build  Containers

```bash
$ git clone https://github.com/caputomarcos/microservice-death-star.git
$ cd microservice-death-star
$ ./gradlew clean image
```

### 2 - Iniciar Microservices

```bash
$ docker-compose -f wow.yml up
```

### 3 - Test Integrado

```bash
$ ./gradlew integration-test:integrationTest
```

### 4 - Teste Manual

* request

```bash
$ curl -X POST -v --header "Content-Type: application/json" --header "Accept: */*" "http://localhost:8080/commands/planets/add/1?name=Tatooine&climate=temperate&terrain=grassy%20hills,%20swamps,%20forests,%20mountains"
```

* response

```bash
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /commands/products/add/1?name=Tatooine&climate=temperate&terrain=grassy%20hills,%20swamps,%20forests,%20mountains HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.47.0
> Content-Type: application/json
> Accept: */*
>
< HTTP/1.1 201 Created
< Date: Wed, 29 Jun 2016 14:14:26 GMT
< X-Application-Context: gateway-service:production:8080
< Date: Wed, 29 Jun 2016 14:14:26 GMT
< Transfer-Encoding: chunked
< Server: Jetty(9.2.16.v20160414)
```

#### Features 

##### Adicionar Planeta

* request

```bash
$ curl -X POST -v --header "Content-Type: application/json" --header "Accept: */*" "http://localhost:8080/commands/planets/add/2?name=Naboo&climate=temperate&terrain=grassy%20hills,%20swamps,%20forests,%20mountains"
```

* response

```bash
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST /commands/planets/add/2?name=Naboo&climate=temperate&terrain=grassy%20hills,%20swamps,%20forests,%20mountains HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.54.0
> Content-Type: application/json
> Accept: */*
>
< HTTP/1.1 201 Created
< Date: Tue, 22 May 2018 16:00:10 GMT
< X-Application-Context: gateway-service:production:8080
< Date: Tue, 22 May 2018 16:00:10 GMT
< Transfer-Encoding: chunked
< Server: Jetty(9.2.16.v20160414)
<
* Connection #0 to host localhost left intact
```

##### Procurar por Id do Planeta 

* request

```bash
$ curl http://localhost:8080/queries/planets/search/findById?id=2
```

* response

```json
{
  "name" : "Naboo",
  "climate" : "temperate",
  "terrain" : "grassy hills, swamps, forests, mountains",
  "numberOfFilms" : 3,
  "destroyable" : false,
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/queries/planets/2"
    },
    "planet" : {
      "href" : "http://localhost:8080/queries/planets/2"
    }
  }
}
```

##### Procurar por Nome do Planeta 

* request

```bash
$ curl http://localhost:8080/queries/planets/search/findByName?name=Naboo
```

* response

```json
{
  "name" : "Naboo",
  "climate" : "temperate",
  "terrain" : "grassy hills, swamps, forests, mountains",
  "numberOfFilms" : 3,
  "destroyable" : false,
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/queries/planets/2"
    },
    "planet" : {
      "href" : "http://localhost:8080/queries/planets/2"
    }
  }
}
```

##### Listar Planetas 

* request

```bash
$ curl http://localhost:8080/queries/planets
```

* response 

```json
{
  "_embedded" : {
    "planets" : [ {
      "name" : "Naboo",
      "climate" : "temperate",
      "terrain" : "grassy hills, swamps, forests, mountains",
      "numberOfFilms" : 3,
      "destroyable" : false,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/queries/planets/2"
        },
        "planet" : {
          "href" : "http://localhost:8080/queries/planets/2"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/queries/planets"
    },
    "profile" : {
      "href" : "http://localhost:8080/queries/profile/planets"
    },
    "search" : {
      "href" : "http://localhost:8080/queries/planets/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 1,
    "totalPages" : 0,
    "number" : 0
   }
}
```

##### Apagar Planetas

* request 

```bash
$ curl -X POST -v --header "Content-Type: application/json" --header "Accept: */*" "http://localhost:8080/commands/planets/del/1"
```

* response

```bash
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST /commands/planets/del/Naboo HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.54.0
> Content-Type: application/json
> Accept: */*
>
< HTTP/1.1 201 Created
< Date: Tue, 22 May 2018 16:04:01 GMT
< X-Application-Context: gateway-service:production:8080
< Date: Tue, 22 May 2018 16:04:01 GMT
< Transfer-Encoding: chunked
< Server: Jetty(9.2.16.v20160414)
<
* Connection #0 to host localhost left intact
```
