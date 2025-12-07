import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*

// Read the private key
def privateKeyContent = new File('/var/lib/jenkins/.ssh/id_rsa').text

// Get credentials store
def domain = Domain.global()
def store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

// Check if credential already exists
def credentialId = 'jenkins-agent-ssh'
def existingCreds = store.getCredentials(domain).find { it.id == credentialId }

if (!existingCreds) {
    // Create SSH credential
    def credentials = new BasicSSHUserPrivateKey(
        CredentialsScope.GLOBAL,
        credentialId,
        'vagrant',
        new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource(privateKeyContent),
        '',
        'SSH key for Jenkins agents'
    )
    
    // Add credential
    store.addCredentials(domain, credentials)
    Jenkins.instance.save()
    println "Created SSH credential: ${credentialId}"
} else {
    println "SSH credential ${credentialId} already exists"
}
