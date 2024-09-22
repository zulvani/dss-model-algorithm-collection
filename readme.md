# DSS

## SAW Method

### Request Example
```curl
curl --location 'http://localhost:8080/public/dss' \
--header 'Content-Type: application/json' \
--data '{
    "method": "SAW",
    "parameters": ["Wawasan tentang Sistem Informasi", "Pratek instalasi Jaringan", "Kepribadian", "wawasan tentang Etika"],
    "weight": {
        "method": "DIRECT",
        "values": [35, 25, 25, 15]
    },
    "criteria": ["DSS_CRITERIA_BENEFIT", "DSS_CRITERIA_BENEFIT", "DSS_CRITERIA_BENEFIT", "DSS_CRITERIA_BENEFIT"],
    "dssAlternativeParameters": [
        {
            "alternativeName": "Indra",
            "parameterValues": [70,50,80,60]
        },
        {
            "alternativeName": "Roni",
            "parameterValues": [50,60,82,70]
        },
        {
            "alternativeName": "Putri",
            "parameterValues": [85,55,80,75]
        },
        {
            "alternativeName": "Dani",
            "parameterValues": [82,70,65,85]
        },
        {
            "alternativeName": "Ratna",
            "parameterValues": [75,75,85,74]
        },
        {
            "alternativeName": "Mira",
            "parameterValues": [62,50,75,80]
        }
    ]
}'
```