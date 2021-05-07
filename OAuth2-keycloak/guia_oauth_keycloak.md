# OAuth2 

Links de referência: https://tools.ietf.org/html/rfc6749#section-1.1

https://www.oauth.com/oauth2-servers/the-resource-server/

É composto por 4 entidade: 

- Resource owner

O **Resource Owner** também pode ser definido como usuário final da nossa aplicação. Um detalhe importante é que algumas vezes esse usuário final pode ser um sistema. É importante ressaltar que Resource Owner não se limita à ser uma pessoa.

Por exemplo, se pensarmos no *Flickr* , um popular serviço de armazenamento e compartilhamento de fotos, O **Resource Owner** é o dono das fotos que ele decidiu publicar no serviço.

- Resource server

- Cliente

- Authorization server

  ```
  1.2.  Protocol Flow
  
       +--------+                               +---------------+
       |        |--(A)- Authorization Request ->|   Resource    |
       |        |                               |     Owner     |
       |        |<-(B)-- Authorization Grant ---|               |
       |        |                               +---------------+
       |        |
       |        |                               +---------------+
       |        |--(C)-- Authorization Grant -->| Authorization |
       | Client |                               |     Server    |
       |        |<-(D)----- Access Token -------|               |
       |        |                               +---------------+
       |        |
       |        |                               +---------------+
       |        |--(E)----- Access Token ------>|    Resource   |
       |        |                               |     Server    |
       |        |<-(F)--- Protected Resource ---|               |
       +--------+                               +---------------+
  ```

  ​	 https://tools.ietf.org/html/rfc6749#section-1.1

Resource server no Spring: https://docs.spring.io/autorepo/docs/spring-security-oauth2-boot/2.0.0.RC2/reference/html/boot-features-security-oauth2-resource-server.html

- Realm: https://www.keycloak.org/docs/latest/server_admin/#core-concepts-and-terms

  O keycloak tem um conceito de Realm, que na prática é um grupo de divisão lógico. Neste grupo contém usuários, credenciais, perfis e grupos.

  Uma parte bastante importante é que um Realm é totalmente isolado de outro Realm, dessa maneira você só consegue gerenciar usuários que o próprio Realm controla.

- Como criar um REALM: https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/informacao_suporte/keycloak-realm.md

  https://www.keycloak.org/docs/latest/server_admin/#admin-console

  https://www.keycloak.org/docs/latest/server_admin/#_create-realm

- Login do Keyclock: https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/informacao_suporte/keycloak-login.md



## Keycloak

Link de referência: https://www.keycloak.org/documentation

https://github.com/keycloak/keycloak

Diferença entre IAM E SSO:

The key difference between SSO and FIM is while SSO is designed to authenticate a single credential across various systems within one organization, federated identity management systems offer single access to a number of applications across various enterprises. 

So, while SSO is a function of FIM, having SSO in place won’t necessarily allow for federated identity management. That said, both tools are crucial in supporting organizations with both securing their data and minimizing obstacles in user experience. 

O Keycloak também faz parte do portfólio de projetos da CNCF, uma espécie de catálogo que nos ajuda escolher ferramentas que nos ajudam a trabalhar com aplicaçãoes Cloud-native: https://landscape.cncf.io/

Começando com keycloak: https://www.keycloak.org/docs/latest/getting_started/index.html

Obs: https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/informacao_procedural/nosso-compose.md