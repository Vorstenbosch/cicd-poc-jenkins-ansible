# Ansible Configuration

This directory contains Ansible playbooks and roles for configuring the CI/CD environment.

## Structure

```
ansible/
├── site.yml              # Main playbook
├── ansible.cfg           # Ansible configuration
├── inventory             # Inventory file with host definitions
├── requirements.yml      # External roles from Ansible Galaxy
└── roles/
    └── webapp/           # Custom webapp role
        └── tasks/
            └── main.yml
```

## Setup

### 1. Install Required Roles

Install the open source roles from Ansible Galaxy:

```bash
ansible-galaxy install -r requirements.yml
```

This will install:
- `geerlingguy.java` - Java installation
- `geerlingguy.jenkins` - Jenkins installation and configuration

### 2. Run Playbooks

From the Ansible master machine:

```bash
# Run all playbooks
ansible-playbook site.yml

# Run only Jenkins setup
ansible-playbook site.yml --limit jenkins

# Run only WebApp setup
ansible-playbook site.yml --limit webapp

# Check what would change (dry-run)
ansible-playbook site.yml --check

# Verbose output
ansible-playbook site.yml -v
```

### 3. Test Connectivity

```bash
# Ping all hosts
ansible all -m ping

# Check Jenkins host
ansible jenkins -m ping

# Run ad-hoc command
ansible all -a "uptime"
```

## Roles

### External Roles (from Ansible Galaxy)

- **geerlingguy.java**: Installs OpenJDK on CentOS
- **geerlingguy.jenkins**: Installs and configures Jenkins

### Custom Roles

- **webapp**: Custom role for web application deployment (currently empty)

## Customization

To customize Jenkins installation, create a file `group_vars/jenkins.yml`:

```yaml
---
java_packages:
  - java-11-openjdk

jenkins_hostname: localhost
jenkins_http_port: 8080
jenkins_admin_username: admin
jenkins_admin_password: admin
```

## Troubleshooting

If SSH connection fails:
```bash
# Test SSH manually
ssh -i ~/.ssh/id_rsa vagrant@192.168.56.10

# Check inventory
ansible-inventory --list
```
