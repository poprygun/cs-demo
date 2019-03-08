# Config Server on SCS with boot app.

## Create Config Server service

## In case you want to get configuration from cli

```bash
cf install-plugin -r CF-Community "spring-cloud-services"
```

## Create Service

```bash
cf create-service p-config-server standard cs-demo-config-server -c '{"git": {"uri": "https://github.com/poprygun/cs-demo-config"}}'

```

## You can further customise the configuration repo if needed

```json
{
  "git": {
    "uri": "https://github.com/spring-cloud-samples/config-repo",
    "repos": {
      "cook": {
        "pattern": "cook*",
        "uri": "https://github.com/spring-cloud-services-samples/cook-config"
      }
    }
  },
  "count": 3
}
```

## Endpoint should render data in application.yml that was deployed in config repository 'newheaven' in our case

[Test Endpoint](https://cs-demo.apps.pcfone.io/where)
