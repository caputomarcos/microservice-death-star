## Test Integrado


Iniciar mongodb rabbitmq

```bash
docker start my-mongo
docker start my-rabbbit
```

Iniciar command-side

```bash
./gradlew command-side:bootRun
```

Iniciar queyr-side

```bash
./gradlew query-side:bootRun
```

Iniciar Teste Integrado

```bash
./gradlew integration-test:integrationTest
```


