import org.sonatype.nexus.blobstore.api.BlobStoreManager

// create a new blob store dedicated to usage with raw repositories
// def blobstore = blobStore.createFileBlobStore('raw', 'raw').name
def blobstore = BlobStoreManager.DEFAULT_BLOBSTORE_NAME
def strictContentTypeValidation = false

repository.createRawHosted('file-private', blobstore)
repository.createRawHosted('file-public', blobstore)

repository.createRawProxy('github.com', 'https://github.com', blobstore, strictContentTypeValidation)
repository.createRawProxy('raw.githubusercontent.com', 'https://raw.githubusercontent.com', blobstore, strictContentTypeValidation)
repository.createRawProxy('downloads.apache.org', 'https://downloads.apache.org', blobstore, strictContentTypeValidation)

log.info('Script rawRepositories completed successfully')
