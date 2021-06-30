import groovy.json.JsonOutput
import org.sonatype.nexus.security.realm.RealmManager

realmManager = container.lookup(RealmManager.class.getName())

//
// Enable anonymous access
//
security.setAnonymousAccess(true)
log.info('Anonymous access enable')

//
// Create new admin user
//
// def adminRole = ['nx-admin']
// def adminUser = security.addUser('mehmet.baykara', 'Mehmet', 'Baykara', 'baykara@baykara.com', true, 'admin456', adminRole)
// log.info('User mb created')

//
// Create a new role that allows a user same access as anonymous and adds healtchcheck access
//
def devPrivileges = ['nx-healthcheck-read', 'nx-healthcheck-summary-read']
def anoRole = ['nx-anonymous']
// add roles that uses the built in nx-anonymous role as a basis and adds more privileges
security.addRole('developer', 'developer', 'User with privileges to allow read access to repo content and healtcheck', devPrivileges, anoRole)
log.info('Role developer created')

// use the new role to create a user
def devRoles = ['developer']
def devUser = security.addUser('dev.user', 'developer', 'User', 'dev.user@example.com', true, 'dev.user123', devRoles)
log.info('User dev.user created')

//
// Create new role that allows deployment and create a user to be used on a CI server
//
// privileges with pattern * to allow any format, browse and read are already part of nx-anonymous
def depPrivileges = ['nx-repository-view-*-*-add', 'nx-repository-view-*-*-edit']
def roles = ['developer']

// add roles that uses the developer role as a basis and adds more privileges
security.addRole('deployer', 'deployer', 'User with privileges to allow deployment all repositories', depPrivileges, roles)
log.info('Role deployer created')

def depRoles = ['deployer']
def ciUser = security.addUser('ci.user', 'ci-user', 'User', 'ci.user@example.com', true, 'ci.user123', depRoles)
log.info('User ci.user created')


// Enable Docker Bearer Token Realm
realmManager.enableRealm("DockerToken")

//
// Change default password
//
// def user = security.securitySystem.getUser('admin')
// user.setEmailAddress('baykara@baykara.com')
// security.securitySystem.updateUser(user)
// security.securitySystem.changePassword('admin','admin456')
// log.info('default password for admin changed')

log.info('Script security completed successfully')

// Return a JSON response containing our new Users for confirmation
return JsonOutput.toJson([devUser, ciUser])
