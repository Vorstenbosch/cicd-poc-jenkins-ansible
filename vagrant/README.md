# Vagrant CI/CD Environment

This Vagrant configuration creates a complete CI/CD environment with four machines:

## Machines

1. **Jenkins Server** (192.168.56.10)
   - 1 GB RAM, 1 CPU
   - Jenkins CI/CD server
   - Access: http://192.168.56.10:8080

2. **Ansible Master** (192.168.56.11)
   - 512 MB RAM, 1 CPU
   - Ansible automation controller
   - Pre-configured with inventory for all servers

3. **Jenkins Agent** (192.168.56.13)
   - 512 MB RAM, 1 CPU
   - Jenkins build agent
   - Java 17 runtime
   - Workspace at /var/jenkins

4. **Web Application Server** (192.168.56.12)
   - 512 MB RAM, 1 CPU
   - Minimal Rocky Linux 10
   - Access: http://192.168.56.12

## Base OS

All machines run **Rocky Linux 9** (modern CentOS replacement, supported until 2032)

## Getting Started

### Prerequisites
- VirtualBox installed
- Vagrant installed

### Starting the Environment

```bash
# Start all machines
vagrant up

# Start individual machines
vagrant up jenkins
vagrant up ansible
vagrant up jenkins-agent
vagrant up webapp

# SSH into machines
vagrant ssh jenkins
vagrant ssh ansible
vagrant ssh jenkins-agent
vagrant ssh webapp
```

### SSH Keys

SSH keys are located in `vagrant/ssh/`:
- `id_rsa` - Private key
- `id_rsa.pub` - Public key

These keys are automatically configured for:
- SSH access between machines
- Ansible automation
- Passwordless authentication

### Jenkins Initial Setup

1. Access Jenkins at http://192.168.56.10:8080
2. Get the initial admin password:
   ```bash
   vagrant ssh jenkins
   sudo cat /var/lib/jenkins/secrets/initialAdminPassword
   ```
3. Or use the credentials configured via Ansible:
   - Username: `admin`
   - Password: `admin` (if configured in group_vars/jenkins.yml)

### Ansible Usage

SSH into the Ansible master:
```bash
vagrant ssh ansible
```

Deploy Jenkins:
```bash
cd ~/ansible
ansible-galaxy install -r requirements.yml
ansible-playbook site.yml --limit jenkins
```

Test connectivity:
```bash
ansible all -m ping
```

### Managing the Environment

```bash
# Stop all machines
vagrant halt

# Restart machines
vagrant reload

# Destroy environment
vagrant destroy

# Check status
vagrant status
```

## Network Configuration

All machines are on a private network (192.168.56.0/24):
- Jenkins: 192.168.56.10
- Ansible: 192.168.56.11
- WebApp: 192.168.56.12
- Jenkins Agent: 192.168.56.13

## Troubleshooting

If SSH connections fail between machines:
```bash
# From ansible machine
ssh-keyscan 192.168.56.10 >> ~/.ssh/known_hosts
ssh-keyscan 192.168.56.12 >> ~/.ssh/known_hosts
```
