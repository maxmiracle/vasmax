## maximserver.ru

PoC:
- Micronaut service as web-site all-in-one.
- Security configurations.
- Performance.


Features:
- certbot micronaut-acme integration

Tips:
- Dockerfile to create server docker. 
- There should be directory maximserver in vasmax project dir. It is absent in repo.
- There are two files accountKey.pem, domainKey.pem required in maximserver directory. See application.yml, Dockerfile.-

### Для разработки
```shell
$ docker run -d \
-p 9443:9443 \
--name portainer \
--restart unless-stopped \
-v data:/data \
-v /var/run/docker.sock:/var/run/docker.sock \
portainer/portainer-ce:latest
```

#### Скопировать в католог static собранный сайт
```shell
cp -a ../vasmax-site/build/** ./src/main/resources/static
```

#### Собрать приложение
```shell
gradle shadowJar -x test
```

#### Собрать новую версию докера с сайтом.
```shell
docker build --platform linux/amd64 -t maxmiracle/test1:14 .
docker push maxmiracle/test1:14
```