# Config Server on SCS with boot app.

## In case you want to get configuration from cli

```bash
cf install-plugin -r CF-Community "spring-cloud-services"
```

## _Create Config Service_


## You can customise the configuration repo if needed using config below.

It could serve the git directory structure that has properties for multiple projects in subfolders, thus `{application}` 

```json
{
  "git": {
    "uri": "https://github.com/poprygun/cs-demo-config",
    "searchPaths": "{application}"
  }
}
```

```bash
cf create-service p-config-server standard cs-demo-config-server -c '{"git": {"uri": "https://github.com/poprygun/cs-demo-config"}}'
cf update-service cs-demo-config-server -c ./config-server.json 
```



## To [get a token](https://gist.github.com/kelapure/1670b881b02cf3abe77891ea55d411fc)

```bash
http --body --form POST https://p-spring-cloud-services.uaa.run.pcfone.io/oauth/token grant_type=client_credentials --auth xexe-clientid:xexe-password | jq -r .access_token
```

## Use postman to debug config server response

provide token obtained from previous request
post to https://config-7307bf5b-d9a2-416f-a7d3-d8891aea26fa.apps.pcfone.io/demo-config-extra/cloud


## _Create [Credhub Service](https://docs.pivotal.io/credhub-service-broker/using.html)_

Variables can be accessed by [directly referencing `vcap`](https://pivotal.io/application-transformation-recipes/security/securing-applications-with-credhub)

### Define secrets configuration file

```json
{
  "clientId": "top-secret-client-id",
  "secret": "top-secret-secret-nowone-should-know"
}
```

```bash
cf create-service credhub default my-credhub-instance -c ./credhub.json
cf update-service my-credhub-instance -c ./credhub.json
```


## Endpoint should render data in application.yml that was deployed in config repository 'newheaven' in our case

[Test Endpoint](https://cs-demo.apps.pcfone.io/where)
