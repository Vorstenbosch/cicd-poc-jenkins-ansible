# Ansible Multi-Environment Setup

This Ansible configuration supports multiple deployment environments.

## Directory Structure

```
inventories/
├── vagrant/          # Local development environment
│   └── hosts
├── raspberry-pi/     # Raspberry Pi cluster
│   └── hosts
└── production/       # Production servers (if needed)
    └── hosts
```

## Using Different Environments

### Vagrant (default)
```bash
ansible-playbook site.yml
# OR explicitly
ansible-playbook -i inventories/vagrant/hosts site.yml
```

### Raspberry Pi
```bash
ansible-playbook -i inventories/raspberry-pi/hosts site.yml
```

### Deploy to specific environment and host group
```bash
# Deploy only Jenkins to Raspberry Pi
ansible-playbook -i inventories/raspberry-pi/hosts site.yml --limit jenkins

# Deploy only agents to Vagrant
ansible-playbook -i inventories/vagrant/hosts site.yml --limit jenkins_agents
```

## Setting Up Raspberry Pi Environment

1. **Update IPs** in `inventories/raspberry-pi/hosts` with your actual Raspberry Pi addresses

2. **Set up SSH keys** on your Raspberry Pis:
   ```bash
   ssh-copy-id pi@192.168.1.100
   ssh-copy-id pi@192.168.1.101
   # etc...
   ```

3. **Test connectivity**:
   ```bash
   ansible -i inventories/raspberry-pi/hosts all -m ping
   ```

4. **Deploy**:
   ```bash
   ansible-playbook -i inventories/raspberry-pi/hosts site.yml
   ```

## Environment-Specific Variables

You can create environment-specific variables:

```
inventories/
├── vagrant/
│   ├── hosts
│   └── group_vars/
│       ├── all.yml           # Vagrant-specific vars
│       └── jenkins.yml       # Override Jenkins config for vagrant
└── raspberry-pi/
    ├── hosts
    └── group_vars/
        ├── all.yml           # Raspberry Pi-specific vars
        └── jenkins.yml       # Override Jenkins config for Pi
```

## Tips

- Use `--check` flag for dry-run: `ansible-playbook -i inventories/raspberry-pi/hosts site.yml --check`
- Use `-v` for verbose output to debug issues
- Keep sensitive data (passwords, keys) in Ansible Vault
