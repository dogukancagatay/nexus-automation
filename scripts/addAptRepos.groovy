import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.Repository

def blobstore = BlobStoreManager.DEFAULT_BLOBSTORE_NAME
def contentMaxAge = -1
def negativeCacheTTL = 1
def metadataMaxAge = 1440

def distroVersionName = 'focal'

def proxyMap = [
  'apt-archive-ubuntu-proxy': 'http://de.archive.ubuntu.com/ubuntu/',
  'apt-docker-ubuntu-proxy': 'https://download.docker.com/linux/ubuntu/',
  'apt-security-ubuntu-proxy': 'http://security.ubuntu.com/ubuntu/',
  'apt-opensuse-proxy': 'https://download.opensuse.org/',
  'apt-kubernetes-proxy': 'https://packages.cloud.google.com/apt/',
]

proxyMap.each{entry ->
  log.info("$entry.key: $entry.value")
  Repository repo = repository.createAptProxy(entry.key, entry.value, blobstore, distroVersionName, true)

  if (repo != null) {
    log.info("Modify repository: $entry.key")
    repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
    repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
    repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
  }
}


log.info('Script aptRepositories completed successfully')
