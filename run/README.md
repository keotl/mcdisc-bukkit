# Minecraft Discs (Spigot)


## Development Server (using Docker)
Files in the `run` directory will be copied over to the dev container.

- From the `run` directory:
```
docker build . -t mcdisc
docker run --name mcdisc-dev -it -p 5005:5005 -p 25565:25565 mcdisc

# If the container has already been created
docker start mcdisc-dev
docker attach mcdisc-dev
```

- Create a remote debugger configuration in IntelliJ for localhost:5005.

- Copy plugin to running container
```
mvn package
docker cp target/*.jar mcdisc-dev:plugins/
```
