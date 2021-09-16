import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.Repository

// create a new blob store dedicated to usage with raw repositories
// def blobstore = blobStore.createFileBlobStore('raw', 'raw').name
def blobstore = BlobStoreManager.DEFAULT_BLOBSTORE_NAME
def contentMaxAge = -1
def negativeCacheTTL = 1
def metadataMaxAge = 1440
def strictContentTypeValidation = false

repository.createRawHosted('file-private', blobstore)
repository.createRawHosted('file-public', blobstore)

def proxyMap = [
  'github.com': 'https://github.com',
  'raw.githubusercontent.com': 'https://raw.githubusercontent.com',
  'downloads.apache.org': 'https://downloads.apache.org',
  'update.k3s.io': 'https://update.k3s.io',
  'storage.googleapis.com': 'https://storage.googleapis.com',
  'get.helm.sh': 'https://get.helm.sh',
]

proxyMap.each{entry ->
  log.info("Adding raw proxy $entry.key: $entry.value")
  Repository repo = repository.createRawProxy(entry.key, entry.value, blobstore, strictContentTypeValidation)

  if (repo != null) {
    log.info("Settings attributes for $repo.name")
    repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
    repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
    repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
  }
}

log.info('Script completed successfully')
