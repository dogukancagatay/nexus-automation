# Sonatype Nexus Automated Setup

Repository includes automated installation scripts and documentation for Sonatype Nexus 3.

With this sample configuration, the following steps are automated;

- Enables anonymous access.
- Creates two users, *dev.user* and *ci.user*. (Passwords, `dev.user123`, `ci.user123`)
- Adds APT repositories.
- Adds Docker repositories.
- Adds raw repositories.


## Installation

In this guide docker based installation is used, but it easily be used for other installation methods as well.

First step is to start a Nexus instance without random password and with Script Support. This is done via provided property file `etc/nexus.properties`. The file needs to be inside `etc` directory of Nexus.

That's why, we need to create `data` directory (for Nexus data) with correct permissions and initial configuration file.

```sh
mkdir -p data && \
cp -r etc data/ && \
sudo chown -R 200:200 data
```

Then, start Nexus instance.

```sh
docker-compose up -d
```

### Provisioning

After Nexus started, we run the provisioning scripts. (Wait until you see `Started Sonatype Nexus` in logs.)

```sh
docker run --rm -it \
  -v "$PWD/scripts:/scripts" \
  --network nexus-network \
  groovy:3.0.8 \
  /scripts/provision.sh
```

#### Customizing Provisioning Script

`provision.sh` file uses the following environment variables.

- `NEXUS_USER`: Nexus admin user. (Default: *admin*)
- `NEXUS_PASSWORD`: Nexus admin password. (Default: *admin123*)
- `NEXUS_HOST`: Host of the Nexus. (Default: *http://nexus:8081*)
- `SCRIPTS_DIR`: Scripts directory path. (Default: */scripts*)

---
## References
- [How to Automate Nexus Repository Manager](https://baykara.medium.com/how-to-automate-nexus-setup-process-5755183bc322)
- [Nexus Scripting Examples](https://github.com/sonatype-nexus-community/nexus-scripting-examples)
- [Automated Setup of Nexus Repository Manager](https://blog.sonatype.com/automated-setup-of-a-repository-manager)
- https://help.sonatype.com/repomanager3/rest-and-integration-api
- https://github.com/ansible-ThoTeam/nexus3-oss
- https://github.com/sonatype/nexus-public/blob/release-3.31.1-01/plugins/nexus-script-plugin/src/main/java/org/sonatype/nexus/script/plugin/internal/provisioning/RepositoryApiImpl.groovy
