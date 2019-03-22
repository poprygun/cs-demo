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
cf create-service p-config-server standard cs-demo-config-server -c '{"git": {"uri": "https://github.com/poprygun/cs-demo-config"}, "encrypt": { "key": "some key to use" }}'
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

## [Encryption](https://docs.run.pivotal.io/spring-cloud-services/config-server/configuring-with-git.html#encryption-and-encrypted-values)


### Specify `encryption key` (or key if using assymetric encryption)

cf create-service p-config-server trial encryption-config-server -c '{"git": {"uri": "https://github.com/poprygun/cs-demo-config"}, "encrypt": { "key": "some key to use" }}'


### Create service key to access Config Server service instance

```bash
cf create-service-key encryption-config-server config-server-key
```

### Collect service key info

```bash
cf service-key encryption-config-server config-server-key

```

```json
{
 "access_token_uri": "https://p-spring-cloud-services.uaa.run.pivotal.io/oauth/token",
 "client_id": "p-config-server-835786a3-44ec-4a70-9f61-337a2551e3ff",
 "client_secret": "mD7vF09cyUlv",
 "uri": "https://config-24b15704-94d7-4494-8263-82a9ac30c731.cfapps.io"
}
```


## [Accessing encrypt API endpoint](https://docs.pivotal.io/spring-cloud-services/1-5/service-broker-and-instances.html#get-access-token-for-direct-requests-to-a-service-instance)

### Access using cf cli

```bash
curl -H "Authorization: $(cf oauth-token)" https://p-spring-cloud-services.uaa.run.pivotal.io/encrypt -d 'Value to be encrypted'
```

### Access using curl

- Obtain an access token

```bash
export USER=cf-username
export PASSWORD=cf-password

access_token="Bearer $(curl -k -XPOST -H"Application/json" -u "cf:" \
--data "username=${USER}&password=${PASSWORD}&client_id=cf&grant_type=password&response_type=token" \
https://login.run.pivotal.io/oauth/token | jq '.access_token')"

```

- Encrypt the secret value

```bash
curl -k -v -XPOST -H "Authorization: Bearer ${access_token}" https://config-24b15704-94d7-4494-8263-82a9ac30c731.cfapps.io/encrypt -d 'Value to be encrypted'

```