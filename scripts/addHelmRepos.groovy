import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.Repository

def blobstore = BlobStoreManager.DEFAULT_BLOBSTORE_NAME
def contentMaxAge = -1
def negativeCacheTTL = 1
def metadataMaxAge = 1440
def strictContentTypeValidation = true

def proxyMap = [
  'helm-stable-proxy': 'https://charts.helm.sh/stable',
  'helm-prometheus-proxy': 'https://prometheus-community.github.io/helm-charts',
  'helm-kube-dashboard-proxy': 'https://kubernetes.github.io/dashboard/',
  'helm-bitnami-proxy': 'https://charts.bitnami.com/bitnami',
  'helm-kubeapps-proxy': 'https://hub.kubeapps.com/',
]

proxyMap.each{entry ->
  log.info("$entry.key: $entry.value")

  def configuration = repository.createProxy(entry.key, 'helm-proxy', entry.value, blobstore, strictContentTypeValidation)
  Repository repo = repository.createRepository(configuration)

  if (repo != null) {
    log.info("Modify repository: $entry.key")
    repo.getConfiguration().getAttributes().'proxy'.'contentMaxAge' = contentMaxAge
    repo.getConfiguration().getAttributes().'proxy'.'metadataMaxAge' = metadataMaxAge
    repo.getConfiguration().getAttributes().'negativeCache'.'timeToLive' = negativeCacheTTL
  }
}

log.info('Script addHelmRepos completed successfully')
