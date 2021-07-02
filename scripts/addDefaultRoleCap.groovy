import groovy.json.JsonOutput
import org.sonatype.nexus.capability.CapabilityReference
import org.sonatype.nexus.capability.CapabilityType
import org.sonatype.nexus.internal.capability.DefaultCapabilityReference
import org.sonatype.nexus.internal.capability.DefaultCapabilityRegistry

returnValue = JsonOutput.toJson([result : 'Did NOT add Default Role'])

def capabilityRegistry = container.lookup(DefaultCapabilityRegistry.class.getName())

// Capability specific values/properties
// def capabilityType = CapabilityType.capabilityType("rutaut")
// def capabilityProps = ['httpHeader': 'some_auth_header']

def capabilityType = CapabilityType.capabilityType("defaultrole")
def capabilityProps = ['role': 'nx-anonymous']
def capabilityNotes = 'configured through scripting api'

// Check if existing Rut Auth capability exists
DefaultCapabilityReference existing = capabilityRegistry.all.find { CapabilityReference capabilityReference ->
  capabilityReference.context().descriptor().type() == capabilityType
}

// If it doesn't, add it with given values/properties
// This should also enable the rutauth-realm
if (!existing)
{
  log.info('Default role capability created as: {}',
           capabilityRegistry.add(capabilityType, true, capabilityNotes, capabilityProps).toString())

  returnValue = JsonOutput.toJson([result : 'Successfully added default role!'])
}

return returnValue


// Reference
// https://community.sonatype.com/t/add-and-enable-rut-auth-capability-using-scripting-api/657/3
