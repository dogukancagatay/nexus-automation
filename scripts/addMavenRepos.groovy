import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.maven.VersionPolicy
import org.sonatype.nexus.repository.maven.LayoutPolicy


def blobstore = BlobStoreManager.DEFAULT_BLOBSTORE_NAME


def proxyMap = [
  'maven-spring-releases-proxy': 'https://repo.spring.io/release',
  'maven-javanet-proxy': 'https://maven.java.net/content/repositories/public/',
  'maven-sonatype-oss-proxy': 'https://oss.sonatype.org/content/repositories/public',
  'maven-typesafe-releases-proxy': 'https://repo.typesafe.com/typesafe/releases',
  'maven-google-mirror-eu-proxy': 'https://maven-central-eu.storage-download.googleapis.com/maven2/',
]


proxyMap.each{entry ->
  log.info("Adding maven proxy $entry.key: $entry.value")
  repository.createMavenProxy(entry.key, entry.value, blobstore, true, VersionPolicy.RELEASE, LayoutPolicy.PERMISSIVE)
}

log.info('Script aptRepositories completed successfully')


