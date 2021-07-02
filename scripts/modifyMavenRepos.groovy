import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.Repository


def blobstore = BlobStoreManager.DEFAULT_BLOBSTORE_NAME
def contentMaxAge = -1
def negativeCacheTTL = 1
def metadataMaxAge = 1440

def groupName = 'maven-public'

def groupRepoList = [
  'maven-snapshots',
  'maven-releases',
]

def proxyRepoList = [
  'maven-central',
  'maven-spring-releases-proxy',
  'maven-javanet-proxy',
  'maven-sonatype-oss-proxy',
  'maven-typesafe-releases-proxy',
  'maven-google-mirror-eu-proxy',
]

Repository repo = null

proxyRepoList.each{it ->
  log.info("Modify repository: $it")

  repo = repository.getRepositoryManager().get(it)
  if (repo != null) {
    log.info("Attributes for $repo.name")
    log.info(repo.getConfiguration().getAttributes().toString)
    repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
    repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
    repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL

    groupRepoList.add(it)

  }
}

// Recreate maven-public group
repo = repository.getRepositoryManager().get(groupName)
if (repo != null) {
  repository.getRepositoryManager().delete(repo.name)
  repo.destroy()

  repository.createMavenGroup(groupName, groupRepoList, blobstore)
}
log.info("Recreate repository group: maven-public")

log.info('Script modifyMavenRepos completed successfully')


