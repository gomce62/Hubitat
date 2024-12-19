import requests
import json

# Replace with your Emporia Vue credentials and delete < >
username = '<enter user id>'
password = '<enter pW>'

# Authenticate and get tokens
auth_url = 'https://api.emporiaenergy.com/customers/auth'
auth_data = {
    'username': username,
    'password': password
}

# Set headers
headers = {
    'Content-Type': 'application/json'
}

# Send authentication request
response = requests.post(auth_url, headers=headers, json=auth_data)
print(f"Authentication response status: {response.status_code}")
print(f"Authentication response text: {response.text}")

if response.status_code == 200:
    tokens = response.json()
    id_token = tokens.get('id_token')
    if id_token:
        print(f"ID Token: {id_token}")

        # Retrieve device list
        devices_url = 'https://api.emporiaenergy.com/customers/devices'
        headers['authtoken'] = id_token

        response = requests.get(devices_url, headers=headers)
        print(f"Device list response status: {response.status_code}")
        print(f"Device list response text: {response.text}")

        if response.status_code == 200:
            devices = response.json().get('devices', [])
            for device in devices:
                print(f"Device GID: {device['deviceGid']}, Device Name: {device['deviceName']}")
        else:
            print(f"Failed to retrieve devices: {response.status_code} - {response.text}")
    else:
        print("Failed to retrieve ID Token")
else:
    print(f"Failed to authenticate: {response.status_code} - {response.text}")
