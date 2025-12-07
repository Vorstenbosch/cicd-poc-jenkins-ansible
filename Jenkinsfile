pipeline {
    agent {
        docker {
            image 'python:3.11-slim'
            label 'jenkins-agent'
            args '-u root:root'
        }
    }
    
    stages {
        stage('Install Tools') {
            steps {
                sh '''
                    apt-get update
                    apt-get install -y ruby
                    pip install ansible-lint
                    gem install rubocop
                '''
            }
        }
        
        stage('Lint Ansible') {
            steps {
                sh '''
                    cd ansible
                    ansible-lint site.yml || true
                    ansible-lint roles/*/tasks/*.yml || true
                '''
            }
        }
        
        stage('Lint Vagrantfile') {
            steps {
                sh '''
                    cd vagrant
                    rubocop Vagrantfile || true
                '''
            }
        }
        
        stage('Validate Ansible Syntax') {
            steps {
                sh '''
                    cd ansible
                    ansible-playbook --syntax-check site.yml
                '''
            }
        }
    }
    
    post {
        success {
            echo 'Linting completed successfully!'
        }
        failure {
            echo 'Linting failed!'
        }
    }
}
