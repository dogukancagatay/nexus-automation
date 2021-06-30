import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.config.WritePolicy

// create hosted repo and expose via https to allow deployments
repository.createDockerHosted('docker-private',              // name
                              8083,                          // httpPort
                              null,                          // httpsPort
                              BlobStoreManager.DEFAULT_BLOBSTORE_NAME, // blobStoreName
                              true,                          // strictContentTypeValidation
                              true,                          // v1Enabled
                              WritePolicy.ALLOW,              // writePolicy
                              true                           // forceBasicAuth
                              )

// create proxy repo of Docker Hub and enable v1 to get search to work
// no ports since access is only indirectly via group
repository.createDockerProxy('docker-hub-proxy',             // name
                             'https://registry-1.docker.io', // remoteUrl
                             'HUB',                          // indexType
                             null,                           // indexUrl
                             null,                           // httpPort
                             null,                           // httpsPort
                             BlobStoreManager.DEFAULT_BLOBSTORE_NAME, // blobStoreName
                             true,                           // strictContentTypeValidation
                             true                            // v1Enabled
                             )

// Create docker group
repository.createDockerGroup('docker-group',              // name
                             8082,                        // httpPort
                             null,                        // httpsPort
                             ['docker-private', 'docker-hub-proxy'],  // members
                             true,                        // v1Enabled
                             BlobStoreManager.DEFAULT_BLOBSTORE_NAME, // blobStoreName
                             false                        // forceBasicAuth
                             )

// Add gcr.io
repository.createDockerProxy('gcr.io-proxy',              // name
                             'https://gcr.io',            // remoteUrl
                             'REGISTRY',                  // indexType
                             null,                        // indexUrl
                             null,                        // httpPort
                             null,                        // httpsPort
                             BlobStoreManager.DEFAULT_BLOBSTORE_NAME, // blobStoreName
                             true,                        // strictContentTypeValidation
                             true                         // v1Enabled
                             )

repository.createDockerGroup('gcr.io-group',              // name
                             8084,                        // httpPort
                             null,                        // httpsPort
                             ['gcr.io-proxy'],            // members
                             true,                        // v1Enabled
                             BlobStoreManager.DEFAULT_BLOBSTORE_NAME, // blobStoreName
                             false                        // forceBasicAuth
                             )

// Add quay.io
repository.createDockerProxy('quay.io-proxy',             // name
                             'https://quay.io',           // remoteUrl
                             'REGISTRY',                  // indexType
                             null,                        // indexUrl
                             null,                        // httpPort
                             null,                        // httpsPort
                             BlobStoreManager.DEFAULT_BLOBSTORE_NAME, // blobStoreName
                             true,                        // strictContentTypeValidation
                             true                         // v1Enabled
                             )

repository.createDockerGroup('quay.io-group',             // name
                             8085,                        // httpPort
                             null,                        // httpsPort
                             ['quay.io-proxy'],           // members
                             true,                        // v1Enabled
                             BlobStoreManager.DEFAULT_BLOBSTORE_NAME, // blobStoreName
                             false                        // forceBasicAuth
                             )

log.info('Script dockerRepositories completed successfully')
