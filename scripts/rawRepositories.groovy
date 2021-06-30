import org.sonatype.nexus.blobstore.api.BlobStoreManager

// create a new blob store dedicated to usage with raw repositories
// def rawStore = blobStore.createFileBlobStore('raw', 'raw')

// and create a first raw hosted repository for documentation using the new blob store
// repository.createRawHosted('file-private', rawStore.name)
repository.createRawHosted('file-private', BlobStoreManager.DEFAULT_BLOBSTORE_NAME)
repository.createRawHosted('file-public', BlobStoreManager.DEFAULT_BLOBSTORE_NAME)

repository.createRawProxy('raw.githubusercontent.com',
                          'https://raw.githubusercontent.com',
			  BlobStoreManager.DEFAULT_BLOBSTORE_NAME,
			  true
			  )
repository.createRawProxy('github.com',
                          'https://github.com',
                          BlobStoreManager.DEFAULT_BLOBSTORE_NAME,
                          true
                          )

log.info('Script rawRepositories completed successfully')
