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
#### copy secrets
```shell
sudo scp -i ~/.ssh/id_cloud1  ~/repo/maximserver/accountKey.pem  user1@213.171.31.41:~/accountKey.pem
```

```shell
sudo scp -i ~/.ssh/id_cloud1  ~/repo/maximserver/domainKey.pem  user1@213.171.31.41:~/domainKey.pem
```

#### запуск портейнера, не относится непосредственно к этому проекту. Здесь для удобства.
```shell
$ docker run -d \
-p 9443:9443 \
--name portainer \
--restart unless-stopped \
-v data:/data \
-v /var/run/docker.sock:/var/run/docker.sock \
portainer/portainer-ce:latest
```

#### Скопировать в католог static собранный сайт - obsolete
```shell
cp -a ../vasmax-site/build/** ./src/main/resources/static
```

#### Скопировать в католог static собранный сайт - vite, react 19.2
```shell
cp -a ../vasmax-vk/dist/** ./src/main/resources/static
```

#### Собрать приложение
```shell
gradle shadowJar -x test
```

#### Собрать новую версию докера с сайтом.
```shell
docker build --platform linux/amd64 -t maxmiracle/test1:25 .
docker push maxmiracle/test1:25
```