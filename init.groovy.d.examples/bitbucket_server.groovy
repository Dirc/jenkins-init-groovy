#!groovy
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.impl.*
import hudson.util.Secret
import jenkins.model.Jenkins
import com.cloudbees.jenkins.plugins.bitbucket.endpoints.*

// Required plugins: 
// - bitbucket-pullrequest-builder
// - cloudbees-bitbucket-branch-source
//


// Variables
bitbbucketName:          'Bitbucket_Server_Name',
bitbbucketServerURL:     'bitbucket.example.com',
//bitbbucketCallCanMerge:  false, -> bug in plugin, this parameter is missing in BitbucketServerEndpoint, see https://github.com/jenkinsci/bitbucket-branch-source-plugin/blob/master/src/main/java/com/cloudbees/jenkins/plugins/bitbucket/endpoints/BitbucketServerEndpoint.java
bitbbucketManageHooks:   true,
bitbbucketCredentialsId: 'credential_reference_id_usr_pwd'

// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()

// get credentials domain
def domain = Domain.global()

// get Bitbucket endpoint configuration
def bitbucketEndpoint = jenkins.getDescriptor("com.cloudbees.jenkins.plugins.bitbucket.endpoints.BitbucketEndpointConfiguration")

// define Bitbucket server endpoint
def bitbucketServerEndpoint = new BitbucketServerEndpoint(
        bitbbucketName,
        bitbbucketServerURL,
        //bitbbucketCallCanMerge,
        bitbbucketManageHooks,
        bitbbucketCredentialsId
)

// update endpoint
bitbucketEndpoint.updateEndpoint(bitbucketServerEndpoint)

// save to disk
jenkins.save()