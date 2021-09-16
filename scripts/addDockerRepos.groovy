import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.config.WritePolicy
import org.sonatype.nexus.repository.Repository

def blobstore = BlobStoreManager.DEFAULT_BLOBSTORE_NAME
def contentMaxAge = -1
def negativeCacheTTL = 1
def metadataMaxAge = 1440

def v1Enabled = true
def strictContentTypeValidation = true

// Create hosted Docker Repository
repository.createDockerHosted('docker-private',              // name
                              8083,                          // httpPort
                              null,                          // httpsPort
                              blobstore,                     // blobStoreName
                              strictContentTypeValidation,   // strictContentTypeValidation
                              v1Enabled,                     // v1Enabled
                              WritePolicy.ALLOW,             // writePolicy
                              true                           // forceBasicAuth
                              )

// Create Docker Hub proxy
Repository repo = repository.createDockerProxy('docker-hub-proxy',             // name
                             'https://registry-1.docker.io', // remoteUrl
                             'HUB',                          // indexType
                             null,                           // indexUrl
                             null,                           // httpPort
                             null,                           // httpsPort
                             blobstore,                      // blobStoreName
                             strictContentTypeValidation,    // strictContentTypeValidation
                             v1Enabled                       // v1Enabled
                             )

if (repo != null) {
  log.info("Modify repository: $repo.name")
  repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
  repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
  repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
}

// Create Docker group
repository.createDockerGroup('docker-group',              // name
                             8082,                        // httpPort
                             null,                        // httpsPort
                             ['docker-private', 'docker-hub-proxy'],  // members
                             v1Enabled,                   // v1Enabled
                             blobstore,                   // blobStoreName
                             false                        // forceBasicAuth
                             )

// Add gcr.io
repo = repository.createDockerProxy('docker-gcr.io-proxy',              // name
                             'https://gcr.io',            // remoteUrl
                             'REGISTRY',                  // indexType
                             null,                        // indexUrl
                             8084,                        // httpPort
                             null,                        // httpsPort
                             blobstore,                   // blobStoreName
                             strictContentTypeValidation, // strictContentTypeValidation
                             v1Enabled                    // v1Enabled
                             )

if (repo != null) {
  log.info("Modify repository: $repo.name")
  repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
  repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
  repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
}

// Add k8s.gcr.io
repo = repository.createDockerProxy('docker-k8s.gcr.io-proxy',              // name
                             'https://k8s.gcr.io',        // remoteUrl
                             'REGISTRY',                  // indexType
                             null,                        // indexUrl
                             8085,                        // httpPort
                             null,                        // httpsPort
                             blobstore,                   // blobStoreName
                             strictContentTypeValidation, // strictContentTypeValidation
                             v1Enabled                    // v1Enabled
                             )

if (repo != null) {
  log.info("Modify repository: $repo.name")
  repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
  repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
  repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
}

// Add quay.io
repo = repository.createDockerProxy('docker-quay.io-proxy',             // name
                             'https://quay.io',           // remoteUrl
                             'REGISTRY',                  // indexType
                             null,                        // indexUrl
                             8086,                        // httpPort
                             null,                        // httpsPort
                             blobstore,                   // blobStoreName
                             strictContentTypeValidation, // strictContentTypeValidation
                             v1Enabled                    // v1Enabled
                             )

if (repo != null) {
  log.info("Modify repository: $repo.name")
  repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
  repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
  repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
}

// Add ghcr.io
repo = repository.createDockerProxy('docker-ghcr.io-proxy',             // name
                             'https://ghcr.io',           // remoteUrl
                             'REGISTRY',                  // indexType
                             null,                        // indexUrl
                             8087,                        // httpPort
                             null,                        // httpsPort
                             blobstore,                   // blobStoreName
                             strictContentTypeValidation, // strictContentTypeValidation
                             v1Enabled                    // v1Enabled
                             )

if (repo != null) {
  log.info("Modify repository: $repo.name")
  repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
  repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
  repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
}

log.info('Script dockerRepositories completed successfully')
