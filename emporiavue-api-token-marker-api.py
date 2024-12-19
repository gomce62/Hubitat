import pyemvue
import json
import requests

vue = pyemvue.PyEmVue()
#create the auth token and store it in keys.json
vue.login(username='enter userid', password='enter PW', token_storage_file='keys.json')

#retrieve the auth_token from keys.json
with open('keys.json')as f:
        data = json.load(f)

#pass token to hubitat via Maker API
token_id = data['id_token']
requests.get("http://<url_or_ip_to_hubitat>/apps/api/<maker_api_id>/devices/<emporia_device_id>/authToken/{}?access_token=<maker_api_access_token>".format(token_id))

print(token_id)
