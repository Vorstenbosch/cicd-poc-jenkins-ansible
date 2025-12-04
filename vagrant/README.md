# Vagrant CI/CD Environment

This Vagrant configuration creates a complete CI/CD environment with three machines:

## Machines

1. **Jenkins Server** (192.168.56.10)
   - 2 GB RAM, 2 CPUs
   - Jenkins CI/CD server
   - Access: http://192.168.56.10:8080

2. **Ansible Master** (192.168.56.11)
   - 1 GB RAM, 1 CPU
   - Ansible automation controller
   - Pre-configured with inventory for Jenkins and WebApp servers

3. **Web Application Server** (192.168.56.12)
   - 1 GB RAM, 1 CPU
   - Nginx web server
   - Python 3 environment
   - Access: http://192.168.56.12

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
vagrant up webapp

# SSH into machines
vagrant ssh jenkins
vagrant ssh ansible
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

### Ansible Usage

SSH into the Ansible master:
```bash
vagrant ssh ansible
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

## Troubleshooting

If SSH connections fail between machines:
```bash
# From ansible machine
ssh-keyscan 192.168.56.10 >> ~/.ssh/known_hosts
ssh-keyscan 192.168.56.12 >> ~/.ssh/known_hosts
```
