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

or load from configuration file

```bash
cf create-service p-config-server standard cs-demo-config-server -c ./config-server.json 
```

## You can further customise the configuration repo if needed using config below.

It could serve the git directory structure that has properties for multiple projects in subfolders, thus `{application}` 

```json
{
  "git": {
    "uri": "https://github.com/poprygun/cs-demo-config",
    "searchPaths": "{application}"
  }
}
```



## Endpoint should render data in application.yml that was deployed in config repository 'newheaven' in our case

[Test Endpoint](https://cs-demo.apps.pcfone.io/where)
