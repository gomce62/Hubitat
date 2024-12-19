import pyemvue
import json
import requests

vue = pyemvue.PyEmVue()
#create the auth token and store it in keys.json
vue.login(username='gomce62.web@gmail.com', password='g0&86KgpLr^Hd9&', token_storage_file='keys.json')

#retrieve the auth_token from keys.json
with open('keys.json')as f:
        data = json.load(f)

#pass token to hubitat via Maker API
token_id = data['id_token']
#requests.get("http://<url_or_ip_to_hubitat>/apps/api/<maker_api_id>/devices/<emporia_device_id>/authToken/{}?access_token=<maker_api_access_token>".format(token_id))
#requests.get("http://192.168.1.123/apps/api/392/devices/1241/authToken/{}?access_token=97e75710-3eae-4822-bdd0-975a12ea3d5b".format(token_id))
requests.get("https://cloud.hubitat.com/api/bfd61e43-7faa-412e-8160-14ead4770947/apps/392/devices/1241/authToken/{}?access_token=97e75710-3eae-4822-bdd0-975a12ea3d5b".format(token_id))


print(token_id)
