import org.sonatype.nexus.blobstore.api.BlobStoreManager

repository.createAptProxy('ubuntu-archive-apt-proxy', 'http://de.archive.ubuntu.com/ubuntu/', BlobStoreManager.DEFAULT_BLOBSTORE_NAME, 'focal', true)
repository.createAptProxy('ubuntu-docker-apt-proxy', 'https://download.docker.com/linux/ubuntu/', BlobStoreManager.DEFAULT_BLOBSTORE_NAME, 'focal', true)
repository.createAptProxy('ubuntu-security-proxy', 'http://security.ubuntu.com/ubuntu/', BlobStoreManager.DEFAULT_BLOBSTORE_NAME, 'focal', true)
repository.createAptProxy('ubuntu2004-opensuse-kubic-proxy', 'https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/xUbuntu_20.04/', BlobStoreManager.DEFAULT_BLOBSTORE_NAME, 'focal', true)
repository.createAptProxy('apt-kubernetes-io-proxy', 'https://packages.cloud.google.com/apt/', BlobStoreManager.DEFAULT_BLOBSTORE_NAME, 'focal', true)

log.info('Script aptRepositories completed successfully')
