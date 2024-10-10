import requests
import json

url = "http://localhost:4444/api/email/kkkk@gmail.com"
headers = {
    "Content-Type": "application/json"
}
data = {
    "email": "kkkk@gmail.com"
}

response = requests.post(url, headers=headers, data=json.dumps(data))

print("Status Code:", response.status_code)
print("Response Body:", response.text)
