## Specification Implementation

#### Padrão 1 para Injeção de Config: @ConfigProperty
- URL: Path: http://localhost:8080/data/pathforconfig1
- SRC: src/main/java/com/tcarvi/config/ConfigController1.java
    - @Path("/pathforconfig1")
    - @RequestScoped
    - public class ConfigTestController1 {
        - @ConfigProperty(name = "injected.value")
        - String injectedValue;
        - // Path Endpoint: http://localhost:8080/data/pathforconfig1/endpointRSXinjected
        - @Path("/endpointRSXinjected")
        - @GET
        - public String getInjectedConfigValue() { return "Config value as Injected by CDI " + injectedValue; }

#### Padrão 2 para Injeção de Config: ConfigProvider.getConfig().getValue
- URL: Path: http://localhost:8080/data/pathforconfig2
- SRC: src/main/java/com/tcarvi/config/ConfigController2.java
    - @Path("/pathforconfig2")
    - @RequestScoped
    - public class ConfigTestController2 {
        - @Path("/endpointRSXlookup")
        - @GET
        - public String getLookupConfigValue() {
            - Config config = ConfigProvider.getConfig();
            - String value = config.getValue("value", String.class);
            - return "Config value from ConfigProvider " + value; }

#### Padrão para arquivo de Configuração, com ou sem profiles:
- src/main/resources/application.yaml

#### Dependências no pom.xml:
```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-config-yaml</artifactId>
</dependency>
```



### Config

Configuration of your application parameters. Specification [here](https://microprofile.io/project/eclipse/microprofile-config)

The example class **ConfigTestController** shows you how to inject a configuration parameter and how you can retrieve it programmatically.

- By default there are 3 default ConfigSources:
    - System.getProperties() (ordinal=400)
    - System.getenv() (ordinal=300)
    - all META-INF/microprofile-config.properties files on the ClassPath. (default ordinal=100, separately configurable via a config_ordinal property inside each file)

The Quarkus Application configuration file is loaded from the classpath resources, for instance .
- src/main/resources/application.properties,  (ordinal=250)
- src/test/resources/application.properties or  (ordinal=250)
- from a jar dependency that contains an application.properties entry.
It is possible to write and register a custom ConfigSources. An example would be a ConfigSource which gets the configured values from a shared database table in a cluster.


Config Profile indicates the project phase, such as dev, testing, live, etc.

